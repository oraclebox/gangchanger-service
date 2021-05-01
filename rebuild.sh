git pull;
./gradlew clean build -x test
docker-compose up --build -d
docker logs -f gangchanger-service
