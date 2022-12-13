package com.sujal.ads;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.Callable;

public class MainActivity extends BaseActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
    }

    public void showBanner(View view) {
        showBannerAd(findViewById(R.id.lay_banner));
    }

    public void showInter(View view) {
        showInterstitialAd(this, new Callable<Void>() {
            public Void call() throws Exception {
                MainActivity.this.toast("AdClosed");
                return null;
            }
        });
    }

    public void showNative(View view) {
        showNativeAd(findViewById(R.id.lay_native));
    }

    public void showAppServiceDialog(View view) {
        AppService(BuildConfig.VERSION_NAME);
    }

    public void showSmallNative(View view) {
        showNativeAd2Extra(findViewById(R.id.lay_smallnative));
    }
}
