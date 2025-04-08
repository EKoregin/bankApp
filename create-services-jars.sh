#!/bin/bash
# Сборка и создание jar для сервисов
# Проверка, был ли передан аргумент
if [ $# -eq 0 ]; then
    # Если аргумент не передан, создаем jar для всех сервисов
    services=("eureka-service" "currency-service" "gateway-service" "processing-service" "auth-service" "client-app")
else
    # Если аргумент передан, используем его как имя сервиса
    services=($1)
    echo $1
fi

# Цикл по сервисам и сборка jar
for service in "${services[@]}"; do
    echo "Сборка для сервиса: $service"
    mvn -f ./"$service"/pom.xml clean package -Dmaven.test.skip=true
done
