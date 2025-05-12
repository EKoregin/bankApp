FROM openjdk:17-alpine
ADD target/*.jar app.jar

ENV EUREKA_HOST=localhost
ENV BANK_APP_URL=http://localhost:8090
ENV AUTH_SERVICE_URL=http://localhost:8080

EXPOSE 9000

ENTRYPOINT [ "sh", "-c", "java \
    -Dspring.profiles.active=cloud \
    -jar /app.jar \
    --cloud.eureka-host=$EUREKA_HOST \
    --cloud.bank-app-url=$BANK_APP_URL \
    --cloud.auth-service-url=$AUTH_SERVICE_URL \
    "]