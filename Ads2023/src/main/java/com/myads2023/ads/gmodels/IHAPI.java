package com.myads2023.ads.gmodels;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IHAPI {

    private static <T> T builder(Class<T> endpoint) {
        return new Retrofit.Builder()
                .baseUrl(ConstantAds.ihAdsID)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(endpoint);
    }

    public static ApiInterface apiInterface() {
        return builder(ApiInterface.class);
    }

}
