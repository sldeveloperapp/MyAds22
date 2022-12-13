package com.myads2023.ads.gmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AdsDetails {

    @SerializedName("adsData")
    @Expose
    private ArrayList<AdsData> adsData = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public ArrayList<AdsData> getAdsData() {
        return adsData;
    }

    public void setAdsData(ArrayList<AdsData> adsData) {
        this.adsData = adsData;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}