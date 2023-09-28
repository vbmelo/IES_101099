package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ForecastRequestScheduler {
    private final Random random = new Random();
    private final List<Integer> availableCityIds = new ArrayList<>();

    public ForecastRequestScheduler() {
        initializeCityIds();

        Timer timer = new Timer();
        int intervalInSeconds = 10;
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

    private void initializeCityIds() {
        availableCityIds.add(1010500);
        availableCityIds.add(1020500);
        availableCityIds.add(1030300);
        availableCityIds.add(1030800);
        availableCityIds.add(1040200);
        availableCityIds.add(1050200);
        availableCityIds.add(1060300);
        availableCityIds.add(107055);
        availableCityIds.add(1080800);
        availableCityIds.add(1081100);
        availableCityIds.add(1081505);
        availableCityIds.add(1090700);
        availableCityIds.add(1090821);
    }

    public static void main(String[] args) {
        new ForecastRequestScheduler();
    }
}
