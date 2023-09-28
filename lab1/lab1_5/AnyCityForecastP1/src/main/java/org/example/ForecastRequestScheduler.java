package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ForecastRequestScheduler {
    private final Random random = new Random();
    private final List<Integer> availableCityIds = new ArrayList<>();

    public ForecastRequestScheduler() {
        loadCityIdsFromJson();

        Timer timer = new Timer();
        int intervalInSeconds = 5;
        timer.scheduleAtFixedRate(new RequestTask(), 0, intervalInSeconds * 1000);
    }

    class RequestTask extends TimerTask {
        @Override
        public void run() {
            if (!availableCityIds.isEmpty()) {
                int randomIndex = random.nextInt(availableCityIds.size());
                int randomCityId = availableCityIds.get(randomIndex);
                IpmaApiClientP2.makeForecastRequest(randomCityId);
            }
        }
    }

    private void loadCityIdsFromJson() {
        try {
            File file = new File("src/main/resources/cities.json");
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(new FileReader(file)).getAsJsonObject();
            JsonArray cityIdsArray = jsonObject.getAsJsonArray("cityIds");

            for (var cityIdElement : cityIdsArray) {
                int cityId = cityIdElement.getAsInt();
                availableCityIds.add(cityId);
            }
        } catch (Exception ex) {
            System.err.println("Erro ao carregar IDs de cidade do JSON: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ForecastRequestScheduler();
    }
}
