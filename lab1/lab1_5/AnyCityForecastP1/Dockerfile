FROM amazoncorretto:18

COPY target/AnyCityForecastP1-1.0-SNAPSHOT.jar /app/AnyCityForecastP1.jar

COPY src/main/resources/cities.json /app/cities.json

WORKDIR /app

CMD ["java", "-jar", "AnyCityForecastP1.jar"]
