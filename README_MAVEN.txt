How to use MAVEN for build projext:

## Setting maven 3.1 ##
"C:\Program Files\Java\setMVN.cmd" 3

## Setting java 8 ##
"C:\Program Files\Java\setJDK.cmd" 8

## Import the project structure in Eclipse ##
mvn eclipse:eclipse

## To clean, compile and build the project ##
mvn clean install 

## mvn skip test ##
mvn install -DskipTests

## To run spring-boot container  ##
java -jar target/simple-spring-boot-rules-engine-1.0.0.jar

## To easy execute the project  ##
double click on run.cmd

