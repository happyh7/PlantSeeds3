import requests
import json
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import uuid
from datetime import datetime
import os

# Få den absoluta sökvägen till scripts-mappen
script_dir = os.path.dirname(os.path.abspath(__file__))
service_account_path = os.path.join(script_dir, 'serviceAccountKey.json')

# Initialisera Firebase Admin SDK
cred = credentials.Certificate(service_account_path)
firebase_admin.initialize_app(cred)
db = firestore.client()

def fetch_plant_data(crop_name):
    base_url = "https://openfarm.cc/api/v1/crops"
    search_url = f"{base_url}?filter={crop_name}"
    
    try:
        response = requests.get(search_url)
        response.raise_for_status()
        data = response.json()
        
        if 'data' in data and len(data['data']) > 0:
            crop_data = data['data'][0]['attributes']
            
            # Skapa ett strukturerat data-objekt
            plant_info = {
                'id': str(uuid.uuid4()),  # Generera ett unikt ID
                'name': crop_data.get('name', ''),
                'binomialName': crop_data.get('binomial_name', ''),
                'description': crop_data.get('description', ''),
                'sunRequirements': crop_data.get('sun_requirements', ''),
                'sowingMethod': crop_data.get('sowing_method', ''),
                'spreadingWidth': crop_data.get('spread', 0),
                'rowSpacing': crop_data.get('row_spacing', 0),
                'heightWidth': crop_data.get('height', 0),
                'daysToMaturity': crop_data.get('growing_degree_days', 0),
                'createdAt': datetime.now(),
                'updatedAt': datetime.now()
            }
            
            return plant_info
    except Exception as e:
        print(f"Error fetching data for {crop_name}: {str(e)}")
        return None

def upload_to_firebase(plant_data):
    try:
        # Lägg till data i 'plants' collection
        doc_ref = db.collection('plants').document(plant_data['id'])
        doc_ref.set(plant_data)
        print(f"Successfully uploaded {plant_data['name']} to Firebase")
    except Exception as e:
        print(f"Error uploading to Firebase: {str(e)}")

def main():
    # Lista över växter att hämta data för
    plants = [
        "tomato", "cucumber", "carrot", "potato", "onion",
        "lettuce", "spinach", "peas", "beans", "corn",
        "cabbage", "broccoli", "cauliflower", "pepper", "eggplant",
        "zucchini", "pumpkin", "radish", "beet", "garlic",
        "basil", "parsley", "cilantro", "dill", "thyme",
        "oregano", "mint", "sage", "rosemary", "chives"
    ]
    
    for plant in plants:
        print(f"Fetching data for {plant}...")
        plant_data = fetch_plant_data(plant)
        
        if plant_data:
            upload_to_firebase(plant_data)
        
        print("---")

if __name__ == "__main__":
    main() 