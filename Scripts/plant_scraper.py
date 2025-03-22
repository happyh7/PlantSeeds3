import requests
import json
import logging
import os
from time import sleep
from datetime import datetime
import backoff
from requests.exceptions import RequestException
from bs4 import BeautifulSoup
import re

# Konfigurera loggning
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler(f'plant_scraper_{datetime.now().strftime("%Y%m%d")}.log'),
        logging.StreamHandler()
    ]
)

class MultiSourcePlantScraper:
    def __init__(self):
        self.session = requests.Session()
        self.plants = []
        self.categories = {
            "vegetables": {
                "name": "Grönsaker",
                "terms": ["vegetable", "vegetables", "grönsaker", "grönsak"],
                "subcategories": [
                    "potato", "tomato", "carrot", "onion", "garlic",
                    "potatis", "tomat", "morot", "lök", "vitlök"
                ]
            },
            "herbs": {
                "name": "Kryddor",
                "terms": ["herb", "herbs", "kryddor", "kryddväxter"],
                "subcategories": [
                    "basil", "parsley", "thyme", "sage", "mint",
                    "basilika", "persilja", "timjan", "salvia", "mynta"
                ]
            },
            "fruits": {
                "name": "Frukt",
                "terms": ["fruit", "fruits", "frukt", "bär"],
                "subcategories": [
                    "apple", "pear", "plum", "berry", "strawberry",
                    "äpple", "päron", "plommon", "bär", "jordgubb"
                ]
            },
            "flowers": {
                "name": "Blommor",
                "terms": ["flower", "flowers", "blommor", "blomma"],
                "subcategories": [
                    "rose", "sunflower", "daisy", "tulip", "dahlia",
                    "ros", "solros", "tusensköna", "tulpan", "dahlia"
                ]
            }
        }
        
        # Headers för att simulera en webbläsare
        self.headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
        }

    @backoff.on_exception(backoff.expo, RequestException, max_tries=3)
    def make_request(self, url, params=None):
        """Gör ett API-anrop med återförsök vid fel."""
        try:
            response = self.session.get(url, params=params, headers=self.headers, timeout=10)
            response.raise_for_status()
            return response
        except Exception as e:
            logging.error(f"API-anrop misslyckades för {url}: {e}")
            return None

    def get_openfarm_data(self, plant_name):
        """Hämtar data från OpenFarm."""
        url = f"https://openfarm.cc/api/v1/crops"
        params = {"filter": plant_name}
        response = self.make_request(url, params)
        
        if response and response.status_code == 200:
            data = response.json()
            if data.get('data'):
                crop_id = data['data'][0].get('id')
                if crop_id:
                    details_url = f"https://openfarm.cc/api/v1/crops/{crop_id}"
                    details_response = self.make_request(details_url)
                    if details_response and details_response.status_code == 200:
                        return details_response.json().get('data', {})
        return None

    def get_gardenia_data(self, plant_name):
        """Hämtar data från Gardenia.net."""
        search_url = f"https://www.gardenia.net/search?q={plant_name}"
        response = self.make_request(search_url)
        
        if not response:
            return None
            
        soup = BeautifulSoup(response.text, 'html.parser')
        plant_data = {}
        
        # Försök hitta första relevanta plantresultatet
        plant_link = soup.find('a', class_='plant-result')
        if plant_link:
            plant_url = f"https://www.gardenia.net{plant_link['href']}"
            plant_response = self.make_request(plant_url)
            
            if plant_response:
                plant_soup = BeautifulSoup(plant_response.text, 'html.parser')
                
                # Extrahera odlingsinformation
                growing_info = plant_soup.find('div', class_='growing-info')
                if growing_info:
                    plant_data.update({
                        "hardiness_zones": self._extract_text(growing_info, '.hardiness'),
                        "sun_exposure": self._extract_text(growing_info, '.sun-exposure'),
                        "soil_type": self._extract_text(growing_info, '.soil-type'),
                        "soil_ph": self._extract_text(growing_info, '.soil-ph'),
                        "bloom_time": self._extract_text(growing_info, '.bloom-time'),
                        "height": self._extract_text(growing_info, '.height'),
                        "spread": self._extract_text(growing_info, '.spread'),
                        "water_needs": self._extract_text(growing_info, '.water-needs'),
                        "maintenance": self._extract_text(growing_info, '.maintenance'),
                        "garden_styles": self._extract_text(growing_info, '.garden-styles'),
                        "garden_uses": self._extract_text(growing_info, '.garden-uses')
                    })
        
        return plant_data

    def get_usda_data(self, plant_name):
        """Hämtar data från USDA Plants Database."""
        search_url = f"https://plants.sc.egov.usda.gov/api/plants/search"
        params = {
            "q": plant_name,
            "pageSize": 1
        }
        response = self.make_request(search_url, params)
        
        if response and response.status_code == 200:
            data = response.json()
            if data.get('data'):
                plant = data['data'][0]
                return {
                    "scientific_name": plant.get('scientificName'),
                    "common_names": plant.get('commonNames', []),
                    "family": plant.get('family'),
                    "growth_habits": plant.get('growthHabits', []),
                    "native_status": plant.get('nativeStatus'),
                    "ecological_info": plant.get('ecologicalInformation', {})
                }
        return None

    def _extract_text(self, soup, selector):
        """Hjälpfunktion för att extrahera text från HTML."""
        element = soup.select_one(selector)
        return element.text.strip() if element else ""

    def merge_plant_data(self, plant_name, category=None, search_term=None):
        """Kombinerar data från alla källor."""
        logging.info(f"Hämtar information om {plant_name} från flera källor...")
        
        # Hämta data från alla källor
        openfarm_data = self.get_openfarm_data(plant_name)
        gardenia_data = self.get_gardenia_data(plant_name)
        usda_data = self.get_usda_data(plant_name)
        
        if not openfarm_data and not gardenia_data and not usda_data:
            logging.warning(f"Ingen information hittades för {plant_name}")
            return None
            
        # Kombinera all information
        plant_data = {
            "id": openfarm_data.get('id') if openfarm_data else None,
            "name": plant_name,
            "category": category,
            "search_term": search_term,
            
            # Källor
            "sources": {
                "openfarm": bool(openfarm_data),
                "gardenia": bool(gardenia_data),
                "usda": bool(usda_data),
                "last_updated": datetime.now().isoformat()
            },
            
            # Grundläggande information
            "basic_info": {
                "common_names": usda_data.get('common_names', []) if usda_data else [],
                "scientific_name": (
                    usda_data.get('scientific_name') if usda_data else 
                    openfarm_data.get('attributes', {}).get('binomial_name') if openfarm_data else 
                    None
                ),
                "family": usda_data.get('family') if usda_data else None,
                "description": openfarm_data.get('attributes', {}).get('description') if openfarm_data else None
            },
            
            # Växtförhållanden
            "growing_conditions": {
                "hardiness_zones": gardenia_data.get('hardiness_zones'),
                "sun_requirements": (
                    openfarm_data.get('attributes', {}).get('sun_requirements') if openfarm_data else 
                    gardenia_data.get('sun_exposure')
                ),
                "soil": {
                    "type": gardenia_data.get('soil_type'),
                    "ph": gardenia_data.get('soil_ph'),
                    "drainage": openfarm_data.get('attributes', {}).get('soil_drainage') if openfarm_data else None
                },
                "temperature": {
                    "optimal": openfarm_data.get('attributes', {}).get('optimal_temperature') if openfarm_data else None,
                    "minimum": None,  # Kan fyllas i från andra källor
                    "maximum": None   # Kan fyllas i från andra källor
                }
            },
            
            # Planteringsdetaljer
            "planting_details": {
                "sowing": {
                    "method": openfarm_data.get('attributes', {}).get('sowing_method') if openfarm_data else None,
                    "depth": openfarm_data.get('attributes', {}).get('sowing_depth') if openfarm_data else None,
                    "spacing": {
                        "rows": openfarm_data.get('attributes', {}).get('row_spacing') if openfarm_data else None,
                        "plants": gardenia_data.get('spread')
                    }
                },
                "dimensions": {
                    "height": gardenia_data.get('height'),
                    "spread": gardenia_data.get('spread')
                }
            },
            
            # Tidslinje
            "timeline": {
                "sowing_season": openfarm_data.get('attributes', {}).get('sowing_season') if openfarm_data else None,
                "bloom_time": gardenia_data.get('bloom_time'),
                "germination": {
                    "minimum_days": openfarm_data.get('attributes', {}).get('germination_days_min') if openfarm_data else None,
                    "maximum_days": openfarm_data.get('attributes', {}).get('germination_days_max') if openfarm_data else None
                },
                "maturity": {
                    "days": openfarm_data.get('attributes', {}).get('maturity_days') if openfarm_data else None,
                    "indicators": openfarm_data.get('attributes', {}).get('maturity_indicators') if openfarm_data else None
                }
            },
            
            # Skötsel
            "care": {
                "water_needs": gardenia_data.get('water_needs'),
                "maintenance_level": gardenia_data.get('maintenance'),
                "pruning": openfarm_data.get('attributes', {}).get('pruning_method') if openfarm_data else None,
                "fertilizer": openfarm_data.get('attributes', {}).get('fertilizer_requirements') if openfarm_data else None,
                "pests": openfarm_data.get('attributes', {}).get('pest_susceptibility') if openfarm_data else None,
                "diseases": openfarm_data.get('attributes', {}).get('disease_susceptibility') if openfarm_data else None
            },
            
            # Användning
            "uses": {
                "garden_styles": gardenia_data.get('garden_styles'),
                "garden_uses": gardenia_data.get('garden_uses'),
                "culinary": openfarm_data.get('attributes', {}).get('culinary_uses') if openfarm_data else None,
                "medicinal": openfarm_data.get('attributes', {}).get('medicinal_uses') if openfarm_data else None
            }
        }
        
        return plant_data

    def search_plants(self, category):
        """Söker efter växter i en specifik kategori."""
        if category not in self.categories:
            logging.error(f"Ogiltig kategori: {category}")
            return
            
        category_data = self.categories[category]
        logging.info(f"Söker efter växter i kategorin: {category_data['name']}")
        
        # Sök genom alla söktermer och underkategorier
        for search_term in category_data['terms'] + category_data['subcategories']:
            plant_data = self.merge_plant_data(search_term, category, search_term)
            if plant_data:
                self.plants.append(plant_data)
                logging.info(f"Lade till information om {search_term}")
            sleep(1)  # Paus mellan anrop för att inte överbelasta API:erna

    def save_plants(self, filename="plants.json"):
        """Sparar insamlad data till en JSON-fil."""
        try:
            with open(filename, 'w', encoding='utf-8') as f:
                json.dump(self.plants, f, ensure_ascii=False, indent=2)
            logging.info(f"Sparade {len(self.plants)} växter till {filename}")
        except Exception as e:
            logging.error(f"Kunde inte spara data till {filename}: {e}")

def main():
    """Huvudfunktion för att köra scrapern."""
    scraper = MultiSourcePlantScraper()
    
    # Skrapa data för alla kategorier
    for category in scraper.categories:
        scraper.search_plants(category)
        
    # Spara resultaten
    scraper.save_plants()

if __name__ == "__main__":
    main() 