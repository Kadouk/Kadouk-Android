package com.kadouk.app.webService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//4
// inja base URL emuno gozashtim, yani hame darkhastamun ta inja URL eshun yekie,
// ke tuye APIInterface har darkhasti mikonim URL esh be in ezafe mishe
// bad az inke ino kamel check kardi APIInterface ro baz kon
public class APIClient {
    public static final String BASE_URL = "http://kadouk.com/kadouk/public/api/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
