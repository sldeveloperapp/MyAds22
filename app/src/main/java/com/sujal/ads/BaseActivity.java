package com.sujal.ads;

import android.os.Bundle;

import com.myads2023.ads.adutils.BaseSimpleClass;
import com.myads2023.ads.gmodels.ConstantAds;

public class BaseActivity extends BaseSimpleClass {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantAds.setAdsUrlID("https://script.google.com/macros/s/AKfycbwBzapKjdZRcYsGW1BqnDg4LCHVnK5VqaTEnJmYds344oqmsT9W74VQZIHm0bZjKf3a/");
        ConstantAds.setIHAdsID("https://script.google.com/macros/s/AKfycbzhyh-itHU7HEQBeOzhgGQ5mekqteCqyXZDz7OxH3l4mwrtrmBNbgIryn4XklxQPEw/");
        ConstantAds.setCtColor(getResources().getColor(R.color.purple_200));
        ConstantAds.setNativeButtonBg(R.drawable.sample_bg);
    }
}