FROM openjdk:17-alpine
ADD target/*.jar app.jar

ENV EUREKA_HOST=localhost
ENV AUTH_HOST=localhost
ENV CURRENCY_HOST=localhost
ENV CURRENCY_PORT=8181

EXPOSE 8090

ENTRYPOINT [ "sh", "-c", "java \
    -Dspring.profiles.active=cloud \
    -jar /app.jar \
    --cloud.auth-host=$AUTH_HOST \
    --cloud.eureka-host=$EUREKA_HOST \
    --cloud.currency-host=$CURRENCY_HOST --cloud.currency-port=$CURRENCY_PORT \
    "]