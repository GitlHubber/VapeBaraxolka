package ragalik.baraxolka.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://imvaper.000webhostapp.com";
    private static Retrofit retrofit;
    private static IApi iApi;

    public static IApi getApi () {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            iApi = retrofit.create(IApi.class);
        }
        return iApi;
    }
}
