// delete exisiting graph
match (n) detach delete n;


// verify you have no nodes/relationships in your graph
match (n) return n;


# Read the following docs to see how to specify reading/load LOCAL CSV file
# https://neo4j.com/developer/kb/import-csv-locations/

# the CSV file by default is placed under your local directory installation:
# Example:
# C:\Users\Atef\AppData\Local\Neo4j\Relate\Data\dbmss\dbms-4f353b66-c5a5-4ce2-a7b6-92f0aba83b3b\import



// Create Warehouses

WITH "file:///" AS base
WITH base + "onMartSuperStore_Warehouses_nodes.csv" AS uri
LOAD CSV WITH HEADERS FROM uri  AS row
MERGE (warehouse:Warehouse {facility_id:row.Facility_ID})
SET warehouse.latitude = toFloat(row.Latitude),
    warehouse.longitude = toFloat(row.Longitude),
	warehouse.city = row.City,
	warehouse.state = row.State,
	warehouse.zip = toInteger(row.Zip),
	warehouse.facility_id = row.Facility_ID;
	


// Create Distribution Centers

WITH "file:///" AS base
WITH base + "onMartSuperStore_Distribution_Centers_nodes.csv" AS uri
LOAD CSV WITH HEADERS FROM uri  AS row
MERGE (distribution_center:Distribution_Center {facility_id:row.Facility_ID})
SET distribution_center.latitude = toFloat(row.Latitude),
    distribution_center.longitude = toFloat(row.Longitude),
	distribution_center.city = row.City,
	distribution_center.state = row.State,
	distribution_center.zip = toInteger(row.Zip),
	distribution_center.facility_id = row.Facility_ID;
	


// Create Countries

WITH "file:///" AS base
WITH base + "onMartSuperStore_Country_nodes.csv" AS uri
LOAD CSV WITH HEADERS FROM uri  AS row
MERGE (country:Country {country:row.Country})
SET country.latitude = toFloat(row.Latitude),
    country.longitude = toFloat(row.Longitude),
	country.country = row.Country,
	country.zip = toInteger(row.Zip);



// Create States

WITH "file:///" AS base
WITH base + "onMartSuperStore_State_nodes.csv" AS uri
LOAD CSV WITH HEADERS FROM uri  AS row
MERGE (state:State {state:row.State})
SET state.latitude = toFloat(row.Latitude),
    state.longitude = toFloat(row.Longitude),
	state.state = row.State,
	state.zip = toInteger(row.Zip);



// Create Cities

WITH "file:///" AS base
WITH base + "onMartSuperStore_City_nodes.csv" AS uri
LOAD CSV WITH HEADERS FROM uri  AS row
MERGE (city:City {city:row.City})
SET city.latitude = toFloat(row.Latitude),
    city.longitude = toFloat(row.Longitude),
	city.city = row.City,
	city.state = row.State,
	city.zip = toInteger(row.Zip);
	
	
	
// Create Zip Codes

WITH "file:///" AS base
WITH base + "onMartSuperStore_Zip_Code_nodes.csv" AS uri
LOAD CSV WITH HEADERS FROM uri  AS row
MERGE (zip_code:Zip_Code {zip:row.Zip})
SET zip_code.latitude = toFloat(row.Latitude),
    zip_code.longitude = toFloat(row.Longitude),
	zip_code.city = row.City,
	zip_code.state = row.State,
	zip_code.zip = toInteger(row.Zip);
	


// Create State IN_COUNTRY Country relationship

WITH "file:///" AS base
WITH base + "onMartSuperStore_State_IN_COUNTRY_Country_relationships.csv" AS uri
LOAD CSV WITH HEADERS FROM uri AS row
MATCH (origin:State {state: row.State})
MATCH (destination:Country {country: row.Country})
MERGE (origin)-[:IN_COUNTRY]->(destination);




// Create City IN_STATE State relationship

WITH "file:///" AS base
WITH base + "onMartSuperStore_City_IN_STATE_State_relationships.csv" AS uri
LOAD CSV WITH HEADERS FROM uri AS row
MATCH (origin:City {city: row.City})
MATCH (destination:State {state: row.State})
MERGE (origin)-[:IN_STATE]->(destination);

	
	
// Create Warehouse SUPPLIES Distribution_Center relationship

WITH "file:///" AS base
WITH base + "onMartSuperStore_warehouse_SUPPLIES_distributionCenter_relationships.csv" AS uri
LOAD CSV WITH HEADERS FROM uri AS row
MATCH (origin:Warehouse {facility_id: row.Warehouse_ID})
MATCH (destination:Distribution_Center {facility_id: row.DistributionCenter_ID})
MERGE (origin)-[:SUPPLIES]->(destination);


// Create Warehouse LOCATED_IN City  relationship

WITH "file:///" AS base
WITH base + "onMartSuperStore_Warehouses_LOCATED_IN_City.csv" AS uri
LOAD CSV WITH HEADERS FROM uri AS row
MATCH (origin:Warehouse {facility_id: row.Facility_ID})
MATCH (destination:City {city: row.City})
MERGE (origin)-[:LOCATED_IN]->(destination);



// Create SHIPS_TO relationship

WITH "file:///" AS base
WITH base + "onMartSuperStore_distributionCenter_SHIPS_TO_zip_code_relationships.csv" AS uri
LOAD CSV WITH HEADERS FROM uri AS row
MATCH (origin:Distribution_Center {facility_id: row.DistributionCenter_ID})
MATCH (destination:Zip_Code {zip: toInteger(row.Zip)})
MERGE (origin)-[:SHIPS_TO]->(destination);


