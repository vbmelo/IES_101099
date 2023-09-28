package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IpmaApiClientP2 {

    private static final Logger logger = LogManager.getLogger(IpmaApiClientP2.class);
    private static final String BASE_URL = "http://api.ipma.pt/open-data/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final IpmaService service = retrofit.create(IpmaService.class);

    public static void makeForecastRequest(int cityId) {
        try {
            Call<IpmaCityForecast> callSync = service.getForecastForACity(cityId);
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                logger.info("Previsão para a cidade {}", cityId);
                for (CityForecast forecastItem : forecast.getData()) {
                    logger.info("Data: {}", forecastItem.getForecastDate());
                    logger.info("Temperatura máxima: {}", forecastItem.getTMax());
                    logger.info("Temperatura mínima: {}", forecastItem.getTMin());
                    logger.info("Precipitação: {}", forecastItem.getPrecipitaProb());
                }
            } else {
                logger.warn("Nenhum resultado para esta solicitação!");
            }
        } catch (Exception ex) {
            logger.error("Ocorreu um erro ao fazer a solicitação", ex);
        }
    }
}
