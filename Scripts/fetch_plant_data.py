import requests
import json
import time
from datetime import datetime

def fetch_crop_data(crop_name):
    base_url = "https://openfarm.cc/api/v1/crops"
    search_url = f"{base_url}?filter={crop_name}"
    
    try:
        response = requests.get(search_url)
        data = response.json()
        
        if not data.get('data'):
            print(f"No data found for {crop_name}")
            return None
        
        # Get the first result
        crop = data['data'][0]
        crop_id = crop['id']
        
        # Fetch detailed information
        detail_url = f"{base_url}/{crop_id}"
        detail_response = requests.get(detail_url)
        detail_data = detail_response.json()['data']
        
        attributes = detail_data['attributes']
        
        # Extract all relevant information
        result = {
            "name": attributes.get('name', ''),
            "scientificName": attributes.get('binomial_name', ''),
            "description": attributes.get('description', ''),
            "sunRequirement": attributes.get('sun_requirements', ''),
            "soilRequirement": attributes.get('soil_requirements', ''),
            "waterRequirement": attributes.get('water_requirements', ''),
            "sowingInstructions": attributes.get('sowing_method', ''),
            "growingInstructions": attributes.get('growing_instructions', ''),
            "harvestInstructions": attributes.get('harvesting_instructions', ''),
            "plantingDepth": str(attributes.get('depth_requirements', {}).get('value', '')),
            "plantingDistance": str(attributes.get('spread_requirements', {}).get('value', '')),
            "rowSpacing": str(attributes.get('row_spacing_requirements', {}).get('value', '')),
            "height": str(attributes.get('height_requirements', {}).get('value', '')),
            "daysToMaturity": attributes.get('days_to_maturity'),
            "companionPlants": ', '.join(attributes.get('companions', [])),
            "avoidPlants": ', '.join(attributes.get('incompatible_plants', [])),
            "tags": ', '.join(attributes.get('tags', [])),
            "harvestPeriod": attributes.get('harvesting_time', ''),
            "variety": attributes.get('variety', ''),
            "category": attributes.get('crop_type', ''),
            "hardiness": f"Zone {attributes.get('minimum_temperature_requirements', {}).get('zone', '')}"
        }
        
        # Clean up empty values
        return {k: v for k, v in result.items() if v not in ['', None, 'None']}
        
    except Exception as e:
        print(f"Error fetching data for {crop_name}: {str(e)}")
        return None

def save_to_json(crops_data, filename):
    with open(filename, 'w', encoding='utf-8') as f:
        json.dump(crops_data, f, ensure_ascii=False, indent=2)

def main():
    # Lista över grödor att hämta information om
    crops = [
        "Tomato", "Carrot", "Potato", "Cucumber", "Lettuce",
        "Pepper", "Onion", "Garlic", "Basil", "Strawberry",
        "Pea", "Bean", "Corn", "Squash", "Radish",
        "Spinach", "Broccoli", "Cauliflower", "Cabbage", "Kale",
        "Beet", "Eggplant", "Watermelon", "Cantaloupe", "Pumpkin",
        "Zucchini", "Mint", "Oregano", "Thyme", "Rosemary",
        "Cilantro", "Parsley", "Dill", "Chives", "Sage",
        "Arugula", "Swiss Chard", "Asparagus", "Brussels Sprouts", "Celery"
    ]
    
    all_crops_data = {}
    
    for crop in crops:
        print(f"Fetching data for {crop}...")
        crop_data = fetch_crop_data(crop)
        if crop_data:
            all_crops_data[crop] = crop_data
        time.sleep(1)  # Be nice to the API
    
    # Save to a JSON file with timestamp
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    filename = f"plant_data_{timestamp}.json"
    save_to_json(all_crops_data, filename)
    print(f"Data saved to {filename}")

if __name__ == "__main__":
    main() 