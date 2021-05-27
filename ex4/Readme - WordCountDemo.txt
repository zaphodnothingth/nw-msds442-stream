1. All required tools must be installed in the directory:
	- C:\Workbench


2. Startup Zookeeper
	- Start a terminal/window on your computer 
	- Change directory to:
		 C:\Workbench
	- Execute from the command line  the script: 
		env-setup-for-kafka-2.6.bat
	- Execute the following command to verify your environment Java SDK setup
		java  -version
	- Change directory to:
		 C:\Workbench\kafka_2.13-2.6.0
	- Execute from the command line the following command: 
		.\bin\windows\zookeeper-server-start.bat config\zookeeper.properties
			

3. Startup Kafka Server
	- Start a terminal/window on your computer 
	- Change directory to:
		 C:\Workbench
	- Execute from the command line  the script: 
		env-setup-for-kafka-2.6.bat
	- Execute the following command to verify your environment Java SDK setup
		java  -version
	- Change directory to:
		 C:\Workbench\kafka_2.13-2.6.0
	- Execute from the command line the following command: 
		.\bin\windows\kafka-server-start.bat config\server.properties


4. Prepare input topic and start Kafka producer
	- Start a terminal/window on your computer 
	- Change directory to:
		 C:\Workbench
	- Execute from the command line  the script: 
		env-setup-for-kafka-2.6.bat
	- Execute the following command to verify your environment Java SDK setup
		java  -version
	- Change directory to:
		 C:\Workbench\kafka_2.13-2.6.0
	- Execute from the command line the following command: 
		.\bin\windows\kafka-topics.bat --create  --bootstrap-server localhost:9092  --replication-factor 1 --partitions 1 --topic streams-plaintext-input
	- Execute from the command line the following command: 
		.\bin\windows\kafka-topics.bat --create  --bootstrap-server localhost:9092 --replication-factor 1  --partitions 1 --topic streams-wordcount-output --config cleanup.policy=compact
	- Execute from the command line the following command: 
		.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --describe


5. Start the Wordcount Application
	- Start a terminal/window on your computer 
	- Change directory to:
		 C:\Workbench
	- Execute from the command line  the script: 
		env-setup-for-kafka-2.6.bat
	- Execute the following command to verify your environment Java SDK setup
		java  -version
	- Change directory to:
		 C:\Workbench\kafka_2.13-2.6.0
	- Execute from the command line the following command: 
		.\bin\windows\kafka-run-class.bat org.apache.kafka.streams.examples.wordcount.WordCountDemo


5. Start the Producer in its own Console
	- Start a terminal/window on your computer 
	- Change directory to:
		 C:\Workbench
	- Execute from the command line  the script: 
		env-setup-for-kafka-2.6.bat
	- Execute the following command to verify your environment Java SDK setup
		java  -version
	- Change directory to:
		 C:\Workbench\kafka_2.13-2.6.0
	- Execute from the command line the following command: 
		.\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic streams-plaintext-input

6. Start the Consumer in its own Console
	- Start a terminal/window on your computer 
	- Change directory to:
		 C:\Workbench
	- Execute from the command line  the script: 
		env-setup-for-kafka-2.6.bat
	- Execute the following command to verify your environment Java SDK setup
		java  -version
	- Change directory to:
		 C:\Workbench\kafka_2.13-2.6.0
	- Execute from the command line the following command: 
		.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092  --topic streams-wordcount-output  --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer



7. Terminate Kafka
    	- Stop the producer and consumer clients with Ctrl-C.
    	- Stop the Kafka broker with Ctrl-C.
    	- Stop the ZooKeeper server with Ctrl-C.


