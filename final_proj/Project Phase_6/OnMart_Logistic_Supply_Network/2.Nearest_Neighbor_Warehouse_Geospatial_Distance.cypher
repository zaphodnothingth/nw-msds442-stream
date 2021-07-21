
// First you need to create the Logistics and Supply Network for OnMart using Neo4j


// After that read how the distance is calculated in Neo4j
// Here is the URL:
// https://neo4j.com/docs/cypher-manual/current/functions/spatial/#functions-distance


MATCH (Warehouse_4:Warehouse {facility_id: "Warehouse_4"})
MATCH (Warehouse_5:Warehouse {facility_id: "Warehouse_5"})
RETURN distance(Point({latitude:Warehouse_4.latitude, longitude:Warehouse_4.longitude}), Point({latitude:Warehouse_5.latitude, longitude:Warehouse_5.longitude}))

