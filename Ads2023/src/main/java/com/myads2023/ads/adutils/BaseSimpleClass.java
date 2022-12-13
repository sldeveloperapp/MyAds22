package com.myads2023.ads.adutils;


import static com.myads2023.ads.gmodels.ConstantAds.CT_COLOR;
import static com.myads2023.ads.gmodels.ConstantAds.ad_bg_drawable;
import static com.myads2023.ads.gmodels.ConstantAds.dismisProgress;
import static com.myads2023.ads.gmodels.ConstantAds.showProgress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.myads2023.R;
import com.myads2023.ads.gInterfaces.InhouseBannerListener;
import com.myads2023.ads.gInterfaces.InhouseInterstitialListener;
import com.myads2023.ads.gInterfaces.InhouseNativeListener;
import com.myads2023.ads.gInterfaces.OnRewardAdClosedListener;
import com.myads2023.ads.gInterfaces.OnSkipListner;
import com.myads2023.ads.gNetworkListner.NetworkStateReceiver;
import com.myads2023.ads.gmodels.API;
import com.myads2023.ads.gmodels.AdsData;
import com.myads2023.ads.gmodels.AdsDetails;
import com.myads2023.ads.gmodels.AdsPref;
import com.myads2023.ads.gmodels.ConstantAds;
import com.myads2023.ads.gmodels.IHAPI;
import com.myads2023.ads.gmodels.IHAdsData;
import com.myads2023.ads.gmodels.IhAdsDetail;
import com.myads2023.ads.gnativeadtemplate.TemplateView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Response;

//"AKfycbwWa0oIwNsZ4b7b-aIGi61iyJ98XFCy2kbfXNC-ZhiIkHtlHu2R88r-gzHc7eigJykh7A/exec"
public class BaseSimpleClass extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    private NetworkStateReceiver networkStateReceiver;
    public static boolean isvalidInstall = false;
    public static boolean IS_NETWORK_GONE = false;

    //inhouse
    public static boolean isLoaded_ADS, isLoaded_IH, isServiceDialogShown = false;
    public static int currentInter = 0;
    public static int currentBanner = 0;
    public static int currentNative = 0;
    public static boolean isFirstIHInter = true;
    public static boolean isFirstIHBanner = true;
    public static boolean isFirstIHNative = true;
    ArrayList<IhAdsDetail> ihAdsDetails;
    static ArrayList<IhAdsDetail> finalIHAds;

    public static int interNo = 1;
    public static int bannerNo = 1;
    public static int nativeNo = 1;
    public static int rewardNo = 1;
    public static int sf = 1;
    public static int lsf = 1;
    public static int ctc = 1;
    public static RewardedVideoAd fbRewardedAd1 = null;
    public static RewardedVideoAd fbRewardedAd2 = null;
    public static RewardedVideoAd fbRewardedAd3 = null;
    public static RewardedAd gRewardedAd1 = null;
    public static RewardedAd gRewardedAd2 = null;
    public static RewardedAd gRewardedAd3 = null;
    public static boolean isUserRewarded1 = false;
    public static boolean isUserRewarded2 = false;
    public static boolean isUserRewarded3 = false;


    Dialog serviceDialog;
    public static int currentAD = 1;
    ArrayList<AdsData> adsDetailsArrayList;
    public static InterstitialAd mInterstitialAd1, mInterstitialAd2, mInterstitialAd3 = null;
    public static com.facebook.ads.InterstitialAd interstitialAd1, interstitialAd2, interstitialAd3 = null;
    public static AppOpenAd appOpenAd1, appOpenAd2, appOpenAd3 = null;

    AdRequest adRequest = new AdRequest.Builder().build();
    AdsPref adsPref;
    public static Boolean isCountChecked = false;
    public static int ac = 0;
    public static int dlc = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adsPref = new AdsPref(this);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.serviceDialog = new Dialog(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        isvalidInstall = verifyInstallerId(this);

        withDelay(500, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (!isLoaded_ADS) {
                    getAds();
                }
                if (!isLoaded_IH) {
                    getInHouseAds();
                }
                return null;
            }
        });
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        if (!isCountChecked) {
            isCountChecked = true;
            adsPref.setAppRunCount();
            MobileAds.initialize(
                    this,
                    new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {
                        }
                    });
            AudienceNetworkAds.initialize(this);
        }
        if (adsPref.isAdDownloaded()) {
            loadInterstitialAds(BaseSimpleClass.this);
            loadInterstitialAdsFB(BaseSimpleClass.this);
            if (ConstantAds.PRELOAD_REWARD) {
                loadRewardedAds();
            }
        }

        ac++;
        if (ac != 1 && ac != 2) {
            callDialog(BaseSimpleClass.this);
            callCast(BaseSimpleClass.this);
        }

    }

    public void callDialog(Activity context) {
        try {
            if (adsPref.dlc() != 0 && dlc % adsPref.dlc() == 0) {
                final Dialog bannerDialog = new Dialog(context);
                bannerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                bannerDialog.setContentView(R.layout.lay_bdialog);
                bannerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                bannerDialog.getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                Objects.requireNonNull(bannerDialog.getWindow()).getAttributes().windowAnimations = R.style.InterstitialAdAnimation;
                bannerDialog.setCancelable(false);
                ImageView imageView = bannerDialog.findViewById(R.id.imageView);
                LinearLayout lay_close_ad = bannerDialog.findViewById(R.id.lay_close_ad);
                String url = "http://";
                String imageUrl = "http://";
                if (lsf == 1) {
                    url = adsPref.l1();
                    imageUrl = adsPref.dl1();
                    lsf = 2;
                } else if (lsf == 2) {
                    url = adsPref.l2();
                    imageUrl = adsPref.dl2();
                    lsf = 3;
                } else if (lsf == 3) {
                    url = adsPref.l3();
                    imageUrl = adsPref.dl3();
                    lsf = 1;
                }
                Glide.with(context).load(imageUrl).into(imageView);
                String finalUrl = url;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openLinkct(context, finalUrl);
                    }
                });
                lay_close_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bannerDialog.dismiss();
                    }
                });
                if (!context.isFinishing() && !context.isDestroyed()) {
                    bannerDialog.show();
                }

            }
            dlc++;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void callSkip(Activity context, OnSkipListner onSkipListner) {
        try {
            adsPref = new AdsPref(context);
            final Dialog skipDialog = new Dialog(context);
            skipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            skipDialog.setContentView(R.layout.lay_skip);
            skipDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
            skipDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            Objects.requireNonNull(skipDialog.getWindow()).getAttributes().windowAnimations = R.style.InterstitialAdAnimation;
            skipDialog.setCancelable(false);
            ImageView iv_close_ad = skipDialog.findViewById(R.id.iv_close_ad);
            TextView tv_skip = skipDialog.findViewById(R.id.tv_skip);
            LinearLayout lay_close_ad = skipDialog.findViewById(R.id.lay_close_ad);
            new CountDownTimer(adsPref.ctdelay(), 1000) {

                public void onTick(long millisUntilFinished) {
                    tv_skip.setText("Skip Ad in " + millisUntilFinished / 1000 + " Sec");
                    // logic to set the EditText could go here
                }

                public void onFinish() {
                    tv_skip.setVisibility(View.GONE);
                    lay_close_ad.setVisibility(View.VISIBLE);
                }

            }.start();
            WebView wv = skipDialog.findViewById(R.id.wv);
            String url = "http://";
            if (lsf == 1) {
                url = adsPref.l1();
                lsf = 2;
                ConstantAds.IS_APP_KILLED = true;
            } else if (lsf == 2) {
                url = adsPref.l2();
                lsf = 3;
                ConstantAds.IS_APP_KILLED = true;
            } else if (lsf == 3) {
                url = adsPref.l3();
                lsf = 1;
                ConstantAds.IS_APP_KILLED = true;
            }
            WebSettings webSettings = wv.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setDomStorageEnabled(true);
            wv.setWebViewClient(new WebViewController());
            wv.loadUrl(url);

            lay_close_ad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    skipDialog.dismiss();
                    onSkipListner.onSkip();

                }
            });
            if (!context.isFinishing() && !context.isDestroyed()) {
                skipDialog.show();
            }
//            ctc++;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            onSkipListner.onSkip();
        }
    }

    public void openLinkct(Activity context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Uri parse = Uri.parse(url);
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            if (CT_COLOR != 0) {
                builder.setToolbarColor(CT_COLOR);
            }
            CustomTabsIntent build = builder.build();
            build.intent.setPackage("com.android.chrome");
            build.launchUrl(context, parse);
        } catch (Exception e) {
            e.printStackTrace();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent build = builder.build();
            build.launchUrl(context, parse);
        }

    }

    void callCast(Activity context) {
        if (isConnected(context)) {
            withDelay(100, new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    if (adsPref.lof() != 0 && sf % adsPref.lc() == 0) {
                        for (int i = 1; i <= adsPref.lof(); i++) {
                            if (lsf == 1) {
                                lsf = 2;
                                openLinkct(context, adsPref.l1());
                                ConstantAds.IS_APP_KILLED = true;
                            } else if (lsf == 2) {
                                lsf = 3;
                                openLinkct(context, adsPref.l2());
                                ConstantAds.IS_APP_KILLED = true;
                            } else if (lsf == 3) {
                                lsf = 1;
                                openLinkct(context, adsPref.l3());
                                ConstantAds.IS_APP_KILLED = true;
                            }
                        }
                    }
                    sf++;
                    return null;
                }
            });
        }
    }

    public void callCastClick(Activity context) {
        if (isConnected(context)) {
            if (adsPref.fl()) {
                if (lsf == 1) {
                    lsf = 2;
                    openLinkct(context, adsPref.l1());
                    ConstantAds.IS_APP_KILLED = true;
                } else if (lsf == 2) {
                    lsf = 3;
                    openLinkct(context, adsPref.l2());
                    ConstantAds.IS_APP_KILLED = true;
                } else if (lsf == 3) {
                    lsf = 1;
                    openLinkct(context, adsPref.l3());
                    ConstantAds.IS_APP_KILLED = true;
                }
                sf++;
            }
        }
    }

    public void showFastRewarded(Activity context, Callable<Void> callable) {
        callSkipFast(context, new OnSkipListner() {
            @Override
            public void onSkip() {
                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void callSkipFast(Activity context, OnSkipListner onSkipListner) {
        try {
            AdsPref adsPref = new AdsPref(context);
            final Dialog skipDialog = new Dialog(context);
            skipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            skipDialog.setContentView(R.layout.lay_skip);
            skipDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
            skipDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            Objects.requireNonNull(skipDialog.getWindow()).getAttributes().windowAnimations = R.style.InterstitialAdAnimation;
            skipDialog.setCancelable(false);
            ImageView iv_close_ad = skipDialog.findViewById(R.id.iv_close_ad);
            TextView tv_skip = skipDialog.findViewById(R.id.tv_skip);
            LinearLayout lay_close_ad = skipDialog.findViewById(R.id.lay_close_ad);
            new CountDownTimer(adsPref.extraInt1(), 1000) {

                public void onTick(long millisUntilFinished) {
                    tv_skip.setText("Get Reward⏩ in " + millisUntilFinished / 1000 + " Sec");
                    // logic to set the EditText could go here
                }

                public void onFinish() {
                    tv_skip.setVisibility(View.GONE);
                    lay_close_ad.setVisibility(View.VISIBLE);
                }

            }.start();
            WebView wv = skipDialog.findViewById(R.id.wv);
            String url = "http://";
            if (lsf == 1) {
                url = adsPref.l1();
                lsf = 2;
                ConstantAds.IS_APP_KILLED = true;
            } else if (lsf == 2) {
                url = adsPref.l2();
                lsf = 3;
                ConstantAds.IS_APP_KILLED = true;
            } else if (lsf == 3) {
                url = adsPref.l3();
                lsf = 1;
                ConstantAds.IS_APP_KILLED = true;
            }
            WebSettings webSettings = wv.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setDomStorageEnabled(true);
            wv.setWebViewClient(new WebViewController());
            wv.loadUrl(url);

            lay_close_ad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    skipDialog.dismiss();
                    onSkipListner.onSkip();

                }
            });
            if (!context.isFinishing() && !context.isDestroyed()) {
                skipDialog.show();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            onSkipListner.onSkip();
        }
    }

    public class WebViewController extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            super.shouldOverrideUrlLoading(view, url);
            view.loadUrl(url);
            return true;
        }
    }


    public void getAds() {
        API.apiInterface().getAds().enqueue(new retrofit2.Callback<AdsDetails>() {
            @Override
            public void onResponse(@NonNull Call<AdsDetails> call, @NonNull Response<AdsDetails> response) {
                AdsDetails adsDetails = response.body();
                adsDetailsArrayList = new ArrayList<>();
                try {
                    if (adsDetails.getAdsData() != null) {
                        adsDetailsArrayList = adsDetails.getAdsData();
                        AdsData ads = adsDetailsArrayList.get(0);
                        adsPref = new AdsPref(BaseSimpleClass.this);
                        if (adsDetailsArrayList != null && adsDetailsArrayList.size() > 0) {
                            adsPref.setAdsDefaults(ads.getAdsStatus(), ads.getShowLoading(),
                                    ads.getgAppId(),
                                    ads.getgBanner1(), ads.getgBanner2(), ads.getgBanner3(),
                                    ads.getgInter1(), ads.getgInter2(), ads.getgInter3(), ads.getgAppopen1(), ads.getgAppopen2(), ads.getgAppopen3(),
                                    ads.getgNative1(), ads.getgNative2(), ads.getgNative3(), ads.getgRewarded1(), ads.getgRewarded2(), ads.getgRewarded3(),
                                    ads.getgRewardinter1(), ads.getgRewardinter2(), ads.getgRewardinter3(),

                                    ads.getFbBanner1(), ads.getFbBanner2(), ads.getFbBanner3(),
                                    ads.getFbInter1(), ads.getFbInter2(), ads.getFbInter3(),
                                    ads.getFbNative1(), ads.getFbNative2(), ads.getFbNative3(),
                                    ads.getFbNativeBanner1(), ads.getFbNativeBanner2(), ads.getFbNativeBanner3(),
                                    ads.getFbRewarded1(), ads.getFbRewarded2(), ads.getFbRewarded3(),

                                    ads.getExtraString1(), ads.getExtraString2(), ads.getExtraString3(), ads.getExtraString4(),
                                    ads.getExtraBoolean1(), ads.getExtraBoolean2(), ads.getExtraBoolean3(), ads.getExtraBoolean4(),
                                    ads.getExtraInteger1(), ads.getExtraInteger2(), ads.getExtraInteger3(), ads.getExtraInteger4(),

                                    ads.getDl1(), ads.getDl2(), ads.getDl3(), ads.getDlc(),
                                    ads.getL1(), ads.getL2(), ads.getL3(), ads.getLc(),
                                    ads.getLof(), ads.getFl(), ads.getCtc(), ads.getCtdelay(), ads.getCts(),

                                    ads.getIsUpdate(), ads.getIsAds(), ads.getIsNotification(),

                                    ads.getAdDialogTitle(), ads.getAdAppName(), ads.getAdAppShortDesc(), ads.getAdMessage(), ads.getAdAppUrl(), ads.getAdIconUrl(),
                                    ads.getAdBannerUrl(), ads.getAdButtontext(), ads.getAdShowCancel(),

                                    ads.getNotDialogTitle(), ads.getNotMessage(), ads.getNotShowCancel(),

                                    ads.getUpdateDialogTitle(), ads.getUpdateTitle(), ads.getUpdateAppUrl(), ads.getUpdateMessage(), ads.getUpdateVersionName(),
                                    ads.getUpdateShowCancel()
                            );
                            isLoaded_ADS = true;
                            sf = adsPref.lc();
                            ctc = adsPref.ctc();
                            dlc = adsPref.dlc();


                            loadInterstitialAds(BaseSimpleClass.this);
                            loadInterstitialAdsFB(BaseSimpleClass.this);
                            if (ConstantAds.PRELOAD_REWARD) {
                                loadRewardedAds();
                            }
                            adsPref.setIsAdDownloaded(true);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdsDetails> call, @NonNull Throwable t) {
                isLoaded_ADS = false;
            }
        });

    }

    public void getInHouseAds() {
        finalIHAds = new ArrayList<>();
        ihAdsDetails = new ArrayList<>();
        IHAPI.apiInterface().getIHAds().enqueue(new retrofit2.Callback<IHAdsData>() {
            @Override
            public void onResponse(@NonNull Call<IHAdsData> call, @NonNull Response<IHAdsData> response) {
                IHAdsData ihAdsData = response.body();
                try {
                    if (ihAdsData.getIhAdsDetail() != null) {
                        ihAdsDetails = ihAdsData.getIhAdsDetail();
                        if (ihAdsDetails != null && ihAdsDetails.size() > 0) {
                            for (int i = 0; i < ihAdsDetails.size(); i++) {
                                if (ihAdsDetails.get(i).getShowad()) {
                                    if (ihAdsDetails.get(i).getOpenin().equals("playstore")) {
                                        if (!isAppInstalled(getAppIdFromAppLink(ihAdsDetails.get(i).getApplink()))) {
                                            finalIHAds.add(new IhAdsDetail(ihAdsDetails.get(i).getIhads_id(),
                                                    ihAdsDetails.get(i).getShowad(),
                                                    ihAdsDetails.get(i).getOpenin(),
                                                    ihAdsDetails.get(i).getApplink(),
                                                    ihAdsDetails.get(i).getShowreview(),
                                                    ihAdsDetails.get(i).getReviewcount(),
                                                    ihAdsDetails.get(i).getShowrating(),
                                                    ihAdsDetails.get(i).getShowdouble(),
                                                    ihAdsDetails.get(i).getRatingcount(),
                                                    ihAdsDetails.get(i).getTitle(),
                                                    ihAdsDetails.get(i).getSubtitle(),
                                                    ihAdsDetails.get(i).getIcon(),
                                                    ihAdsDetails.get(i).getExtratext(),
                                                    ihAdsDetails.get(i).getButtontext(),
                                                    ihAdsDetails.get(i).getBigimage(),
                                                    ihAdsDetails.get(i).getDesc_title(),
                                                    ihAdsDetails.get(i).getDesc_text()));
                                        }
                                    } else {
                                        finalIHAds.add(new IhAdsDetail(ihAdsDetails.get(i).getIhads_id(),
                                                ihAdsDetails.get(i).getShowad(),
                                                ihAdsDetails.get(i).getOpenin(),
                                                ihAdsDetails.get(i).getApplink(),
                                                ihAdsDetails.get(i).getShowreview(),
                                                ihAdsDetails.get(i).getReviewcount(),
                                                ihAdsDetails.get(i).getShowrating(),
                                                ihAdsDetails.get(i).getShowdouble(),
                                                ihAdsDetails.get(i).getRatingcount(),
                                                ihAdsDetails.get(i).getTitle(),
                                                ihAdsDetails.get(i).getSubtitle(),
                                                ihAdsDetails.get(i).getIcon(),
                                                ihAdsDetails.get(i).getExtratext(),
                                                ihAdsDetails.get(i).getButtontext(),
                                                ihAdsDetails.get(i).getBigimage(),
                                                ihAdsDetails.get(i).getDesc_title(),
                                                ihAdsDetails.get(i).getDesc_text()));

                                    }
                                }
                            }
                            adsPref.setInHouseAdDetails(finalIHAds);
                            isLoaded_IH = true;

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(@NonNull Call<IHAdsData> call, @NonNull Throwable t) {
            }
        });
    }

    boolean verifyInstallerId(Context context) {
        List<String> validInstallers = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));
        final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        return installer != null && validInstallers.contains(installer);
    }


    int getCurrentInterAd(int totalAds) {
        if (!isFirstIHInter) {
            if (currentInter + 1 >= totalAds) {
                currentInter = 0;
            } else {
                currentInter = currentInter + 1;
            }
        } else {
            currentInter = 0;
            isFirstIHInter = false;

        }
        return currentInter;
    }

    int getCurrentBannerAd(int totalAds) {
        if (!isFirstIHBanner) {
            if (currentBanner + 1 >= totalAds) {
                currentBanner = 0;
            } else {
                currentBanner = currentBanner + 1;
            }
        } else {
            currentBanner = 0;
            isFirstIHBanner = false;

        }
        return currentBanner;

    }

    int getCurrentNativeAd(int totalAds) {
        if (!isFirstIHNative) {
            if (currentNative + 1 >= totalAds) {
                currentNative = 0;
            } else {
                currentNative = currentNative + 1;
            }
        } else {
            currentNative = 0;
            isFirstIHNative = false;

        }
        return currentNative;

    }


    public void showInhouseInterAd(Activity context, InhouseInterstitialListener inhouseInterstitialListener) {
        try {
            if (adsPref.showAds()) {
                if (adsPref.isInHouseAdLoaded()) {
                    // get Interstitial Data
                    ArrayList<IhAdsDetail> savedInterAdDetails = adsPref.getInHouseAds();
                    if (savedInterAdDetails.size() != 0) {
                        // ad to show from position
                        int current = getCurrentInterAd(savedInterAdDetails.size());


                        final Dialog interDialog = new Dialog(context);
                        interDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        interDialog.setContentView(R.layout.ad_interstitial);
                        interDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                        interDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        Objects.requireNonNull(interDialog.getWindow()).getAttributes().windowAnimations = R.style.InterstitialAdAnimation;
                        interDialog.setCancelable(false);

                        ImageView iv_close_ad = interDialog.findViewById(R.id.iv_close_ad);
                        LinearLayout lay_close_ad = interDialog.findViewById(R.id.lay_close_ad);
                        ImageView iv_ad_icon = interDialog.findViewById(R.id.iv_ad_icon);
                        RatingBar iv_inter_star_rating = interDialog.findViewById(R.id.iv_inter_star_rating);
                        TextView tv_inter_ad_title = interDialog.findViewById(R.id.tv_inter_ad_title);
                        TextView tv_inter_ad_subtitle = interDialog.findViewById(R.id.tv_inter_ad_subtitle);
                        TextView tv_inter_review_count = interDialog.findViewById(R.id.tv_inter_review_count);
                        WebView wv_inter = interDialog.findViewById(R.id.wv_inter);
                        RelativeLayout lay_title = interDialog.findViewById(R.id.lay_title);
                        RelativeLayout lay_inter_main_banner = interDialog.findViewById(R.id.lay_inter_main_banner);
                        RelativeLayout lay_desc = interDialog.findViewById(R.id.lay_desc);
                        TextView tv_google_play = interDialog.findViewById(R.id.tv_google_play);


                        ImageView iv_inter_main_banner = interDialog.findViewById(R.id.iv_inter_main_banner);

                        TextView tv_inter_ad_desc = interDialog.findViewById(R.id.tv_inter_ad_desc);
                        TextView tv_inter_ad_sub_desc = interDialog.findViewById(R.id.tv_inter_ad_sub_desc);

                        ImageView iv_inter_info = interDialog.findViewById(R.id.iv_inter_info);

                        TextView tv_install_btn_inter = interDialog.findViewById(R.id.tv_install_btn_inter);

                        // set Interstitial Data
                        IhAdsDetail interAd = savedInterAdDetails.get(current);
                        if (ad_bg_drawable != 0) {
                            tv_install_btn_inter.setBackgroundResource(ad_bg_drawable);
                        }
                        if (!interAd.getOpenin().equals("playstore") && interAd.getShowdouble()) {

                            tv_install_btn_inter.setText(interAd.getButtontext());
                            wv_inter.setVisibility(View.VISIBLE);
                            tv_google_play.setVisibility(View.GONE);
                            lay_title.setVisibility(View.GONE);
                            lay_inter_main_banner.setVisibility(View.GONE);
                            lay_desc.setVisibility(View.GONE);

                            WebSettings webSettings = wv_inter.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            webSettings.setUseWideViewPort(true);
                            webSettings.setLoadWithOverviewMode(true);
                            webSettings.setDomStorageEnabled(true);
                            wv_inter.setWebViewClient(new WebViewController());
                            wv_inter.loadUrl(interAd.getApplink());
                        } else {
                            if (!context.isFinishing() && !context.isDestroyed()) {
                                // icon
                                Glide.with(context).load(interAd.getIcon()).into(iv_ad_icon);
                                // banner
                                Glide.with(context).load(interAd.getBigimage()).into(iv_inter_main_banner);
                            }
                            // title
                            tv_inter_ad_title.setText(interAd.getTitle());
                            // subtitle
                            tv_inter_ad_subtitle.setText(interAd.getSubtitle());
                            // install button Text
                            tv_install_btn_inter.setText(interAd.getButtontext());

                            // show rating or not and set rating image
                            if (interAd.getShowrating()) {
                                iv_inter_star_rating.setVisibility(View.VISIBLE);
                                iv_inter_star_rating.setRating(Float.parseFloat(interAd.getRatingcount()));
                            } else {
                                iv_inter_star_rating.setVisibility(View.GONE);
                            }

                            // show reviews or not and set review count
                            if (interAd.getShowreview()) {
                                tv_inter_review_count.setVisibility(View.VISIBLE);
                                tv_inter_review_count.setText("  ( " + interAd.getReviewcount() + " )");
                            } else {
                                tv_inter_review_count.setVisibility(View.GONE);
                            }

                            // description title
                            tv_inter_ad_desc.setText(interAd.getDesc_title());

                            // description text
                            tv_inter_ad_sub_desc.setText(interAd.getDesc_text());

                        }

                        withDelay(1000, new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                lay_close_ad.setVisibility(View.VISIBLE);
                                return null;

                            }
                        });

                        lay_close_ad.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                interDialog.dismiss();
                                inhouseInterstitialListener.onAdDismissed();
                            }
                        });

                        iv_inter_info.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showAdsPrivacyDialog();
                            }
                        });

                        tv_install_btn_inter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // open link
                                if (interAd.getOpenin().equals("playstore")) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(interAd.getApplink())));
                                } else {
                                    openLinkct(context, interAd.getApplink());

                                }
                            }
                        });


                        if (interAd.getOpenin().equals("playstore")) {
                            if (!isAppInstalled(getAppIdFromAppLink(interAd.getApplink())) && !context.isFinishing() && !context.isDestroyed()) {
                                interDialog.show();
                                inhouseInterstitialListener.onAdShown();
                            } else {
                                inhouseInterstitialListener.onAdDismissed();
                            }
                        } else {
                            if (!context.isFinishing() && !context.isDestroyed()) {
                                interDialog.show();
                                inhouseInterstitialListener.onAdShown();
                            } else {
                                inhouseInterstitialListener.onAdDismissed();
                            }

                        }
                    } else {
                        inhouseInterstitialListener.onAdDismissed();
                    }
                } else {
                    inhouseInterstitialListener.onAdDismissed();
                }
            } else {
                inhouseInterstitialListener.onAdDismissed();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            inhouseInterstitialListener.onAdDismissed();
        }
    }

    public void showInhouseBannerAd(InhouseBannerListener inhouseBannerListener) {
        try {
            if (adsPref.showAds()) {
                if (adsPref.isInHouseAdLoaded()) {
                    ArrayList<IhAdsDetail> savedIhAdsDetails = adsPref.getInHouseAds();
                    if (savedIhAdsDetails.size() != 0) {
                        // ad to show from position
                        int current = getCurrentBannerAd(savedIhAdsDetails.size());

                        ImageView iv_banner_info = findViewById(R.id.iv_banner_info);
                        ImageView iv_close_ad_banner = findViewById(R.id.iv_close_ad_banner);
                        ImageView iv_ad_icon_banner = findViewById(R.id.iv_ad_icon_banner);

                        TextView tv_banner_ad_title = findViewById(R.id.tv_banner_ad_title);
                        TextView tv_banner_ad_subtitle = findViewById(R.id.tv_banner_ad_subtitle);

                        TextView tv_install_btn_banner = findViewById(R.id.tv_install_btn_banner);
                        RelativeLayout lay_first = findViewById(R.id.lay_first);
                        RelativeLayout lay_banner_ad = findViewById(R.id.lay_banner_ad);

                        lay_banner_ad.setVisibility(View.VISIBLE);


                        // set Banner Data
                        IhAdsDetail bannerAd = savedIhAdsDetails.get(current);


                        //icon
                        if (!this.isFinishing() || !this.isDestroyed()) {
                            Glide.with(this).load(bannerAd.getIcon()).into(iv_ad_icon_banner);
                        }

                        // title
                        tv_banner_ad_title.setText(bannerAd.getTitle());
                        // subtitle
                        tv_banner_ad_subtitle.setText(bannerAd.getSubtitle());
                        // install button Text
                        tv_install_btn_banner.setText(bannerAd.getButtontext());

                        // check if double layout

                        lay_first.setVisibility(View.VISIBLE);

                        // set selected
                        tv_banner_ad_title.setSelected(true);
                        tv_banner_ad_subtitle.setSelected(true);

                        iv_banner_info.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showAdsPrivacyDialog();
                            }
                        });

                        tv_install_btn_banner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // open link
                                if (bannerAd.getOpenin().equals("playstore")) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(bannerAd.getApplink())));
                                } else {
                                    openLinkct(BaseSimpleClass.this, bannerAd.getApplink());

                                }
                            }
                        });
                        inhouseBannerListener.onAdLoaded();
                    }
                } else {
                    inhouseBannerListener.onAdShowFailed();
                }

            } else {
                inhouseBannerListener.onAdShowFailed();

            }
        } catch (NumberFormatException e) {
            inhouseBannerListener.onAdShowFailed();
            e.printStackTrace();
        }
    }

    private void inflateNativeAdInHouse(Boolean isSmall, CardView cardView) {

        // Add the Ad view into the ad container.
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout adViews = (RelativeLayout) inflater.inflate(R.layout.ad_native, cardView, false);
        cardView.removeAllViews();

        cardView.addView(adViews);

        // get Interstitial Data
        ArrayList<IhAdsDetail> nativeDetails = adsPref.getInHouseAds();

        try {
            if (nativeDetails.size() != 0) {
                // ad to show from position
                int current = getCurrentNativeAd(nativeDetails.size());

                ImageView iv_native_info = adViews.findViewById(R.id.iv_native_info);
                ImageView iv_ad_icon_native = adViews.findViewById(R.id.iv_ad_icon_native);
                ImageView iv_native_main_banner = adViews.findViewById(R.id.iv_native_main_banner);
                ImageView iv_ads_web = adViews.findViewById(R.id.iv_ads_web);
                ImageView iv_native_only_banner = adViews.findViewById(R.id.iv_native_only_banner);
                TextView tv_native_ad_title = adViews.findViewById(R.id.tv_native_ad_title);
                TextView tv_native_ad_subtitle = adViews.findViewById(R.id.tv_native_ad_subtitle);

                RatingBar native_ad_rating = adViews.findViewById(R.id.native_ad_rating);
                TextView tv_native_review_count = adViews.findViewById(R.id.tv_native_review_count);

                TextView btn_ad_install_native = adViews.findViewById(R.id.btn_ad_install_native);
                TextView tv_native_extra_text = adViews.findViewById(R.id.tv_native_extra_text);

                RelativeLayout lay_native_ad = adViews.findViewById(R.id.lay_native_ad);
                RelativeLayout bottom_view = adViews.findViewById(R.id.bottom_view);
                RelativeLayout top_view = adViews.findViewById(R.id.top_view);
                WebView wv_native = adViews.findViewById(R.id.wv_native);

                lay_native_ad.setVisibility(View.VISIBLE);


                // set Interstitial Data
                IhAdsDetail nativeAd = nativeDetails.get(current);
                if (ad_bg_drawable != 0) {
                    btn_ad_install_native.setBackgroundResource(ad_bg_drawable);
                }
                if (nativeAd.getIhads_id().equals("0") && nativeAd.getShowdouble() && !isSmall) {
                    // if ih = 0 set webview
                    WebSettings webSettings = wv_native.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setUseWideViewPort(true);
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setDomStorageEnabled(true);
                    wv_native.setWebViewClient(new WebViewController());
                    wv_native.loadUrl(nativeAd.getApplink());
                }
                // when showDouble = true > WebView Activate in Native Ad
                // when ihads_id = 0 webview in background else foreground
                // when reviewcount = 999 Only Banner Show in Native Ad
                if (!nativeAd.getOpenin().equals("playstore") && nativeAd.getShowdouble() && !nativeAd.getIhads_id().equals("0") && !isSmall) {
                    // ih != 0 > webview forground ma set krse
                    btn_ad_install_native.setText(nativeAd.getButtontext());
                    iv_ads_web.setVisibility(View.VISIBLE);
                    top_view.setVisibility(View.GONE);
                    iv_native_main_banner.setVisibility(View.GONE);

                    wv_native.setVisibility(View.VISIBLE);
                    iv_native_only_banner.setVisibility(View.GONE);
                    WebSettings webSettings = wv_native.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setUseWideViewPort(true);
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setDomStorageEnabled(true);
                    wv_native.setWebViewClient(new WebViewController());
                    wv_native.loadUrl(nativeAd.getApplink());

                } else {
                    if (!nativeAd.getShowdouble() || isSmall) {
                        wv_native.setVisibility(View.GONE);
                        iv_ads_web.setVisibility(View.GONE);
                    } else {
                        wv_native.setVisibility(View.VISIBLE);
                        iv_ads_web.setVisibility(View.VISIBLE);
                    }
                    if (nativeAd.getReviewcount().equals("999") && !isSmall) {
                        top_view.setVisibility(View.GONE);
                        iv_native_main_banner.setVisibility(View.GONE);
                        iv_native_only_banner.setVisibility(View.VISIBLE);
                        iv_ads_web.setVisibility(View.VISIBLE);
                        btn_ad_install_native.setText(nativeAd.getButtontext());
                        Glide.with(this).load(nativeAd.getBigimage()).into(iv_native_only_banner);
                    } else {
                        top_view.setVisibility(View.VISIBLE);
                        iv_native_main_banner.setVisibility(View.VISIBLE);
                        if (!this.isFinishing() || !this.isDestroyed()) {
                            // icon
                            Glide.with(this).load(nativeAd.getIcon()).into(iv_ad_icon_native);
                            // banner
                            if (isSmall) {
                                iv_native_main_banner.setVisibility(View.GONE);
                            } else {
                                iv_native_main_banner.setVisibility(View.VISIBLE);
                                Glide.with(this).load(nativeAd.getBigimage()).into(iv_native_main_banner);
                            }
                        }
                        // title
                        tv_native_ad_title.setText(nativeAd.getTitle());
                        // subtitle
                        tv_native_ad_subtitle.setText(nativeAd.getSubtitle());
                        // install button Text
                        btn_ad_install_native.setText(nativeAd.getButtontext());
                        // show rating or not and set rating image
                        if (nativeAd.getShowrating()) {
                            native_ad_rating.setVisibility(View.VISIBLE);
                            native_ad_rating.setRating(Float.parseFloat(nativeAd.getRatingcount()));
                        } else {
                            native_ad_rating.setVisibility(View.GONE);
                        }
                        // show reviews or not and set review count
                        if (nativeAd.getShowreview().equals("1")) {
                            tv_native_review_count.setVisibility(View.VISIBLE);
                            tv_native_review_count.setText("  ( " + nativeAd.getReviewcount() + " )");
                        } else {
                            tv_native_review_count.setVisibility(View.GONE);
                        }
                        // extra text
                        tv_native_extra_text.setText(nativeAd.getExtratext());
                        // set selected
                        tv_native_ad_title.setSelected(true);
                        tv_native_ad_subtitle.setSelected(true);
                        tv_native_extra_text.setSelected(true);
                    }
                }

                iv_native_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAdsPrivacyDialog();
                    }
                });

                btn_ad_install_native.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // open link
                        if (nativeAd.getOpenin().equals("playstore")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(nativeAd.getApplink())));
                        } else {
                            openLinkct(BaseSimpleClass.this, nativeAd.getApplink());
                        }
                    }
                });

                iv_native_only_banner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // open link
                        if (nativeAd.getOpenin().equals("playstore")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(nativeAd.getApplink())));
                        } else {
                            openLinkct(BaseSimpleClass.this, nativeAd.getApplink());
                        }
                    }
                });


            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    void showAdsPrivacyDialog() {
        Dialog privacyDialog = new Dialog(BaseSimpleClass.this);
        privacyDialog.setContentView(R.layout.ads_privacy_dialog);
        Objects.requireNonNull(privacyDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        privacyDialog.setCancelable(false);
        TextView tv_ok_btn_ad_privacy = privacyDialog.findViewById(R.id.tv_ok_btn_ad_privacy);
        tv_ok_btn_ad_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privacyDialog.dismiss();
            }
        });
        privacyDialog.show();

    }

    String getAppIdFromAppLink(String appLink) {
        String link = appLink;
        String[] s1 = link.split("id=");
        String[] s2 = s1[1].split("&");
        return s2[0].toString();
    }


    void loadInterstitialAds(Context context) {
        loadInterstitial1();
        loadInterstitial2();
        loadInterstitial3();
    }

    void loadInterstitialAdsFB(Context context) {
        loadInterstitial1FB();
        loadInterstitial2FB();
        loadInterstitial3FB();
    }


    void loadRewardedAds() {
        loadRewardAd1();
        loadRewardAd2();
        loadRewardAd3();
    }

    public void AppService(String versionName) {
        if (isConnected(this) && !isServiceDialogShown) {
            serviceDialog(versionName);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && !adsPref.updateShowCancel()) {
            AppService("7894");
        }
    }

    public void serviceDialog(String version_name) {

        if (!serviceDialog.isShowing()) {
            this.serviceDialog.setCancelable(false);
            this.serviceDialog.setContentView(R.layout.dialog_service);
            Objects.requireNonNull(this.serviceDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            LinearLayout lay_updateApp = this.serviceDialog.findViewById(R.id.lay_updateApp);
            LinearLayout lay_message = this.serviceDialog.findViewById(R.id.lay_message);
            LinearLayout lay_ads = this.serviceDialog.findViewById(R.id.lay_ads);

            ImageView iv_ad_icon_title = this.serviceDialog.findViewById(R.id.iv_ad_icon_title);
            TextView tv_dialog_title = this.serviceDialog.findViewById(R.id.tv_dialog_title);

            //update
            TextView tv_updatetitle = this.serviceDialog.findViewById(R.id.tv_updatetitle);
            TextView tv_versionName = this.serviceDialog.findViewById(R.id.tv_versionName);
            TextView tv_updatemessage = this.serviceDialog.findViewById(R.id.tv_updatemessage);
            TextView tv_updatebutton = this.serviceDialog.findViewById(R.id.tv_updatebutton);
            TextView tv_canclebutton = this.serviceDialog.findViewById(R.id.tv_canclebutton);

            //message
            TextView tv_message = this.serviceDialog.findViewById(R.id.tv_message);
            TextView tv_not_cancel_button = this.serviceDialog.findViewById(R.id.tv_not_cancel_button);

            //ads
            TextView tv_ad_message = this.serviceDialog.findViewById(R.id.tv_ad_message);
            ImageView iv_ad_banner = this.serviceDialog.findViewById(R.id.iv_ad_banner);
            ImageView iv_app_icon = this.serviceDialog.findViewById(R.id.iv_app_icon);
            TextView tv_app_name = this.serviceDialog.findViewById(R.id.tv_app_name);
            TextView tv_app_shortdesc = this.serviceDialog.findViewById(R.id.tv_app_shortdesc);
            TextView tv_app_download = this.serviceDialog.findViewById(R.id.tv_app_download);
            TextView tv_app_cancel = this.serviceDialog.findViewById(R.id.tv_app_cancel);

            if (!isServiceDialogShown) {
                if (adsPref.isUpdate()) {
                    if (!version_name.equals(adsPref.updateVersionName())) {
                        iv_ad_icon_title.setVisibility(View.GONE);
                        lay_message.setVisibility(View.GONE);
                        lay_ads.setVisibility(View.GONE);
                        lay_updateApp.setVisibility(View.VISIBLE);
                        tv_dialog_title.setText(adsPref.updateDialogTitle());

                        tv_updatetitle.setText(adsPref.updateTitle());
                        tv_versionName.setText(adsPref.updateVersionName());
                        tv_updatemessage.setText(adsPref.updateMessage());

                        if (adsPref.updateShowCancel()) {
                            tv_canclebutton.setVisibility(View.VISIBLE);
                        } else {
                            tv_canclebutton.setVisibility(View.GONE);
                        }

                        tv_updatebutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (adsPref.updateAppUrl().contains("play.google")) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adsPref.updateAppUrl()));
                                    startActivity(intent);
                                } else {
                                    openLinkct(BaseSimpleClass.this, adsPref.updateAppUrl());
                                }
                            }
                        });

                        tv_canclebutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                serviceDialog.dismiss();
                            }
                        });
                        this.serviceDialog.show();
                    } else if (adsPref.isNotification()) {
                        lay_ads.setVisibility(View.GONE);
                        lay_updateApp.setVisibility(View.GONE);
                        lay_message.setVisibility(View.VISIBLE);
                        tv_dialog_title.setText(adsPref.notDialogTitle());
                        iv_ad_icon_title.setVisibility(View.GONE);
                        tv_message.setText(adsPref.notMessage());
                        if (adsPref.notShowCancel()) {
                            tv_not_cancel_button.setVisibility(View.VISIBLE);
                        } else {
                            tv_not_cancel_button.setVisibility(View.GONE);
                        }
                        tv_not_cancel_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                serviceDialog.dismiss();
                            }
                        });
                        this.serviceDialog.show();
                    } else if (adsPref.isAds()) {
                        iv_ad_icon_title.setVisibility(View.VISIBLE);
                        lay_updateApp.setVisibility(View.GONE);
                        lay_message.setVisibility(View.GONE);
                        lay_ads.setVisibility(View.VISIBLE);
                        tv_dialog_title.setText(adsPref.adDialogTitle());
                        tv_app_cancel.setText(adsPref.adButtonText());

                        if (adsPref.adShowCancel()) {
                            tv_app_cancel.setVisibility(View.VISIBLE);
                        } else {
                            tv_app_cancel.setVisibility(View.GONE);
                        }

                        tv_ad_message.setText(adsPref.adMessage());
                        if (!this.isFinishing() && !this.isDestroyed()) {
                            Glide.with(this).load(adsPref.adBannerUrl()).into(iv_ad_banner);
                            Glide.with(this).load(adsPref.adIconUrl()).into(iv_app_icon);
                        }
                        tv_app_name.setText(adsPref.adAppName());
                        tv_app_shortdesc.setText(adsPref.adShortDesc());

                        tv_app_download.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (adsPref.adAppUrl().contains("play.google")) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adsPref.adAppUrl()));
                                    startActivity(intent);
                                } else {
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                        if (!getPackageManager().canRequestPackageInstalls()) {
//                                            startActivityForResult(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
//                                                    .setData(Uri.parse(String.format("package:%s", getPackageName()))), 1);
//                                        } else {
//                                            new DownloadApk(BaseSimpleClass.this).startDownloadingApk(adsPref.adAppUrl());
//                                        }
//                                    }
                                    openLinkct(BaseSimpleClass.this, adsPref.adAppUrl());

                                }
                            }
                        });

                        tv_app_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                serviceDialog.dismiss();
                            }
                        });

                        if (adsPref.adAppUrl().contains("play.google.com")) {
                            String link = adsPref.adAppUrl();
                            String[] s1 = link.split("id=");
                            String[] s2 = s1[1].split("&");
                            String app_id = s2[0].toString();
                            if (!isAppInstalled(app_id)) {
                                this.serviceDialog.show();
                            }
                        } else {
                            this.serviceDialog.show();
                        }
                    }
                } else if (adsPref.isNotification()) {
                    lay_ads.setVisibility(View.GONE);
                    lay_updateApp.setVisibility(View.GONE);
                    lay_message.setVisibility(View.VISIBLE);
                    tv_dialog_title.setText(adsPref.notDialogTitle());
                    iv_ad_icon_title.setVisibility(View.GONE);
                    tv_message.setText(adsPref.notMessage());
                    if (adsPref.notShowCancel()) {
                        tv_not_cancel_button.setVisibility(View.VISIBLE);
                    } else {
                        tv_not_cancel_button.setVisibility(View.GONE);
                    }
                    tv_not_cancel_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            serviceDialog.dismiss();
                        }
                    });
                    this.serviceDialog.show();
                } else if (adsPref.isAds()) {
                    iv_ad_icon_title.setVisibility(View.VISIBLE);
                    lay_updateApp.setVisibility(View.GONE);
                    lay_message.setVisibility(View.GONE);
                    lay_ads.setVisibility(View.VISIBLE);
                    tv_dialog_title.setText(adsPref.adDialogTitle());
                    tv_app_download.setText(adsPref.adButtonText());

                    if (adsPref.adShowCancel()) {
                        tv_app_cancel.setVisibility(View.VISIBLE);
                    } else {
                        tv_app_cancel.setVisibility(View.GONE);
                    }

                    tv_ad_message.setText(adsPref.adMessage());
                    if (!this.isFinishing() && !this.isDestroyed()) {
                        Glide.with(this).load(adsPref.adBannerUrl()).into(iv_ad_banner);
                        Glide.with(this).load(adsPref.adIconUrl()).into(iv_app_icon);
                    }
                    tv_app_name.setText(adsPref.adAppName());
                    tv_app_shortdesc.setText(adsPref.adShortDesc());

                    tv_app_download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (adsPref.adAppUrl().contains("play.google")) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adsPref.adAppUrl()));
                                startActivity(intent);
                            } else {
                                openLinkct(BaseSimpleClass.this, adsPref.adAppUrl());
                            }
                        }
                    });

                    tv_app_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            serviceDialog.dismiss();
                        }
                    });

                    if (adsPref.adAppUrl().contains("play.google.com")) {
                        String link = adsPref.adAppUrl();
                        String[] s1 = link.split("id=");
                        String[] s2 = s1[1].split("&");
                        String app_id = s2[0].toString();
                        if (!isAppInstalled(app_id)) {
                            this.serviceDialog.show();
                        }
                    } else {
                        this.serviceDialog.show();
                    }
                }
            }
        }


    }

    public boolean isAppInstalled(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public void loadInterstitial1() {
        if (isConnected(this) && mInterstitialAd1 == null) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            InterstitialAd.load(this, adsPref.gInter1(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd1 = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    mInterstitialAd1 = null;
                }
            });

        }
    }

    public void loadInterstitial2() {
        if (isConnected(this) && mInterstitialAd2 == null) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            InterstitialAd.load(this, adsPref.gInter2(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd2 = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    mInterstitialAd2 = null;
                }
            });
        }


    }

    public void loadInterstitial3() {
        if (isConnected(this) && mInterstitialAd3 == null) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            InterstitialAd.load(this, adsPref.gInter3(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd3 = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    mInterstitialAd3 = null;
                }
            });
        }
    }

    public void loadInterstitial1FB() {
        if (isConnected(this)) {
            if (interstitialAd1 == null) {
                interstitialAd1 = new com.facebook.ads.InterstitialAd(this, adsPref.fbInter1());
                InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {

                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        loadInterstitial1FB();
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        interstitialAd1 = null;
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                };
                interstitialAd1.loadAd(
                        interstitialAd1.buildLoadAdConfig()
                                .withAdListener(interstitialAdListener)
                                .build());
            }
        }
    }

    public void loadInterstitial2FB() {
        if (isConnected(this) && interstitialAd2 == null) {
            interstitialAd2 = new com.facebook.ads.InterstitialAd(this, adsPref.fbInter2());
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    loadInterstitial2FB();
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    interstitialAd2 = null;

                }

                @Override
                public void onAdLoaded(Ad ad) {
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            interstitialAd2.loadAd(
                    interstitialAd2.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
        }
    }

    public void loadInterstitial3FB() {
        if (isConnected(this) && interstitialAd3 == null) {
            interstitialAd3 = new com.facebook.ads.InterstitialAd(this, adsPref.fbInter3());
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    loadInterstitial3FB();
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    interstitialAd3 = null;

                }

                @Override
                public void onAdLoaded(Ad ad) {
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            interstitialAd3.loadAd(
                    interstitialAd3.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
        }
    }

    public void showBannerAd(View bannerView) {
        if (bannerNo == 1) {
            showBanner1(bannerView);
        } else if (bannerNo == 2) {
            showBanner2(bannerView);
        } else if (bannerNo == 3) {
            showBanner3(bannerView);
        }
        setBannerNo();
    }

    public void showLargeBannerAd(View largeBannerView) {
        if (bannerNo == 1) {
            showLargeBanner1(largeBannerView);
        } else if (bannerNo == 2) {
            showLargeBanner2(largeBannerView);
        } else if (bannerNo == 3) {
            showLargeBanner3(largeBannerView);
        }
        setBannerNo();
    }

    public void showNativeAd(View nativeView) {
        if (nativeNo == 1) {
            showNativeAd1(nativeView);
        } else if (nativeNo == 2) {
            showNativeAd2(nativeView);
        } else if (nativeNo == 3) {
            showNativeAd3(nativeView);
        }
        setNativeNo();

    }

    public void showNativeAd(View nativeView, View viewInflater) {
        if (nativeNo == 1) {
            showNativeAd1Fragment(nativeView, viewInflater);
        } else if (nativeNo == 2) {
            showNativeAd2Fragment(nativeView, viewInflater);
        } else if (nativeNo == 3) {
            showNativeAd3Fragment(nativeView, viewInflater);
        }
        setNativeNo();

    }

    void setInterNo() {
        if (currentAD % adsPref.adStatus() == 0) {
            if (interNo == 3) {
                interNo = 1;
            } else {
                interNo++;
            }
        }
    }

    void setBannerNo() {
        if (bannerNo == 3) {
            bannerNo = 1;
        } else {
            bannerNo++;
        }
    }

    void setNativeNo() {
        if (nativeNo == 1) {
            nativeNo = 2;
        } else if (nativeNo == 2) {
            nativeNo = 3;
        } else if (nativeNo == 3) {
            nativeNo = 1;
        }
    }

    void setRewardNo() {
        if (rewardNo == 3) {
            rewardNo = 1;
        } else {
            rewardNo++;
        }
    }

    public void showRewardedAd(Activity activity, OnRewardAdClosedListener onRewardAdClosedListener) {
        if (rewardNo == 1) {
            showRewardAd1(activity, onRewardAdClosedListener);
        } else if (rewardNo == 2) {
            showRewardAd2(activity, onRewardAdClosedListener);
        } else if (rewardNo == 3) {
            showRewardAd3(activity, onRewardAdClosedListener);
        } else {
            onRewardAdClosedListener.onRewardAdNotShown();
        }
        setRewardNo();
    }

    public void loadRewardAd1() {
        if (isConnected(this) && gRewardedAd1 == null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(this, adsPref.gRewarded1(),
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            gRewardedAd1 = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            gRewardedAd1 = rewardedAd;
                        }
                    });

        }

    }

    public void loadRewardAd2() {
        if (isConnected(this) && gRewardedAd2 == null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(this, adsPref.gRewarded2(),
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            gRewardedAd2 = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            gRewardedAd2 = rewardedAd;
                        }
                    });

        }

    }

    public void loadRewardAd3() {
        if (isConnected(this) && gRewardedAd3 == null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(this, adsPref.gRewarded3(),
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            gRewardedAd3 = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            gRewardedAd3 = rewardedAd;
                        }
                    });

        }

    }

    public void showRewardAd1(Activity activity, OnRewardAdClosedListener onRewardAdClosedListener) {
        if (isConnected(this) && gRewardedAd1 != null) {
            gRewardedAd1.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    isUserRewarded1 = true;
                }
            });
            gRewardedAd1.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    onRewardAdClosedListener.onRewardAdNotShown();
                    gRewardedAd1 = null;
                    isUserRewarded1 = false;
                    loadRewardAd1();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    if (isUserRewarded1) {
                        onRewardAdClosedListener.onRewardSuccess();
                    } else {
                        onRewardAdClosedListener.onRewardFailed();
                    }
                    gRewardedAd1 = null;
                    isUserRewarded1 = false;
                    loadRewardAd1();
                }
            });

        } else if (isConnected(this)) {
            showRewardAd1Fb(activity, onRewardAdClosedListener);
        } else {
            onRewardAdClosedListener.onRewardAdNotShown();
        }
    }


    public void showRewardAd1Fb(Activity activity, OnRewardAdClosedListener onRewardAdClosedListener) {
        showProgress(activity, "Loading Video Ad...");
        fbRewardedAd1 = new RewardedVideoAd(this, adsPref.fbRewarded1());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoCompleted() {
                isUserRewarded1 = true;
            }

            @Override
            public void onRewardedVideoClosed() {
                if (isUserRewarded1) {
                    onRewardAdClosedListener.onRewardSuccess();
                } else {
                    onRewardAdClosedListener.onRewardFailed();
                }
                isUserRewarded1 = false;
                ConstantAds.IS_APP_KILLED = false;
                ConstantAds.IS_INTER_SHOWING = false;
                loadRewardAd1();
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                dismisProgress(activity);
                onRewardAdClosedListener.onRewardAdNotShown();
                isUserRewarded1 = false;
                loadRewardAd1();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                dismisProgress(activity);
                ConstantAds.IS_APP_KILLED = true;
                ConstantAds.IS_INTER_SHOWING = true;
                fbRewardedAd1.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        fbRewardedAd1.loadAd(
                fbRewardedAd1.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());

    }

    public void showRewardAd2Fb(Activity activity, OnRewardAdClosedListener onRewardAdClosedListener) {
        showProgress(activity, "Loading Video Ad...");
        fbRewardedAd2 = new RewardedVideoAd(this, adsPref.fbRewarded2());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoCompleted() {
                isUserRewarded2 = true;
            }

            @Override
            public void onRewardedVideoClosed() {
                if (isUserRewarded2) {
                    onRewardAdClosedListener.onRewardSuccess();
                } else {
                    onRewardAdClosedListener.onRewardFailed();
                }
                isUserRewarded2 = false;
                ConstantAds.IS_APP_KILLED = false;
                ConstantAds.IS_INTER_SHOWING = false;
                loadRewardAd2();
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                dismisProgress(activity);
                onRewardAdClosedListener.onRewardAdNotShown();
                isUserRewarded2 = false;
                loadRewardAd2();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                dismisProgress(activity);
                ConstantAds.IS_APP_KILLED = true;
                ConstantAds.IS_INTER_SHOWING = true;
                fbRewardedAd2.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        fbRewardedAd2.loadAd(
                fbRewardedAd2.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());

    }

    public void showRewardAd3Fb(Activity activity, OnRewardAdClosedListener onRewardAdClosedListener) {
        showProgress(activity, "Loading Video Ad...");
        fbRewardedAd3 = new RewardedVideoAd(this, adsPref.fbRewarded3());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoCompleted() {
                isUserRewarded3 = true;
            }

            @Override
            public void onRewardedVideoClosed() {
                if (isUserRewarded3) {
                    onRewardAdClosedListener.onRewardSuccess();
                } else {
                    onRewardAdClosedListener.onRewardFailed();
                }
                isUserRewarded3 = false;
                ConstantAds.IS_APP_KILLED = false;
                ConstantAds.IS_INTER_SHOWING = false;
                loadRewardAd3();
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                dismisProgress(activity);
                onRewardAdClosedListener.onRewardAdNotShown();
                isUserRewarded3 = false;
                loadRewardAd3();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                dismisProgress(activity);
                ConstantAds.IS_APP_KILLED = true;
                ConstantAds.IS_INTER_SHOWING = true;
                fbRewardedAd3.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        fbRewardedAd3.loadAd(
                fbRewardedAd3.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());

    }


    public void showRewardAd2(Activity activity, OnRewardAdClosedListener onRewardAdClosedListener) {
        if (isConnected(this) && gRewardedAd2 != null) {
            gRewardedAd2.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    isUserRewarded2 = true;
                }
            });
            gRewardedAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    onRewardAdClosedListener.onRewardAdNotShown();
                    gRewardedAd2 = null;
                    isUserRewarded2 = false;
                    loadRewardAd2();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    if (isUserRewarded2) {
                        onRewardAdClosedListener.onRewardSuccess();
                    } else {
                        onRewardAdClosedListener.onRewardFailed();
                    }
                    gRewardedAd2 = null;
                    isUserRewarded2 = false;
                    loadRewardAd2();
                }
            });

        } else if (isConnected(this)) {
            showRewardAd2Fb(activity, onRewardAdClosedListener);
        } else {
            onRewardAdClosedListener.onRewardAdNotShown();
        }
    }

    public void showRewardAd3(Activity activity, OnRewardAdClosedListener onRewardAdClosedListener) {
        if (isConnected(this) && gRewardedAd3 != null) {
            gRewardedAd3.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    isUserRewarded3 = true;
                }
            });
            gRewardedAd3.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    onRewardAdClosedListener.onRewardAdNotShown();
                    gRewardedAd3 = null;
                    isUserRewarded3 = false;
                    loadRewardAd3();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    if (isUserRewarded3) {
                        onRewardAdClosedListener.onRewardSuccess();
                    } else {
                        onRewardAdClosedListener.onRewardFailed();
                    }
                    gRewardedAd3 = null;
                    isUserRewarded3 = false;
                    loadRewardAd3();
                }
            });

        } else if (isConnected(this)) {
            showRewardAd3Fb(activity, onRewardAdClosedListener);
        } else {
            onRewardAdClosedListener.onRewardAdNotShown();
        }
    }

    public void showInterstitialAd(Activity context, Callable<Void> callable) {
        if (isConnected(context)) {
            if (adsPref.cts() && ctc % adsPref.ctc() == 0) {
                callSkip(context, new OnSkipListner() {
                    @Override
                    public void onSkip() {
                        try {
                            callable.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                if (interNo == 1) {
                    showInterstitial1(context, callable);
                } else if (interNo == 2) {
                    showInterstitial2(context, callable);
                } else if (interNo == 3) {
                    showInterstitial3(context, callable);
                } else {
                    try {
                        callable.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                setInterNo();
            }
            ctc++;
        } else {
            try {
                callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    void showInterstitialAdFB(Activity context, Callable<Void> callable) {
        if (interNo == 1) {
            showInterstitial1FB(context, callable);
        } else if (interNo == 2) {
            showInterstitial2FB(context, callable);
        } else if (interNo == 3) {
            showInterstitial3FB(context, callable);
        } else {
            try {
                callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setInterNo();

    }

    void showInterstitial1(Activity context, Callable<Void> params) {
        if (currentAD % adsPref.adStatus() == 0 && isConnected(this)) {
            if (mInterstitialAd1 != null) {
                if (adsPref.showloading()) {
                    withDelay(context, ConstantAds.AD_DELAY, ConstantAds.AD_MESSAGE, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            mInterstitialAd1.show((Activity) context);
                            ConstantAds.IS_APP_KILLED = true;
                            ConstantAds.IS_INTER_SHOWING = true;
                            mInterstitialAd1.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    ConstantAds.IS_APP_KILLED = false;
                                    ConstantAds.IS_INTER_SHOWING = false;
                                    loadInterstitial1();
                                    try {
                                        params.call();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    mInterstitialAd1 = null;

                                    showInterstitial1FB(context, params);
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    mInterstitialAd1 = null;
                                }
                            });
                            return null;
                        }
                    });
                } else {
                    mInterstitialAd1.show((Activity) context);
                    ConstantAds.IS_APP_KILLED = true;
                    ConstantAds.IS_INTER_SHOWING = true;
                    mInterstitialAd1.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            ConstantAds.IS_APP_KILLED = false;
                            ConstantAds.IS_INTER_SHOWING = false;
                            loadInterstitial1();
                            try {
                                params.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            mInterstitialAd1 = null;
                            showInterstitial1FB(context, params);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd1 = null;
                        }
                    });
                }
            } else {
                showInterstitial1FB(context, params);
            }
        } else {
            try {
                params.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentAD++;
    }

    void showInterstitial2(Activity context, Callable<Void> params) {
        if (currentAD % adsPref.adStatus() == 0 && isConnected(this)) {
            if (mInterstitialAd2 != null) {
                if (adsPref.showloading()) {
                    withDelay(context, ConstantAds.AD_DELAY, ConstantAds.AD_MESSAGE, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            mInterstitialAd2.show((Activity) context);
                            ConstantAds.IS_APP_KILLED = true;
                            ConstantAds.IS_INTER_SHOWING = true;
                            mInterstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    ConstantAds.IS_APP_KILLED = false;
                                    ConstantAds.IS_INTER_SHOWING = false;
                                    loadInterstitial2();
                                    try {
                                        params.call();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    mInterstitialAd1 = null;
                                    showInterstitial2FB(context, params);
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    mInterstitialAd2 = null;
                                }
                            });
                            return null;
                        }
                    });
                } else {
                    mInterstitialAd2.show((Activity) context);
                    ConstantAds.IS_APP_KILLED = true;
                    ConstantAds.IS_INTER_SHOWING = true;
                    mInterstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            ConstantAds.IS_APP_KILLED = false;
                            ConstantAds.IS_INTER_SHOWING = false;
                            loadInterstitial2();
                            try {
                                params.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            mInterstitialAd1 = null;
                            showInterstitial2FB(context, params);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd2 = null;
                        }
                    });
                }
            } else {
                showInterstitial2FB(context, params);
            }
        } else {
            try {
                params.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentAD++;

    }

    void showInterstitial3(Activity context, Callable<Void> params) {
        if (currentAD % adsPref.adStatus() == 0 && isConnected(this)) {
            if (mInterstitialAd3 != null) {
                if (adsPref.showloading()) {
                    withDelay(context, ConstantAds.AD_DELAY, ConstantAds.AD_MESSAGE, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            mInterstitialAd3.show((Activity) context);
                            ConstantAds.IS_APP_KILLED = true;
                            ConstantAds.IS_INTER_SHOWING = true;
                            mInterstitialAd3.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    ConstantAds.IS_APP_KILLED = false;
                                    ConstantAds.IS_INTER_SHOWING = false;
                                    loadInterstitial3();
                                    try {
                                        params.call();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    mInterstitialAd1 = null;
                                    showInterstitial3FB(context, params);
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    mInterstitialAd3 = null;
                                }
                            });

                            return null;
                        }
                    });
                } else {
                    mInterstitialAd3.show((Activity) context);
                    ConstantAds.IS_APP_KILLED = true;
                    ConstantAds.IS_INTER_SHOWING = true;
                    mInterstitialAd3.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            ConstantAds.IS_APP_KILLED = false;
                            ConstantAds.IS_INTER_SHOWING = false;
                            loadInterstitial3();
                            try {
                                params.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            mInterstitialAd1 = null;
                            showInterstitial3FB(context, params);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd3 = null;
                        }
                    });
                }

            } else {
                showInterstitial3FB(context, params);
            }
        } else {
            try {
                params.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentAD++;

    }

    void showInterstitial1FB(Activity context, Callable<Void> params) {
        if (interstitialAd1 != null && interstitialAd1.isAdLoaded() && !interstitialAd1.isAdInvalidated()) {
            try {
                params.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            interstitialAd1.show();
            interstitialAd1 = null;
        } else {
            showInhouseInterAd(context, new InhouseInterstitialListener() {
                @Override
                public void onAdShown() {

                }

                @Override
                public void onAdDismissed() {
                    try {
                        params.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    void showInterstitial2FB(Activity context, Callable<Void> params) {
        if (interstitialAd2 != null && interstitialAd2.isAdLoaded() && !interstitialAd2.isAdInvalidated()) {
            try {
                params.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            interstitialAd2.show();
            interstitialAd2 = null;
        } else {
            showInhouseInterAd(context, new InhouseInterstitialListener() {
                @Override
                public void onAdShown() {

                }

                @Override
                public void onAdDismissed() {
                    try {
                        params.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    void showInterstitial3FB(Activity context, Callable<Void> params) {
        if (interstitialAd3 != null && interstitialAd3.isAdLoaded() && !interstitialAd3.isAdInvalidated()) {
            try {
                params.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            interstitialAd3.show();
            interstitialAd3 = null;
        } else {
            showInhouseInterAd(context, new InhouseInterstitialListener() {
                @Override
                public void onAdShown() {

                }

                @Override
                public void onAdDismissed() {
                    try {
                        params.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    void hideInhouseBanner() {
        RelativeLayout lay_banner_ad = findViewById(R.id.lay_banner_ad);
        lay_banner_ad.setVisibility(View.GONE);
    }

    void hideInhouseNative() {
        CardView cardView = findViewById(R.id.native_ad_container);
        cardView.setVisibility(View.GONE);
    }

    void hideInhouseNative(View viewInflater) {
        CardView cardView = viewInflater.findViewById(R.id.native_ad_container);
        cardView.setVisibility(View.GONE);
    }

    void hideInhouseNativeDialog(Dialog dialog) {
        CardView cardView = dialog.findViewById(R.id.native_ad_container);
        cardView.setVisibility(View.GONE);
    }

    void hideInhouseNative2() {
        CardView cardView = findViewById(R.id.native_ad_container2);
        cardView.setVisibility(View.GONE);
    }

    void hideInhouseNative2(View viewInflater) {
        CardView cardView = viewInflater.findViewById(R.id.native_ad_container2);
        cardView.setVisibility(View.GONE);
    }

    void hideInhouseNative3() {
        CardView cardView = findViewById(R.id.native_ad_container3);
        cardView.setVisibility(View.GONE);
    }

    void hideInhouseNative3(View viewInflater) {
        CardView cardView = viewInflater.findViewById(R.id.native_ad_container3);
        cardView.setVisibility(View.GONE);
    }

    void hideInhouseNativeAdapter(CardView cardView) {
        cardView.setVisibility(View.GONE);
    }

    public void showInhouseNativeAd(Boolean isSmall, CardView cardView, InhouseNativeListener
            inhouseNativeListener) {
        try {
            if (adsPref.showAds()) {
                if (adsPref.isInHouseAdLoaded()) {
                    if (adsPref.getInHouseAds().size() != 0) {
                        cardView.setVisibility(View.VISIBLE);
                        inflateNativeAdInHouse(isSmall, cardView);
                        inhouseNativeListener.onAdLoaded();
                        cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cardView.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        cardView.setVisibility(View.GONE);
                        inhouseNativeListener.onAdShowFailed();
                    }
                } else {
                    cardView.setVisibility(View.GONE);
                    inhouseNativeListener.onAdShowFailed();
                }
            } else {
                cardView.setVisibility(View.GONE);
                inhouseNativeListener.onAdShowFailed();
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            cardView.setVisibility(View.GONE);
            inhouseNativeListener.onAdShowFailed();
        }

    }


    void showBanner1(View bannerView) {
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            LinearLayout adContainer = (LinearLayout) this.findViewById(R.id.banner_adView);
            AdView mAdView = new AdView(this);
            mAdView.setAdUnitId(adsPref.gBanner1());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize();
            mAdView.setAdSize(adSize);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mAdView.destroy();
                    showBanner1FB(bannerView);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }
            });
        } else {
            bannerView.setVisibility(View.GONE);
        }

    }

    void showBanner2(View bannerView) {
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            LinearLayout adContainer = (LinearLayout) this.findViewById(R.id.banner_adView);
            AdView mAdView = new AdView(this);
            mAdView.setAdUnitId(adsPref.gBanner2());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize();
            mAdView.setAdSize(adSize);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mAdView.destroy();
                    showBanner2FB(bannerView);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }
            });

        } else {
            bannerView.setVisibility(View.GONE);
        }

    }

    void showBanner3(View bannerView) {
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            LinearLayout adContainer = (LinearLayout) this.findViewById(R.id.banner_adView);
            AdView mAdView = new AdView(this);
            mAdView.setAdUnitId(adsPref.gBanner3());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize();
            mAdView.setAdSize(adSize);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mAdView.destroy();
                    showBanner3FB(bannerView);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }
            });


        } else {
            bannerView.setVisibility(View.GONE);
        }

    }

    void showBanner1FB(View bannerView) {
        com.facebook.ads.AdView adView = null;
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            com.facebook.ads.AdView finalAdView = adView;
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    if (finalAdView != null) {
                        finalAdView.destroy();
                    }
                    showInhouseBannerAd(new InhouseBannerListener() {
                        @Override
                        public void onAdLoaded() {
                            bannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            bannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            adView = new com.facebook.ads.AdView(this, adsPref.fbBanner1(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_adView);
            adContainer.addView(adView);
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(adView);
        } else {
            bannerView.setVisibility(View.GONE);
        }
    }

    void showBanner2FB(View bannerView) {
        com.facebook.ads.AdView adView = null;
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            com.facebook.ads.AdView finalAdView = adView;
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    if (finalAdView != null) {
                        finalAdView.destroy();
                    }
                    showInhouseBannerAd(new InhouseBannerListener() {
                        @Override
                        public void onAdLoaded() {
                            bannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            bannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            adView = new com.facebook.ads.AdView(this, adsPref.fbBanner2(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_adView);
            adContainer.addView(adView);
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(adView);
        } else {
            bannerView.setVisibility(View.GONE);
        }
    }

    void showBanner3FB(View bannerView) {
        com.facebook.ads.AdView adView = null;
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            com.facebook.ads.AdView finalAdView = adView;
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    if (finalAdView != null) {
                        finalAdView.destroy();
                    }
                    showInhouseBannerAd(new InhouseBannerListener() {
                        @Override
                        public void onAdLoaded() {
                            bannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            bannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            adView = new com.facebook.ads.AdView(this, adsPref.fbBanner3(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_adView);
            adContainer.addView(adView);
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(adView);
        } else {
            bannerView.setVisibility(View.GONE);
        }
    }

    void showLargeBanner1(View largeBannerView) {
        if (isConnected(this)) {
            largeBannerView.setVisibility(View.VISIBLE);
            LinearLayout adContainer = (LinearLayout) this.findViewById(R.id.banner_adView);
            AdView mAdView = new AdView(this);
            mAdView.setAdUnitId(adsPref.gBanner1());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = AdSize.LARGE_BANNER;
            mAdView.setAdSize(adSize);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mAdView.destroy();
                    showLargeBanner1FB(largeBannerView);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    hideInhouseBanner();
                    largeBannerView.setVisibility(View.VISIBLE);
                }
            });
        } else {
            largeBannerView.setVisibility(View.GONE);
        }


    }

    void showLargeBanner2(View largeBannerView) {
        if (isConnected(this)) {
            largeBannerView.setVisibility(View.VISIBLE);
            LinearLayout adContainer = (LinearLayout) this.findViewById(R.id.banner_adView);
            AdView mAdView = new AdView(this);
            mAdView.setAdUnitId(adsPref.gBanner2());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = AdSize.LARGE_BANNER;
            mAdView.setAdSize(adSize);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mAdView.destroy();
                    showLargeBanner2FB(largeBannerView);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    hideInhouseBanner();
                    largeBannerView.setVisibility(View.VISIBLE);
                }
            });

        } else {
            largeBannerView.setVisibility(View.GONE);
        }

    }

    void showLargeBanner3(View largeBannerView) {
        if (isConnected(this)) {
            largeBannerView.setVisibility(View.VISIBLE);
            LinearLayout adContainer = (LinearLayout) this.findViewById(R.id.banner_adView);
            AdView mAdView = new AdView(this);
            mAdView.setAdUnitId(adsPref.gBanner3());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = AdSize.LARGE_BANNER;
            mAdView.setAdSize(adSize);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mAdView.destroy();
                    showLargeBanner3FB(largeBannerView);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    hideInhouseBanner();
                    largeBannerView.setVisibility(View.VISIBLE);
                }
            });

        } else {
            largeBannerView.setVisibility(View.GONE);
        }

    }

    void showLargeBanner1FB(View bannerView) {
        com.facebook.ads.AdView adView = null;
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            com.facebook.ads.AdView finalAdView = adView;
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    if (finalAdView != null) {
                        finalAdView.destroy();
                    }
                    showInhouseBannerAd(new InhouseBannerListener() {
                        @Override
                        public void onAdLoaded() {
                            bannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            bannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            adView = new com.facebook.ads.AdView(this, adsPref.fbBanner1(), com.facebook.ads.AdSize.BANNER_HEIGHT_90);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_adView);
            adContainer.addView(adView);
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(adView);
        } else {
            bannerView.setVisibility(View.GONE);
        }
    }

    void showLargeBanner2FB(View bannerView) {
        com.facebook.ads.AdView adView = null;
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            com.facebook.ads.AdView finalAdView = adView;
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    if (finalAdView != null) {
                        finalAdView.destroy();
                    }
                    showInhouseBannerAd(new InhouseBannerListener() {
                        @Override
                        public void onAdLoaded() {
                            bannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            bannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            adView = new com.facebook.ads.AdView(this, adsPref.fbBanner2(), com.facebook.ads.AdSize.BANNER_HEIGHT_90);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_adView);
            adContainer.addView(adView);
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(adView);
        } else {
            bannerView.setVisibility(View.GONE);
        }
    }

    void showLargeBanner3FB(View bannerView) {
        com.facebook.ads.AdView adView = null;
        if (isConnected(this)) {
            bannerView.setVisibility(View.VISIBLE);
            com.facebook.ads.AdView finalAdView = adView;
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    if (finalAdView != null) {
                        finalAdView.destroy();
                    }
                    showInhouseBannerAd(new InhouseBannerListener() {
                        @Override
                        public void onAdLoaded() {
                            bannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            bannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    hideInhouseBanner();
                    bannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            adView = new com.facebook.ads.AdView(this, adsPref.fbBanner3(), com.facebook.ads.AdSize.BANNER_HEIGHT_90);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_adView);
            adContainer.addView(adView);
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
            hideInhouseBanner();
            adContainer.removeAllViews();
            adContainer.addView(adView);
        } else {
            bannerView.setVisibility(View.GONE);
        }
    }

    void showNativeAd1FBAdapter(View nativeContainer, RelativeLayout nativeView, CardView cardView) {
        if (!adsPref.fbNative1().equals("na")) {
            nativeContainer.setVisibility(View.VISIBLE);
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative1());
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, cardView, new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeContainer.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    cardView.setVisibility(View.GONE);
                    nativeContainer.setVisibility(View.VISIBLE);
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, nativeView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, cardView, new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeContainer.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeAd2FBAdapter(View nativeContainer, RelativeLayout nativeView, CardView cardView) {
        if (!adsPref.fbNative2().equals("na")) {
            nativeContainer.setVisibility(View.VISIBLE);
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative2());
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, cardView, new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeContainer.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    cardView.setVisibility(View.GONE);
                    nativeContainer.setVisibility(View.VISIBLE);
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, nativeView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, cardView, new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeContainer.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeAd3FBAdapter(View nativeContainer, RelativeLayout nativeView, CardView cardView) {
        if (!adsPref.fbNative3().equals("na")) {
            nativeContainer.setVisibility(View.VISIBLE);
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative3());
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, cardView, new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeContainer.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    cardView.setVisibility(View.GONE);
                    nativeContainer.setVisibility(View.VISIBLE);
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, nativeView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, cardView, new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeContainer.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeBannerAd1FBAdapter(View nativeContainer, RelativeLayout nativeBannerView, CardView cardView) {
        if (!adsPref.fbNativeBanner1().equals("na")) {
            nativeContainer.setVisibility(View.VISIBLE);
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner1());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, cardView, new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeContainer.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    cardView.setVisibility(View.GONE);
                    nativeContainer.setVisibility(View.VISIBLE);
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, nativeBannerView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            showInhouseNativeAd(true, cardView, new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    Log.e("showNativeBanner1", "OnAdLoaded");
                    nativeContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    Log.e("showNativeBanner1", "onAdShowFailed");
                    nativeContainer.setVisibility(View.GONE);
                }
            });
        }


    }

    void showNativeBannerAd2FBAdapter(View nativeContainer, RelativeLayout nativeBannerView, CardView cardView) {
        if (!adsPref.fbNativeBanner2().equals("na")) {
            nativeContainer.setVisibility(View.VISIBLE);
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner2());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, cardView, new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeContainer.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    cardView.setVisibility(View.GONE);
                    nativeContainer.setVisibility(View.VISIBLE);
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, nativeBannerView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            showInhouseNativeAd(true, cardView, new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    Log.e("showNativeBanner1", "OnAdLoaded");
                    nativeContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    Log.e("showNativeBanner1", "onAdShowFailed");
                    nativeContainer.setVisibility(View.GONE);
                }
            });
        }


    }

    void showNativeBannerAd3FBAdapter(View nativeContainer, RelativeLayout nativeBannerView, CardView cardView) {
        if (!adsPref.fbNativeBanner3().equals("na")) {
            nativeContainer.setVisibility(View.VISIBLE);
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner3());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, cardView, new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeContainer.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    cardView.setVisibility(View.GONE);
                    nativeContainer.setVisibility(View.VISIBLE);
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, nativeBannerView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            showInhouseNativeAd(true, cardView, new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    Log.e("showNativeBanner1", "OnAdLoaded");
                    nativeContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    Log.e("showNativeBanner1", "onAdShowFailed");
                    nativeContainer.setVisibility(View.GONE);
                }
            });
        }


    }

    public void showNativeAdAdapter(View nativeContainer, TemplateView view, RelativeLayout fbNativeAdView, CardView cardView) {
        if (isConnected(this)) {
            if (nativeNo == 1) {
                showNativeAd1Adapter(nativeContainer, view, fbNativeAdView, cardView);
            } else if (nativeNo == 2) {
                showNativeAd2Adapter(nativeContainer, view, fbNativeAdView, cardView);
            } else if (nativeNo == 3) {
                showNativeAd3Adapter(nativeContainer, view, fbNativeAdView, cardView);
            }
        } else {
            nativeContainer.setVisibility(View.GONE);
        }

        setNativeNo();
    }

    void showNativeAd1Adapter(View nativeContainer, TemplateView view, RelativeLayout fbNativeAdView, CardView cardView) {
        if (!adsPref.gNative1().equals("na")) {
            AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative1())
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            nativeContainer.setVisibility(View.VISIBLE);
                            view.setVisibility(View.VISIBLE);
                            view.setNativeAd(nativeAd);
                        }
                    }).withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                            view.setVisibility(View.GONE);
                            if (view.getTemplateTypeName().equals("small_template")) {
                                showNativeBannerAd1FBAdapter(nativeContainer, fbNativeAdView, cardView);
                            } else {
                                showNativeAd1FBAdapter(nativeContainer, fbNativeAdView, cardView);
                            }
                        }

                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                        }
                    })
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            view.setVisibility(View.GONE);
            if (view.getTemplateTypeName().equals("small_template")) {
                showNativeBannerAd1FBAdapter(nativeContainer, fbNativeAdView, cardView);
            } else {
                showNativeAd1FBAdapter(nativeContainer, fbNativeAdView, cardView);
            }
        }


    }

    void showNativeAd2Adapter(View nativeContainer, TemplateView view, RelativeLayout fbNativeAdView, CardView cardView) {
        if (!adsPref.gNative2().equals("na")) {
            AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative2())
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            nativeContainer.setVisibility(View.VISIBLE);
                            view.setVisibility(View.VISIBLE);
                            view.setNativeAd(nativeAd);
                        }
                    }).withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                            view.setVisibility(View.GONE);
                            if (view.getTemplateTypeName().equals("small_template")) {
                                showNativeBannerAd2FBAdapter(nativeContainer, fbNativeAdView, cardView);
                            } else {
                                showNativeAd2FBAdapter(nativeContainer, fbNativeAdView, cardView);
                            }
                        }

                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                        }
                    })
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            view.setVisibility(View.GONE);
            if (view.getTemplateTypeName().equals("small_template")) {
                showNativeBannerAd2FBAdapter(nativeContainer, fbNativeAdView, cardView);
            } else {
                showNativeAd2FBAdapter(nativeContainer, fbNativeAdView, cardView);
            }
        }


    }

    void showNativeAd3Adapter(View nativeContainer, TemplateView view, RelativeLayout fbNativeAdView, CardView cardView) {
        if (!adsPref.gNative3().equals("na")) {
            AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative3())
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            nativeContainer.setVisibility(View.VISIBLE);
                            view.setVisibility(View.VISIBLE);
                            view.setNativeAd(nativeAd);
                        }
                    }).withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                            view.setVisibility(View.GONE);
                            if (view.getTemplateTypeName().equals("small_template")) {
                                showNativeBannerAd3FBAdapter(nativeContainer, fbNativeAdView, cardView);
                            } else {
                                showNativeAd3FBAdapter(nativeContainer, fbNativeAdView, cardView);
                            }
                        }

                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                        }
                    })
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            view.setVisibility(View.GONE);
            if (view.getTemplateTypeName().equals("small_template")) {
                showNativeBannerAd3FBAdapter(nativeContainer, fbNativeAdView, cardView);
            } else {
                showNativeAd3FBAdapter(nativeContainer, fbNativeAdView, cardView);
            }
        }


    }


    private void inflateNativeBannerAd(NativeBannerAd nativeBannerAd, RelativeLayout relativeLayout) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        NativeAdLayout nativeAdLayout = new NativeAdLayout(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_layout_fb, relativeLayout, false);
        relativeLayout.addView(adView);


        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(this, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        MediaView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }

    private void inflateNativeAdFacebook(com.facebook.ads.NativeAd nativeAd, RelativeLayout relativeLayout) {
        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        NativeAdLayout nativeAdLayout = new NativeAdLayout(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout adViews = (RelativeLayout) inflater.inflate(R.layout.native_ad_layout_fb, relativeLayout, false);
        relativeLayout.addView(adViews);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = adViews.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adViews.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adViews.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adViews.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adViews.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adViews.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adViews.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adViews.findViewById(R.id.native_ad_call_to_action);
        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adViews,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }

    void showNativeAd1FBFragment(View nativeView, View viewInflater) {
        if (!adsPref.fbNative1().equals("na")) {
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative1());

            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }


                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    hideInhouseNative(viewInflater);
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, viewInflater.findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeView.setVisibility(View.GONE);
                }
            });
        }

    }

    void showNativeAd2FBFragment(View nativeView, View viewInflater) {
        if (!adsPref.fbNative2().equals("na")) {
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative2());

            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }


                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    hideInhouseNative(viewInflater);
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, viewInflater.findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeView.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeAd3FBFragment(View nativeView, View viewInflater) {
        if (!adsPref.fbNative3().equals("na")) {
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative3());

            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }


                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    hideInhouseNative(viewInflater);
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, viewInflater.findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeView.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeAd1FB(View nativeView) {
        if (!adsPref.fbNative1().equals("na")) {
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative1());

            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }


                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    hideInhouseNative();
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeView.setVisibility(View.GONE);
                }
            });

        }


    }

    void showNativeAd2FB(View nativeView) {
        if (!adsPref.fbNative2().equals("na")) {
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative2());

            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }


                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    hideInhouseNative();
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeView.setVisibility(View.GONE);
                }
            });

        }


    }

    void showNativeAd3FB(View nativeView) {
        if (!adsPref.fbNative3().equals("na")) {
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative3());

            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }


                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }
                    hideInhouseNative();
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeView.setVisibility(View.GONE);
                }
            });

        }


    }

    void showNativeAd2FBExtra(View nativeView) {
        if (adsPref.extraBoolean1()) {
            if (!adsPref.fbNative2().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                final com.facebook.ads.NativeAd nativeAd;
                nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative2());
                NativeAdListener nativeAdListener = new NativeAdListener() {
                    @Override
                    public void onMediaDownloaded(Ad ad) {
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        showInhouseNativeAd(false, findViewById(R.id.native_ad_container2), new InhouseNativeListener() {
                            @Override
                            public void onAdLoaded() {
                                nativeView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdShowFailed() {
                                nativeView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        if (nativeAd != ad) {
                            return;
                        }
                        hideInhouseNative2();
                        nativeView.setVisibility(View.VISIBLE);
                        // Inflate Native Ad into Container
                        inflateNativeAdFacebook(nativeAd, findViewById(R.id._lay_native2));
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                };
                nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
            } else {
                showInhouseNativeAd(false, findViewById(R.id.native_ad_container2), new InhouseNativeListener() {
                    @Override
                    public void onAdLoaded() {
                        nativeView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdShowFailed() {
                        nativeView.setVisibility(View.GONE);
                    }
                });
            }

        } else {
            nativeView.setVisibility(View.GONE);
        }

    }

    void showNativeAd2FBExtra(View nativeView, View viewInflater) {
        if (adsPref.extraBoolean1()) {
            if (!adsPref.fbNative2().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                final com.facebook.ads.NativeAd nativeAd;
                nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative2());
                NativeAdListener nativeAdListener = new NativeAdListener() {
                    @Override
                    public void onMediaDownloaded(Ad ad) {
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container2), new InhouseNativeListener() {
                            @Override
                            public void onAdLoaded() {
                                nativeView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdShowFailed() {
                                nativeView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        if (nativeAd != ad) {
                            return;
                        }
                        hideInhouseNative2(viewInflater);
                        nativeView.setVisibility(View.VISIBLE);
                        // Inflate Native Ad into Container
                        inflateNativeAdFacebook(nativeAd, viewInflater.findViewById(R.id._lay_native2));
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                };
                nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
            } else {
                showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container2), new InhouseNativeListener() {
                    @Override
                    public void onAdLoaded() {
                        nativeView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdShowFailed() {
                        nativeView.setVisibility(View.GONE);
                    }
                });
            }

        } else {
            nativeView.setVisibility(View.GONE);
        }


    }

    void showNativeAd3FBExtra(View nativeView) {
        if (adsPref.extraBoolean1()) {
            if (!adsPref.fbNative3().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                final com.facebook.ads.NativeAd nativeAd;
                nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative3());
                NativeAdListener nativeAdListener = new NativeAdListener() {
                    @Override
                    public void onMediaDownloaded(Ad ad) {
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        showInhouseNativeAd(false, findViewById(R.id.native_ad_container3), new InhouseNativeListener() {
                            @Override
                            public void onAdLoaded() {
                                nativeView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdShowFailed() {
                                nativeView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        if (nativeAd != ad) {
                            return;
                        }
                        hideInhouseNative3();
                        nativeView.setVisibility(View.VISIBLE);
                        // Inflate Native Ad into Container
                        inflateNativeAdFacebook(nativeAd, findViewById(R.id._lay_native3));
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                };
                nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
            } else {
                showInhouseNativeAd(false, findViewById(R.id.native_ad_container3), new InhouseNativeListener() {
                    @Override
                    public void onAdLoaded() {
                        nativeView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdShowFailed() {
                        nativeView.setVisibility(View.GONE);
                    }
                });

            }
        } else {
            nativeView.setVisibility(View.GONE);
        }

    }

    void showNativeAd3FBExtra(View nativeView, View viewInflater) {
        if (adsPref.extraBoolean1()) {
            if (!adsPref.fbNative3().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                final com.facebook.ads.NativeAd nativeAd;
                nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative3());
                NativeAdListener nativeAdListener = new NativeAdListener() {
                    @Override
                    public void onMediaDownloaded(Ad ad) {
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container3), new InhouseNativeListener() {
                            @Override
                            public void onAdLoaded() {
                                nativeView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdShowFailed() {
                                nativeView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        if (nativeAd != ad) {
                            return;
                        }
                        hideInhouseNative3(viewInflater);
                        nativeView.setVisibility(View.VISIBLE);
                        // Inflate Native Ad into Container
                        inflateNativeAdFacebook(nativeAd, viewInflater.findViewById(R.id._lay_native3));
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                };
                nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
            } else {
                showInhouseNativeAd(false, viewInflater.findViewById(R.id.native_ad_container3), new InhouseNativeListener() {
                    @Override
                    public void onAdLoaded() {
                        nativeView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdShowFailed() {
                        nativeView.setVisibility(View.GONE);
                    }
                });
            }
        } else {
            nativeView.setVisibility(View.GONE);
        }

    }

    void showNativeAd3FBDialog(Dialog dialog, View nativeView) {
        if (!adsPref.fbNative3().equals("na")) {
            nativeView.setVisibility(View.VISIBLE);
            final com.facebook.ads.NativeAd nativeAd;
            nativeAd = new com.facebook.ads.NativeAd(this, adsPref.fbNative3());
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(false, dialog.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != ad) {
                        return;
                    }

                    hideInhouseNativeDialog(dialog);
                    nativeView.setVisibility(View.VISIBLE);
                    // Inflate Native Ad into Container
                    inflateNativeAdFacebook(nativeAd, dialog.findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } else {
            showInhouseNativeAd(false, dialog.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeView.setVisibility(View.GONE);
                }
            });
        }

    }

    void showNativeBannerAd1FBFragment(View nativeBannerView, View viewInflater) {
        if (!adsPref.fbNativeBanner1().equals("na")) {
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner1());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeBannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeBannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    hideInhouseNative(viewInflater);
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, viewInflater.findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeBannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeBannerView.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeBannerAd2FBFragment(View nativeBannerView, View viewInflater) {
        if (!adsPref.fbNativeBanner2().equals("na")) {
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner2());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeBannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeBannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    hideInhouseNative(viewInflater);
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, viewInflater.findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeBannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeBannerView.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeBannerAd3FBFragment(View nativeBannerView, View viewInflater) {
        if (!adsPref.fbNativeBanner3().equals("na")) {
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner3());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeBannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeBannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    hideInhouseNative(viewInflater);
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, viewInflater.findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeBannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeBannerView.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeBannerAd1FB(View nativeBannerView) {
        if (!adsPref.fbNativeBanner1().equals("na")) {
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner1());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeBannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeBannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    hideInhouseNative();
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            TemplateView template = findViewById(R.id.my_template);
            template.setVisibility(View.GONE);
            showInhouseNativeAd(template.getTemplateTypeName().equals("small_template"), findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeBannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeBannerView.setVisibility(View.GONE);
                }
            });
        }

    }

    void showNativeBannerAd2FB(View nativeBannerView) {
        if (!adsPref.fbNativeBanner2().equals("na")) {
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner2());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeBannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeBannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    hideInhouseNative();
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            TemplateView template = findViewById(R.id.my_template);
            template.setVisibility(View.GONE);
            showInhouseNativeAd(template.getTemplateTypeName().equals("small_template"), findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeBannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeBannerView.setVisibility(View.GONE);
                }
            });
        }

    }

    void showNativeBannerAd3FB(View nativeBannerView) {
        if (!adsPref.fbNativeBanner3().equals("na")) {
            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner3());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeBannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeBannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    hideInhouseNative();
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            TemplateView template = findViewById(R.id.my_template);
            template.setVisibility(View.GONE);
            showInhouseNativeAd(template.getTemplateTypeName().equals("small_template"), findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeBannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeBannerView.setVisibility(View.GONE);
                }
            });
        }

    }

    void showNativeBannerAd2FBExtra(View nativeBannerView) {
        if (adsPref.extraBoolean1()) {
            if (!adsPref.fbNativeBanner2().equals("na")) {
                nativeBannerView.setVisibility(View.VISIBLE);
                NativeBannerAd nativeBannerAd;
                nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner2());
                NativeAdListener nativeAdListener = new NativeAdListener() {

                    @Override
                    public void onMediaDownloaded(Ad ad) {
                        // Native ad finished downloading all assets
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        showInhouseNativeAd(true, findViewById(R.id.native_ad_container2), new InhouseNativeListener() {
                            @Override
                            public void onAdLoaded() {
                                nativeBannerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdShowFailed() {
                                nativeBannerView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        if (nativeBannerAd == null || nativeBannerAd != ad) {
                            return;
                        }
                        hideInhouseNative2();
                        nativeBannerView.setVisibility(View.VISIBLE);
                        inflateNativeBannerAd(nativeBannerAd, findViewById(R.id._lay_native2));
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Native ad clicked
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Native ad impression
                    }
                };
                // load the ad
                nativeBannerAd.loadAd(
                        nativeBannerAd.buildLoadAdConfig()
                                .withAdListener(nativeAdListener)
                                .build());
            } else {
                showInhouseNativeAd(true, findViewById(R.id.native_ad_container2), new InhouseNativeListener() {
                    @Override
                    public void onAdLoaded() {
                        nativeBannerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdShowFailed() {
                        nativeBannerView.setVisibility(View.GONE);
                    }
                });
            }

        } else {
            nativeBannerView.setVisibility(View.GONE);
        }


    }

    void showNativeBannerAd2FBExtra(View nativeBannerView, View viewInflater) {
        if (adsPref.extraBoolean1()) {
            if (!adsPref.fbNativeBanner2().equals("na")) {
                nativeBannerView.setVisibility(View.VISIBLE);
                NativeBannerAd nativeBannerAd;
                nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner2());
                NativeAdListener nativeAdListener = new NativeAdListener() {

                    @Override
                    public void onMediaDownloaded(Ad ad) {
                        // Native ad finished downloading all assets
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container2), new InhouseNativeListener() {
                            @Override
                            public void onAdLoaded() {
                                nativeBannerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdShowFailed() {
                                nativeBannerView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        if (nativeBannerAd == null || nativeBannerAd != ad) {
                            return;
                        }
                        hideInhouseNative2(viewInflater);
                        nativeBannerView.setVisibility(View.VISIBLE);
                        inflateNativeBannerAd(nativeBannerAd, viewInflater.findViewById(R.id._lay_native2));
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Native ad clicked
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Native ad impression
                    }
                };
                // load the ad
                nativeBannerAd.loadAd(
                        nativeBannerAd.buildLoadAdConfig()
                                .withAdListener(nativeAdListener)
                                .build());
            } else {
                showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container2), new InhouseNativeListener() {
                    @Override
                    public void onAdLoaded() {
                        nativeBannerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdShowFailed() {
                        nativeBannerView.setVisibility(View.GONE);
                    }
                });
            }
        } else {
            nativeBannerView.setVisibility(View.GONE);
        }

    }

    void showNativeBannerAd3FBExtra(View nativeBannerView) {
        if (adsPref.extraBoolean1()) {
            if (!adsPref.fbNativeBanner3().equals("na")) {
                nativeBannerView.setVisibility(View.VISIBLE);
                NativeBannerAd nativeBannerAd;
                nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner3());
                NativeAdListener nativeAdListener = new NativeAdListener() {

                    @Override
                    public void onMediaDownloaded(Ad ad) {
                        // Native ad finished downloading all assets
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        showInhouseNativeAd(true, findViewById(R.id.native_ad_container3), new InhouseNativeListener() {
                            @Override
                            public void onAdLoaded() {
                                nativeBannerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdShowFailed() {
                                nativeBannerView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        if (nativeBannerAd == null || nativeBannerAd != ad) {
                            return;
                        }
                        hideInhouseNative3();
                        nativeBannerView.setVisibility(View.VISIBLE);
                        inflateNativeBannerAd(nativeBannerAd, findViewById(R.id._lay_native3));
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Native ad clicked
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Native ad impression
                    }
                };
                // load the ad
                nativeBannerAd.loadAd(
                        nativeBannerAd.buildLoadAdConfig()
                                .withAdListener(nativeAdListener)
                                .build());
            } else {
                showInhouseNativeAd(true, findViewById(R.id.native_ad_container3), new InhouseNativeListener() {
                    @Override
                    public void onAdLoaded() {
                        nativeBannerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdShowFailed() {
                        nativeBannerView.setVisibility(View.GONE);
                    }
                });
            }
        } else {
            nativeBannerView.setVisibility(View.GONE);
        }

    }

    void showNativeBannerAd3FBExtra(View nativeBannerView, View viewInflater) {
        if (adsPref.extraBoolean1()) {
            if (!adsPref.fbNativeBanner3().equals("na")) {
                nativeBannerView.setVisibility(View.VISIBLE);
                NativeBannerAd nativeBannerAd;
                nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner3());
                NativeAdListener nativeAdListener = new NativeAdListener() {

                    @Override
                    public void onMediaDownloaded(Ad ad) {
                        // Native ad finished downloading all assets
                    }

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container3), new InhouseNativeListener() {
                            @Override
                            public void onAdLoaded() {
                                nativeBannerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdShowFailed() {
                                nativeBannerView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        if (nativeBannerAd == null || nativeBannerAd != ad) {
                            return;
                        }
                        hideInhouseNative3(viewInflater);
                        nativeBannerView.setVisibility(View.VISIBLE);
                        inflateNativeBannerAd(nativeBannerAd, viewInflater.findViewById(R.id._lay_native3));
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Native ad clicked
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Native ad impression
                    }
                };
                // load the ad
                nativeBannerAd.loadAd(
                        nativeBannerAd.buildLoadAdConfig()
                                .withAdListener(nativeAdListener)
                                .build());
            } else {
                showInhouseNativeAd(true, viewInflater.findViewById(R.id.native_ad_container3), new InhouseNativeListener() {
                    @Override
                    public void onAdLoaded() {
                        nativeBannerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdShowFailed() {
                        nativeBannerView.setVisibility(View.GONE);
                    }
                });

            }
        } else {
            nativeBannerView.setVisibility(View.GONE);
        }

    }

    void showNativeBannerAd3FBDialog(Dialog dialog, View nativeBannerView) {
        if (!adsPref.fbNativeBanner3().equals("na")) {

            nativeBannerView.setVisibility(View.VISIBLE);
            NativeBannerAd nativeBannerAd;
            nativeBannerAd = new NativeBannerAd(this, adsPref.fbNativeBanner3());
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    showInhouseNativeAd(true, dialog.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                        @Override
                        public void onAdLoaded() {
                            nativeBannerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdShowFailed() {
                            nativeBannerView.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    hideInhouseNativeDialog(dialog);
                    nativeBannerView.setVisibility(View.VISIBLE);
                    inflateNativeBannerAd(nativeBannerAd, dialog.findViewById(R.id._lay_native));
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Native ad clicked
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Native ad impression
                }
            };
            // load the ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            showInhouseNativeAd(true, dialog.findViewById(R.id.native_ad_container), new InhouseNativeListener() {
                @Override
                public void onAdLoaded() {
                    nativeBannerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdShowFailed() {
                    nativeBannerView.setVisibility(View.GONE);
                }
            });
        }
    }

    void showNativeAd1Fragment(View nativeView, View viewInflater) {
        if (isConnected(this)) {
            if (!adsPref.gNative1().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative1())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative(viewInflater);
                                nativeView.setVisibility(View.VISIBLE);
                                TemplateView template = viewInflater.findViewById(R.id.my_template);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = viewInflater.findViewById(R.id.my_template);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd1FBFragment(nativeView, viewInflater);
                                } else {
                                    showNativeAd1FBFragment(nativeView, viewInflater);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = viewInflater.findViewById(R.id.my_template);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd1FBFragment(nativeView, viewInflater);
                } else {
                    showNativeAd1FBFragment(nativeView, viewInflater);
                }
            }
        } else {
            nativeView.setVisibility(View.GONE);
        }

    }

    void showNativeAd2Fragment(View nativeView, View viewInflater) {
        if (isConnected(this)) {
            if (!adsPref.gNative2().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative2())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative(viewInflater);
                                nativeView.setVisibility(View.VISIBLE);
                                TemplateView template = viewInflater.findViewById(R.id.my_template);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = viewInflater.findViewById(R.id.my_template);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd2FBFragment(nativeView, viewInflater);
                                } else {
                                    showNativeAd2FBFragment(nativeView, viewInflater);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = viewInflater.findViewById(R.id.my_template);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd1FBFragment(nativeView, viewInflater);
                } else {
                    showNativeAd1FBFragment(nativeView, viewInflater);
                }
            }
        } else {
            nativeView.setVisibility(View.GONE);
        }

    }

    void showNativeAd3Fragment(View nativeView, View viewInflater) {
        if (isConnected(this)) {
            if (!adsPref.gNative3().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative3())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative(viewInflater);
                                nativeView.setVisibility(View.VISIBLE);
                                TemplateView template = viewInflater.findViewById(R.id.my_template);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = viewInflater.findViewById(R.id.my_template);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd3FBFragment(nativeView, viewInflater);
                                } else {
                                    showNativeAd3FBFragment(nativeView, viewInflater);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = viewInflater.findViewById(R.id.my_template);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd1FBFragment(nativeView, viewInflater);
                } else {
                    showNativeAd1FBFragment(nativeView, viewInflater);
                }
            }
        } else {
            nativeView.setVisibility(View.GONE);
        }
    }

    void showNativeAd1(View nativeView) {
        if (isConnected(this)) {
            if (!adsPref.gNative1().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative1())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative();
                                nativeView.setVisibility(View.VISIBLE);
                                TemplateView template = findViewById(R.id.my_template);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = findViewById(R.id.my_template);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd1FB(nativeView);
                                } else {
                                    showNativeAd1FB(nativeView);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = findViewById(R.id.my_template);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd1FB(nativeView);
                } else {
                    showNativeAd1FB(nativeView);
                }
            }
        } else {
            nativeView.setVisibility(View.GONE);
        }

    }

    void showNativeAd2(View nativeView) {
        if (isConnected(this)) {
            if (!adsPref.gNative2().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative2())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative();
                                nativeView.setVisibility(View.VISIBLE);
                                TemplateView template = findViewById(R.id.my_template);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = findViewById(R.id.my_template);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd2FB(nativeView);
                                } else {
                                    showNativeAd2FB(nativeView);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();
                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = findViewById(R.id.my_template);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd2FB(nativeView);
                } else {
                    showNativeAd2FB(nativeView);
                }
            }
        } else {
            nativeView.setVisibility(View.GONE);
        }

    }

    void showNativeAd3(View nativeView) {

        if (isConnected(this)) {
            if (!adsPref.gNative3().equals("na")) {
                nativeView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative3())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative();
                                nativeView.setVisibility(View.VISIBLE);
                                TemplateView template = findViewById(R.id.my_template);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = findViewById(R.id.my_template);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd3FB(nativeView);
                                } else {
                                    showNativeAd3FB(nativeView);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = findViewById(R.id.my_template);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd3FB(nativeView);
                } else {
                    showNativeAd3FB(nativeView);
                }
            }
        } else {
            nativeView.setVisibility(View.GONE);
        }

    }

    public void showNativeAd2Extra(View nativeExtraView) {
        if (isConnected(this) && adsPref.extraBoolean1()) {
            if (!adsPref.gNative2().equals("na")) {
                nativeExtraView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative2())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative2();
                                nativeExtraView.setVisibility(View.VISIBLE);
                                TemplateView template = findViewById(R.id.my_template2);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = findViewById(R.id.my_template2);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd2FBExtra(nativeExtraView);
                                } else {
                                    showNativeAd2FBExtra(nativeExtraView);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeExtraView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = findViewById(R.id.my_template2);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd2FBExtra(nativeExtraView);
                } else {
                    showNativeAd2FBExtra(nativeExtraView);
                }
            }

        } else {
            nativeExtraView.setVisibility(View.GONE);
        }

    }

    public void showNativeAd2Extra(View nativeExtraView, View viewInflater) {

        if (isConnected(this) && adsPref.extraBoolean1()) {
            if (!adsPref.gNative2().equals("na")) {
                nativeExtraView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative2())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative2(viewInflater);
                                nativeExtraView.setVisibility(View.VISIBLE);
                                TemplateView template = viewInflater.findViewById(R.id.my_template2);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = viewInflater.findViewById(R.id.my_template2);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd2FBExtra(nativeExtraView, viewInflater);
                                } else {
                                    showNativeAd2FBExtra(nativeExtraView, viewInflater);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeExtraView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = viewInflater.findViewById(R.id.my_template2);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd2FBExtra(nativeExtraView, viewInflater);
                } else {
                    showNativeAd2FBExtra(nativeExtraView, viewInflater);
                }
            }
        } else {
            nativeExtraView.setVisibility(View.GONE);
        }

    }

    public void showNativeAd3Extra(View nativeExtraView) {
        if (isConnected(this) && adsPref.extraBoolean1()) {
            if (!adsPref.gNative3().equals("na")) {
                nativeExtraView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative3())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative3();
                                nativeExtraView.setVisibility(View.VISIBLE);
                                TemplateView template = findViewById(R.id.my_template3);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = findViewById(R.id.my_template3);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd3FBExtra(nativeExtraView);
                                } else {
                                    showNativeAd3FBExtra(nativeExtraView);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeExtraView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = findViewById(R.id.my_template3);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd3FBExtra(nativeExtraView);
                } else {
                    showNativeAd3FBExtra(nativeExtraView);
                }
            }
        } else {
            nativeExtraView.setVisibility(View.GONE);
        }

    }

    public void showNativeAd3Extra(View nativeExtraView, View viewInflater) {
        if (isConnected(this) && adsPref.extraBoolean1()) {
            if (!adsPref.gNative3().equals("na")) {
                nativeExtraView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative3())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                hideInhouseNative3(viewInflater);
                                nativeExtraView.setVisibility(View.VISIBLE);
                                TemplateView template = viewInflater.findViewById(R.id.my_template3);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = viewInflater.findViewById(R.id.my_template3);
                                template.setVisibility(View.GONE);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd3FBExtra(nativeExtraView, viewInflater);
                                } else {
                                    showNativeAd3FBExtra(nativeExtraView, viewInflater);
                                }
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeExtraView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();

                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = viewInflater.findViewById(R.id.my_template3);
                template.setVisibility(View.GONE);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd3FBExtra(nativeExtraView, viewInflater);
                } else {
                    showNativeAd3FBExtra(nativeExtraView, viewInflater);
                }
            }
        } else {
            nativeExtraView.setVisibility(View.GONE);
        }

    }

    public void showNativeAdDialog(Dialog dialog, View nativeDialogView) {
        if (isConnected(this)) {
            if (!adsPref.gNative3().equals("na")) {
                nativeDialogView.setVisibility(View.VISIBLE);
                AdLoader adLoader = new AdLoader.Builder(this, adsPref.gNative3())
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                nativeDialogView.setVisibility(View.VISIBLE);
                                TemplateView template = dialog.findViewById(R.id.my_template);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                TemplateView template = dialog.findViewById(R.id.my_template);
                                if (template.getTemplateTypeName().equals("small_template")) {
                                    showNativeBannerAd3FBDialog(dialog, nativeDialogView);
                                } else {
                                    showNativeAd3FBDialog(dialog, nativeDialogView);
                                }

                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                nativeDialogView.setVisibility(View.VISIBLE);
                            }
                        })
                        .build();
                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                TemplateView template = dialog.findViewById(R.id.my_template);
                if (template.getTemplateTypeName().equals("small_template")) {
                    showNativeBannerAd3FBDialog(dialog, nativeDialogView);
                } else {
                    showNativeAd3FBDialog(dialog, nativeDialogView);
                }
            }
        } else {
            nativeDialogView.setVisibility(View.GONE);
        }

    }

    public void loadAppOpen2() {
        if (isConnected(this)) {
            AppOpenAd.load((Context) this, adsPref.gAppopen2(), new AdRequest.Builder().build(), 1, new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    appOpenAd2 = appOpenAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    appOpenAd2 = null;
                }
            });
        }
    }

    public void loadAppOpen3() {
        if (isConnected(this)) {
            AppOpenAd.load((Context) this, adsPref.gAppopen3(), new AdRequest.Builder().build(), 1, new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    appOpenAd3 = appOpenAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    appOpenAd3 = null;
                }
            });
        }
    }

    public void showAppOpen2(Activity context, Callable<Void> callable) {
        if (currentAD % adsPref.adStatus() == 0 && isConnected(this)) {
            if (appOpenAd2 != null) {
                appOpenAd2.show(BaseSimpleClass.this);
                ConstantAds.IS_APP_KILLED = true;
                ConstantAds.IS_INTER_SHOWING = true;
                appOpenAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                    public void onAdDismissedFullScreenContent() {
                        ConstantAds.IS_APP_KILLED = false;
                        ConstantAds.IS_INTER_SHOWING = false;
                        loadAppOpen2();
                        try {
                            callable.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        appOpenAd2 = null;
                        showInterstitial2FB(context, callable);
                    }

                    public void onAdShowedFullScreenContent() {
                        appOpenAd2 = null;
                    }
                });

            } else {
                showInterstitial2FB(context, callable);
            }
        } else {
            try {
                callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentAD++;
    }

    public void showAppOpen3(Activity context, Callable<Void> callable) {
        if (currentAD % adsPref.adStatus() == 0 && isConnected(this)) {
            if (appOpenAd3 != null) {
                appOpenAd3.show(BaseSimpleClass.this);
                ConstantAds.IS_APP_KILLED = true;
                ConstantAds.IS_INTER_SHOWING = true;
                appOpenAd3.setFullScreenContentCallback(new FullScreenContentCallback() {
                    public void onAdDismissedFullScreenContent() {
                        ConstantAds.IS_APP_KILLED = false;
                        ConstantAds.IS_INTER_SHOWING = false;
                        loadAppOpen3();
                        try {
                            callable.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        appOpenAd3 = null;
                        showInterstitial3FB(context, callable);
                    }

                    public void onAdShowedFullScreenContent() {
                        appOpenAd3 = null;
                    }
                });

            } else {
                showInterstitial3FB(context, callable);
            }
        } else {
            try {
                callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentAD++;
    }

    private AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getPortraitAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    public void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void exitApp() {
        ConstantAds.IS_APP_KILLED = true;
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void withDelay(int delay, Callable<Void> callable) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }

    public void withDelay(Activity context, int delay, String message, Callable<Void> callable) {
        showProgress(context, message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismisProgress(context);
                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkAvailable() {
        if (IS_NETWORK_GONE) {
            getAds();
            getInHouseAds();
        }
    }

    @Override
    public void networkUnavailable() {
        IS_NETWORK_GONE = true;
    }

    public void openLink(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void getRandomNumber(int min, int max) {
        final int random = new Random().nextInt((max - min) + 1) + min;
    }
}
