FROM openjdk:17-alpine
ADD target/*.jar app.jar

ENV DB_HOST=localhost
ENV EUREKA_HOST=localhost
ENV KAFKA_HOST=localhost
ENV CURRENCY_HOST=localhost
ENV CURRENCY_PORT=8181
ENV AUTH_HOST=localhost

EXPOSE 8182

ENTRYPOINT [ "sh", "-c", "java \
    -Dspring.profiles.active=cloud \
    -jar /app.jar \
    --cloud.auth-host=$AUTH_HOST \
    --cloud.db-host=$DB_HOST --cloud.kafka-host=$KAFKA_HOST \
    --cloud.eureka-host=$EUREKA_HOST \
    --cloud.currency-host=$CURRENCY_HOST --cloud.currency-port=$CURRENCY_PORT \
    "]
