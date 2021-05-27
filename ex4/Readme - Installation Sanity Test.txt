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


4. Create a topic to store your events
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
 		.\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092



5. Write some events to the  topic 
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
		.\bin\windows\kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092


6. Read  the events
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
		.\bin\windows\kafka-console-consumer.bat --topic quickstart-events --from-beginning --bootstrap-server localhost:9092


7. Terminate Kafka
    	- Stop the producer and consumer clients with Ctrl-C.
    	- Stop the Kafka broker with Ctrl-C.
    	- Stop the ZooKeeper server with Ctrl-C.


