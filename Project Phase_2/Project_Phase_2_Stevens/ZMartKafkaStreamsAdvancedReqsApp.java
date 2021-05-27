/*
 * Copyright 2016 Bill Bejeck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bbejeck.chapter_3;

import bbejeck.chapter_3.service.SecurityDBService;
import bbejeck.chapter_3.LogsBuilder;
import bbejeck.clients.producer.MockDataProducer;
import bbejeck.model.Purchase;
import bbejeck.model.PurchasePattern;
import bbejeck.model.RewardAccumulator;
import bbejeck.util.serde.StreamsSerdes;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.javafaker.Faker;
import java.util.Properties;

import bbejeck.util.datagen.DataGenerator;


@SuppressWarnings("unchecked")
public class ZMartKafkaStreamsAdvancedReqsApp {

    private static final Logger LOG = LoggerFactory.getLogger(ZMartKafkaStreamsAdvancedReqsApp.class);

    public static void main(String[] args) throws Exception {
		
		// Create HashMap that has all info indexed on the zipcodes served by  
		// the warehouses and distrubution centers in the supply chain network for OnMart
		
		DataGenerator.createWdcHashmap();
		Thread.sleep(1000*1);
		

        StreamsConfig streamsConfig = new StreamsConfig(getProperties());

        Serde<Purchase> purchaseSerde = StreamsSerdes.PurchaseSerde();
		
        Serde<PurchasePattern> purchasePatternSerde = StreamsSerdes.PurchasePatternSerde();
        Serde<RewardAccumulator> rewardAccumulatorSerde = StreamsSerdes.RewardAccumulatorSerde();
        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

		

        // previous requirements
        KStream<String,Purchase> purchaseKStream = builder.stream( "transactions", Consumed.with(stringSerde, purchaseSerde))
                .mapValues(p -> Purchase.builder(p).maskCreditCard().build());  


        KStream<String, PurchasePattern> patternKStream = purchaseKStream.mapValues(purchase -> PurchasePattern.builder(purchase).build());

        patternKStream.print( Printed.<String, PurchasePattern>toSysOut().withLabel("patterns"));
        patternKStream.to("patterns", Produced.with(stringSerde,purchasePatternSerde));


        KStream<String, RewardAccumulator> rewardsKStream = purchaseKStream.mapValues(purchase -> RewardAccumulator.builder(purchase).build());

        rewardsKStream.print(Printed.<String, RewardAccumulator>toSysOut().withLabel("rewards"));
        rewardsKStream.to("rewards", Produced.with(stringSerde,rewardAccumulatorSerde));



        // selecting a key for storage and filtering out low dollar purchases


        KeyValueMapper<String, Purchase, Long> purchaseDateAsKey = (key, purchase) -> purchase.getPurchaseDate().getTime();

        KStream<Long, Purchase> filteredKStream = purchaseKStream.filter((key, purchase) -> purchase.getPrice() > 5.00).selectKey(purchaseDateAsKey);

        filteredKStream.print(Printed.<Long, Purchase>toSysOut().withLabel("purchases"));
        filteredKStream.to("purchases", Produced.with(Serdes.Long(),purchaseSerde));



        // branching stream for separating out purchases in new departments to their own topics

        Predicate<String, Purchase> isElectronics = (key, purchase) -> purchase.getDepartment().equalsIgnoreCase("electronics");
		Predicate<String, Purchase> isFurniture = (key, purchase) -> purchase.getDepartment().equalsIgnoreCase("furniture");
		Predicate<String, Purchase> isOfficeSupplies = (key, purchase) -> purchase.getDepartment().equalsIgnoreCase("office supplies");
	
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		/////////////														////////////
		/////////////			ADD YOUR CODE HERE							////////////
		/////////////														////////////
		/////////////			Predicates for:								////////////
		/////////////			1. 	grocery									////////////
		Predicate<String, Purchase> isGrocery = (key, purchase) -> purchase.getDepartment().equalsIgnoreCase("grocery");
		/////////////			2. 	clothing								////////////	
		Predicate<String, Purchase> isClothing = (key, purchase) -> purchase.getDepartment().equalsIgnoreCase("clothing");	
		/////////////			3. 	sports									////////////
		Predicate<String, Purchase> isSports = (key, purchase) -> purchase.getDepartment().equalsIgnoreCase("sports");
		/////////////														////////////
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////		


        int electronics = 0;
		int furniture = 1;
		int officeSupplies = 2;
		int grocery = 3;
		int clothing = 4;
		int sports = 5;

        KStream<String, Purchase>[] kstreamByDept = purchaseKStream.branch(isElectronics, isFurniture, isOfficeSupplies, isGrocery, isClothing, isSports);

        kstreamByDept[electronics].to("electronics", Produced.with(stringSerde, purchaseSerde));
        kstreamByDept[electronics].print(Printed.<String, Purchase>toSysOut().withLabel("electronics"));
		
		kstreamByDept[furniture].to("furniture", Produced.with(stringSerde, purchaseSerde));
        kstreamByDept[furniture].print(Printed.<String, Purchase>toSysOut().withLabel("furniture"));
		
		kstreamByDept[officeSupplies].to("officeSupplies", Produced.with(stringSerde, purchaseSerde));
        kstreamByDept[officeSupplies].print(Printed.<String, Purchase>toSysOut().withLabel("officeSupplies"));
		


		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		/////////////														////////////
		/////////////			ADD YOUR CODE HERE							////////////
		/////////////														////////////
		/////////////			kstreamByDept for:							////////////
		/////////////			1. 	grocery									////////////
		
		kstreamByDept[grocery].to("grocery", Produced.with(stringSerde, purchaseSerde));
        kstreamByDept[grocery].print(Printed.<String, Purchase>toSysOut().withLabel("grocery")); // ajs
		/////////////			2. 	clothing								////////////	
		
		kstreamByDept[clothing].to("clothing", Produced.with(stringSerde, purchaseSerde));
        kstreamByDept[clothing].print(Printed.<String, Purchase>toSysOut().withLabel("clothing"));	
		/////////////			3. 	sports									////////////
		
		kstreamByDept[sports].to("sports", Produced.with(stringSerde, purchaseSerde));
        kstreamByDept[sports].print(Printed.<String, Purchase>toSysOut().withLabel("sports"));
		/////////////														////////////
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		


		ForeachAction<String, Purchase> purchaseForeachAction = (key, purchase) ->
                SecurityDBService.saveRecord(purchase.getPurchaseDate(), purchase.getOrderId(), purchase.getItemPurchased());

        // security Requirements to record transactions for certain order

        
        purchaseKStream.filter((key, purchase) -> purchase.getOrderId().equals("000000")).foreach(purchaseForeachAction);



		//buildHeadings
		LogsBuilder.buildHeadings(); 
		
		
		// buildOrdersLog, buildDeliveryOrdersLog and buildWDCLog CSV files and buildJsonLog - Flatened & Nested JSON files
		ForeachAction<String, Purchase> purcahseAction = (key, purchase) -> LogsBuilder.buildCSVLog(purchase);    
		purchaseKStream.foreach(purcahseAction);

		// Start the Producer Thread to create Purchases/Transactions in real-time
        // used only to produce data for this application, not typical usage
        MockDataProducer.producePurchaseData();
        
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(),streamsConfig);
        LOG.info("ZMart Advanced Requirements Kafka Streams Application Started");
        kafkaStreams.start();
		

		/****************************************************************************/
		/****************************************************************************/
		//		The following sleep must be adjusted 								//
		//		based on the number of transactions that are needed.		 		//
		//																			//		
		//		1000 			- sleep 1 second 									//
		//		1000 * 60 		- sleep 1 minutes 									//
		//		1000 * 60 * 60 	- sleep 1 hour 										//
		//																			//		
		//		MockDataProducer generates ROUGHLY 100 transactions/second 			//
		//																			//
		//		DataGenerator has the following constants that must be  			//
		//		adjusted for the needed number of transactions						//
		//				DEFAULT_NUM_PURCHASES										//
		//				NUM_ITERATIONS												//
		//																			//
		/****************************************************************************/
		/****************************************************************************/

	
		
        Thread.sleep(1000*60*10);
        LOG.info("Shutting down the Kafka Streams Application now");
        kafkaStreams.close();
        MockDataProducer.shutdown();
    }




    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "Example-Kafka-Streams-Job");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "streams-purchases");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testing-streams-api");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return props;
    }

}
