package com.example.project_pas;

import retrofit2.Retrofit;

public class ApiClient {
    public static Retrofit retrofit;
    public static final String BASE_URL = "https://mediadwi.com/api/latihan/";
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

}
