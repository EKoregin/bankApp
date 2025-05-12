FROM openjdk:17-alpine
ADD target/*.jar app.jar

ENV EUREKA_HOST=localhost
ENV PROCESSING_URL="http://localhost:8182"
ENV CURRENCY_URL="http://localhost:8181"
ENV BANK_APP_URL="http://localhost:8090"
ENV AUTH_SERVICE_URL="http://localhost:9000"
ENV HISTORY_SERVICE_URL="http://localhost:8481"

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar /app.jar \
    --cloud.eureka-host=$EUREKA_HOST \
    --cloud.processing-service-url=$PROCESSING_URL \
    --cloud.currency-service-url=$CURRENCY_URL \
    --cloud.bank-app-url=$BANK_APP_URL \
    --cloud.auth-service-url=$AUTH_SERVICE_URL \
    --cloud.history-service-url=$HISTORY_SERVICE_URL \
    "]
