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
		.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
			

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
		.\bin\windows\kafka-server-start.bat .\config\server.properties


4. Startup your Real-Time Stream Application
	- Start a terminal/window on your computer 
	- Change directory to:
		 C:\Workbench
	- Execute from the command line  the script: 
		env-setup-for-kafka-2.6.bat
	- Execute the following command to verify your environment Java SDK setup
		java  -version
	- Change directory to:
		C:\Workbench\kafka-streams-in-action-master-new
	- Execute from the command line the following command: 
		gradle wrapper
	- Execute from the command line the following command: 
		.\gradlew runZmartAdvancedChapter_3


