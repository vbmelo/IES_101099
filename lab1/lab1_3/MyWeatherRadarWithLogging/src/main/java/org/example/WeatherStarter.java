package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherStarter {

    private static final Logger logger = LogManager.getLogger(WeatherStarter.class);
    private static final int CITY_ID_AVEIRO = 1010500;

    public static void main(String[] args) {
        try {
            // Configuração do Log4j2
            System.setProperty("log4j.configurationFile", "log4j2.xml");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.ipma.pt/open-data/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IpmaService service = retrofit.create(IpmaService.class);
            Call<IpmaCityForecast> callSync = service.getForecastForACity(CITY_ID_AVEIRO);

            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                var listLength = forecast.getData().size();
                logger.info("Dias de Previsão do tempo Disponíveis: {}{}", listLength, forecast.getData());

                for (CityForecast forecastItem : forecast.getData()) {
                    logger.info("Data: {}", forecastItem.getForecastDate());
                    logger.info("Temperatura máxima: {}", forecastItem.getTMax());
                    logger.info("Temperatura mínima: {}", forecastItem.getTMin());
                    logger.info("Precipitação: {}", forecastItem.getPrecipitaProb());
                }
            } else {
                logger.warn("No results for this request!");
            }
        } catch (Exception ex) {
            logger.error("An error occurred", ex);
        }
    }
}
