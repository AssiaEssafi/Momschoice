package my.app.momschoice.customerFoodPanel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static APIService apiService;

    public static APIService getClient(String baseUrl) {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(APIService.class);
        }
        return apiService;
    }
}
