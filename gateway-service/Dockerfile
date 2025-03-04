FROM openjdk:17-alpine
ADD target/*.jar app.jar

ENV EUREKA_HOST=localhost
ENV PROCESSING_URL="http://localhost:8182"
ENV CURRENCY_URL="http://localhost:8181"

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar /app.jar \
    --cloud.eureka-host=$EUREKA_HOST \
    --cloud.processing-service-url=$PROCESSING_URL \
    --cloud.currency-service-url=$CURRENCY_URL \
    "]
