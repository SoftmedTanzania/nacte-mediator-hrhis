# Human Resource Health Infomation System (HRHIS) - The National Council for Technical Education (NACTE) Mediator

[![Java CI Badge](https://github.com/SoftmedTanzania/nacte-mediator-hrhis/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/SoftmedTanzania/nacte-mediator-hrhis/actions?query=workflow%3A%22Java+CI+with+Maven%22)
[![Coverage Status](https://coveralls.io/repos/github/SoftmedTanzania/nacte-mediator-hrhis/badge.svg?branch=development)](https://coveralls.io/github/SoftmedTanzania/nacte-mediator-hrhis?branch=development)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7b05b97def254b6591128789f6a643de)](https://www.codacy.com/gh/SoftmedTanzania/nacte-mediator-hrhis/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SoftmedTanzania/nacte-mediator-hrhis&amp;utm_campaign=Badge_Grade)

An [OpenHIM](http://openhim.org/) mediator for handling system integrations between HRHIS and NACTE.

## 1. Dev Requirements

1. Java 1.8
2. IntelliJ or Visual Studio Code
3. Maven 3.6.3

## 2. Pre-Deployment

**The file contents of `mediator.properties` and `mediator-registration-info.json` must be changed before deployment**

This idea behind this mediator is to deploy the same JAR with a different set of configurations parameters based on the
system to connect.

### 3 Configuration Parameters

The configuration parameters specific to the mediator and destination system can be found at

`src/main/resources/mediator.properties`

```
    mediator.name=Nacte-Mediator-HRHIS
    mediator.host=localhost
    mediator.port=3016
    mediator.timeout=60000
    mediator.heartbeats=true
    
    core.scheme=openhim-scheme
    core.host=openhim-address
    core.api.port=8080
    core.api.user=root@openhim.org
    core.api.password=openhim-password
    
    destination.host=destination-system-address
    destination.api.port=destination-system-address-port
    destination.api.path=/destination-system-path
    destination.scheme=destination-system-scheme
    destination.authenticationToken=token
```

The configuration parameters specific to the mediator and the mediator's metadata can be found at

`src/main/resources/mediator-registration-info.json`

```
    {
      "urn": "urn:uuid:068583e0-6c57-11eb-af21-471e9f9cc89e",
      "version": "0.1.0",
      "name": "HRHIS to Nacte Mediator",
      "description": "This is an Openhim mediator that handles data sharing between HRHIS and Nacte",
      "endpoints": [
        {
          "name": "HRHIS to Nacte Mediator Route",
          "host": "localhost",
          "port": "3016",
          "path": "/nacte",
          "type": "http"
        }
      ],
      "defaultChannelConfig": [
        {
          "name": "HRHIS to Nacte Mediator",
          "urlPattern": "^/nacte$",
          "type": "http",
          "allow": ["hrhistonactemediator"],
          "routes": [
            {
              "name": "HRHIS to Nacte Mediator Route",
              "host": "localhost",
              "port": "3016",
              "path": "/nacte",
              "type": "http",
              "primary": "true"
            }
          ]
        }
      ],
      "configDefs": [
        {
          "param": "destinationConnectionProperties",
          "displayName": "Destination Connection Properties",
          "description": "Configuration to set the hostname, port and path for the destination server",
          "type": "struct",
          "template": [
            {
              "param": "destinationHost",
              "displayName": "Destination Host Name",
              "description": "IP address/hostname of the destination server. e.g 192.168.1.1",
              "type": "string"
            },
            {
              "param": "destinationPort",
              "displayName": "Destination Port Number",
              "description": "The port number of the destination server. e.g 8080",
              "type": "number"
            },
            {
              "param": "destinationPath",
              "displayName": "Destination Path",
              "description": "The destination path for receiving data from the HIM. eg /hdr",
              "type": "string"
            },
            {
              "param": "destinationScheme",
              "displayName": "Destination Scheme",
              "description": "Whether the destination is using HTTP or HTTPS requests.",
              "type": "option",
              "values": [
                "http",
                "https"
              ]
            },
            {
              "param": "destinationAuthenticationToken",
              "displayName": "Destination Authentication Token",
              "description": "The authentication token for the destination system.",
              "type": "string"
            }
          ]
        }
      ]
    }

```

## 4. Deployment

To build and run the mediator after performing the above configurations, run the following

```
  mvn clean package -DskipTests=true -e source:jar javadoc:jar
  java -jar target/nacte-mediator-hrhis-<version>-jar-with-dependencies.jar
```
