package com.myads2023.ads.gmodels;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("exec")
    Call<AdsDetails> getAds();

    @GET("exec")
    Call<IHAdsData> getIHAds();


}
