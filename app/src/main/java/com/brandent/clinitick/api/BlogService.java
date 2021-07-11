package com.brandent.clinitick.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BlogService {
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://blog.clinitick.com/wp-json/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
