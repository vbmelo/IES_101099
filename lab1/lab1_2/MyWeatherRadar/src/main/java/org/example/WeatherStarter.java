package org.example;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    //todo: should generalize for a city passed as argument
    private static final int CITY_ID_AVEIRO = 1010500;

    public static void  main(String[] args ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IpmaService service = retrofit.create(IpmaService.class);

        Call<IpmaCityForecast> callSync = service.getForecastForACity(CITY_ID_AVEIRO);

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                var listLength = forecast.getData().size();
                System.out.println("Dias de Previsão do tempo Disponíveis: " + listLength + forecast.getData());

                for (CityForecast forecastItem : forecast.getData()) {
                    System.out.printf("Data: %s%n", forecastItem.getForecastDate());
                    System.out.printf("Temperatura máxima: %s%n", forecastItem.getTMax());
                    System.out.printf("Temperatura mínima: %s%n", forecastItem.getTMin());
                    System.out.printf("Precipitação: %s%n", forecastItem.getPrecipitaProb());
                    System.out.println();
                }
            } else {
                System.out.println( "No results for this request!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}