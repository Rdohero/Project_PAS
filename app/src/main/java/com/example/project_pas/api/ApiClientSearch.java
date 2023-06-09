package com.example.project_pas.api;

import retrofit2.Retrofit;

public class ApiClientSearch {
    public static Retrofit retrofit;
    public static final String BASE_URL = "www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata";
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

}