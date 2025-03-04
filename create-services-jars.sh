#!/bin/bash

mvn -f ./eureka-service/pom.xml clean package -Dmaven.test.skip=true
mvn -f ./currency-service/pom.xml clean package -Dmaven.test.skip=true
mvn -f ./gateway-service/pom.xml clean package -Dmaven.test.skip=true
mvn -f ./processing-service/pom.xml clean package -Dmaven.test.skip=true