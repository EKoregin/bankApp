FROM openjdk:17-alpine
ADD target/*.jar app.jar

ENV DB_HOST=localhost
ENV EUREKA_HOST=localhost
ENV KAFKA_HOST=localhost
ENV AUTH_HOST=localhost

EXPOSE 8481

ENTRYPOINT [ "sh", "-c", "java \
    -Dspring.profiles.active=cloud \
    -jar /app.jar \
    --cloud.auth-host=$AUTH_HOST \
    --cloud.db-host=$DB_HOST --cloud.kafka-host=$KAFKA_HOST \
    --cloud.eureka-host=$EUREKA_HOST \
    "]
