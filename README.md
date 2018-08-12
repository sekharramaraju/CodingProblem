# inmar
This appilicaiton to perform the CRUD operations on inventory data models and display the infographic tree of the data structures.
This document describes the environment setup for development, deployment and testing activities. 
The applcation is developed using the technologes Java, MySql and Angualr6.

## Development
The applicaiton is developed on Windows OS and giving installation instruction according to Windows OS.

### Software Requirements

1. Java (min v1.8)
2. Node.js
3. Yarn
4. JHipster
5. MySql

####Installation Instructions

1. Java 8: Download latest Java from Oracle and follow the installation instructions specified in the installation package. Make sure that JAVA_HOME is set. Open command prompt and execute

    java -version

    to make sure java is installed properly.
    
2. [Node.js][]: Node is used to run a development web server.

3. [Yarn][]: Yarn is used to manage the node dependencies. Download the yarn form https://yarnpkg.com/en/docs/install#windows-stable.
    
    yarn install v1.6.0

4. JHipster: Execute the following statements to install JHipster.

    npm install -g yo@2.0.4
    npm install -g generator-jhipster@5.1.0
    
    For more about Jhipster installation refer to documentation at https://www.jhipster.tech/documentation-archive/v5.1.0.

5. MySql : MySql is used as database to store the data. Install MySql from https://www.mysql.com/downloads/

### Building the applicatin

1. The application is using the MySql default username and password to connect to the db. Execute MySql and create inmar db.  

2. To build the application, first install all dependencis by executing 
    
    Yarn install
    
Maven is used to build the project for debug

    mvnw
 
 for productin
    
    mvnw -Pprod

This will build the applicaiton and hosts the applicaiton in local webserver in port 8080. User http://localhost:8080 to browse and verify the applicaiton.


### Updating the applicaiton 

As JHipster is used as development environment. JHipster commnads can be used to create the enitities and services to the applicaiton. 
For example to create the entity use:

    jhipster entity <entityname>

There will be series of questions to answer,where jhipster will create the enity and UI(based on the selected options)
    
The applicaiton is using Angular 6 v6.0.0, to build the UI components. Once the UI got changed or updated execute following command to build the UI.

    yarn start

## Deployment
This section provides the iformation to deploy the applicaiton in Pivotal Webservices. 

1. Register a account in https://pivotal.io/platform
2. Download and install CF client from Pivotal

#### Login to pivotal

3. Open command prompt and browse to applicaiton root directory and login to cloudfoundry using

    cf login -u username -p password
    
4. create a MySql db service

    cf create-service cleardb spark inmar

 5. For first time deployment execute
     jhipster cloudfoundry
    
    There will be series of config questions to answer to set the environment
    (a) select inmar as app name
    (b) set service type as cleardb
    (c) set plan as spark
    (d) service name as inmar
    
    Jhipster will builds,uploads package to the pivotal and starts the service
 
 6. From next time onwards execute the following commands to host the applicaitn
 
    ./mvnw -Pprod package
    
    cf push -f ./deploy/cloudfoundry/manifest.yml -p target\*.war

    cf bind-service inmar inmar
    
    cf restage inmar
    
    cf services
    
 7. Browse to service in broser
 
 
 ## Debugging & Health Check of deployed service
 
Cloudfoundry client provides option to monitor and debug the application
1. Retrieve logs
 
    cf logs --recent
    
2. Retrieve health and resource usage of the app 

    cf app app_name

3. App events

    cf events app_name

