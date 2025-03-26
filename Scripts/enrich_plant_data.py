import requests
import json
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import os
from datetime import datetime
import time
from typing import Dict, Any, Optional
import logging

# Konfigurera logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler('plant_enrichment.log'),
        logging.StreamHandler()
    ]
)

# Initiera Firebase
script_dir = os.path.dirname(os.path.abspath(__file__))
service_account_path = os.path.join(script_dir, 'serviceAccountKey.json')
cred = credentials.Certificate(service_account_path)
firebase_admin.initialize_app(cred)
db = firestore.client()

class PlantDataEnricher:
    def __init__(self):
        self.openfarm_base_url = "https://openfarm.cc/api/v1/crops"
        self.rate_limit_delay = 1  # Sekunder mellan API-anrop
    
    def fetch_from_openfarm(self, plant_name: str) -> Optional[Dict[str, Any]]:
        """Hämta data från OpenFarm API"""
        try:
            search_url = f"{self.openfarm_base_url}?filter={plant_name}"
            response = requests.get(search_url)
            response.raise_for_status()
            data = response.json()
            
            if 'data' in data and len(data['data']) > 0:
                crop_data = data['data'][0]['attributes']
                return {
                    'binomialName': crop_data.get('binomial_name', ''),
                    'description': crop_data.get('description', ''),
                    'sunRequirements': crop_data.get('sun_requirements', ''),
                    'sowingMethod': crop_data.get('sowing_method', ''),
                    'spreadingWidth': crop_data.get('spread', 0),
                    'rowSpacing': crop_data.get('row_spacing', 0),
                    'heightWidth': crop_data.get('height', 0),
                    'daysToMaturity': crop_data.get('growing_degree_days', 0),
                    'dataSource': 'OpenFarm'
                }
        except Exception as e:
            logging.error(f"Error fetching OpenFarm data for {plant_name}: {str(e)}")
        return None

    def merge_plant_data(self, existing_data: Dict[str, Any], new_data: Dict[str, Any]) -> Dict[str, Any]:
        """Sammanfoga existerande data med ny data, behåll existerande värden om de finns"""
        merged_data = existing_data.copy()
        
        for key, value in new_data.items():
            if key not in merged_data or not merged_data[key]:
                merged_data[key] = value
            elif isinstance(value, (int, float)) and merged_data[key] == 0:
                merged_data[key] = value
        
        merged_data['updatedAt'] = datetime.now()
        return merged_data

    def update_firebase_document(self, doc_id: str, data: Dict[str, Any]) -> bool:
        """Uppdatera ett dokument i Firestore"""
        try:
            doc_ref = db.collection('plants').document(doc_id)
            doc_ref.update(data)
            logging.info(f"Successfully updated document {doc_id}")
            return True
        except Exception as e:
            logging.error(f"Error updating document {doc_id}: {str(e)}")
            return False

def main():
    enricher = PlantDataEnricher()
    
    # Hämta alla plantor från Firestore
    plants_ref = db.collection('plants')
    docs = plants_ref.stream()
    
    total_plants = 0
    updated_plants = 0
    
    for doc in docs:
        total_plants += 1
        plant_data = doc.to_dict()
        plant_name = plant_data.get('name', '')
        
        logging.info(f"Processing plant: {plant_name}")
        
        # Hämta data från OpenFarm
        openfarm_data = enricher.fetch_from_openfarm(plant_name)
        
        if openfarm_data:
            # Sammanfoga data
            merged_data = enricher.merge_plant_data(plant_data, openfarm_data)
            
            # Uppdatera i Firestore
            if enricher.update_firebase_document(doc.id, merged_data):
                updated_plants += 1
        
        # Vänta mellan API-anrop för att respektera rate limits
        time.sleep(enricher.rate_limit_delay)
    
    logging.info(f"Processing complete. Processed {total_plants} plants, updated {updated_plants}.")

if __name__ == "__main__":
    main() 