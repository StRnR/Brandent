package com.brandent.clinitick.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainService {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://app.clinitick.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
