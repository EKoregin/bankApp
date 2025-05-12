FROM openjdk:17-alpine
ADD target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","/app.jar"]
