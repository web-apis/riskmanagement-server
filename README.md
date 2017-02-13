Example code for Interface Representation Patterns (TODO rename to title of the paper)

## Overview

This minimal example shows ...

## Running The Application

To test the example application run the following commands.

* To start the server run:

	```mvn exec:java -Dexec.mainClass="com.premierquotes.RiskManagementServerApplication" -Dexec.args="server config.yml"```

* To package the example run:

	```mvn package```

* To run the packaged server.

	```java -jar target/riskmanagement-1.0-SNAPSHOT.jar server config.yml```

* By default (you can change this in the config.yml) the server will listen to port 8080 and 8081. To create a new claim run:

	```curl http://localhost:8080/claims -H "Content-Type: application/json" -d '{"timeOfIncident":"2017-02-01T10:00:00", "amount": 2000 }'```
	
	```curl -X GET http://localhost:8080/claims```


## Eclipse Setup

If you want to explore the code in Eclipse, first clone the repository. Then run ```File -> Import -> Maven -> Existing Maven Projects``` and select the folder with your local clone of the repository. 

* To run the server, create a Java launch configuration for the ```RiskManagementServerApplication``` Main-Class and specify ```server config.yml``` as arguments. You can then run the server.
