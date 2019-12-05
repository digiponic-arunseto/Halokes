package com.digiponic.halokes.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arunstop on 24-May-19.
 */

public class RetrofitClient {
    private static final String BASE_URL = "http://halokes.digiponic.co.id/index.php/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public WebApi getApi() {
        return retrofit.create(WebApi.class);
    }
}
