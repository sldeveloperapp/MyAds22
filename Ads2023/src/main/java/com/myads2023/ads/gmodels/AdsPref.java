package com.myads2023.ads.gmodels;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myads2023.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdsPref {

    Context context;
    SharedPreferences adsPreference;
    SharedPreferences.Editor editor;

    public AdsPref(Context context) {
        this.context = context;
        adsPreference = context.getSharedPreferences("AdsPrefrence2023", Context.MODE_PRIVATE);
        editor = adsPreference.edit();

    }

    public void setAdsDefaults(Integer ads_status, Boolean show_loading,

                               String g_app_id,

                               String g_banner1, String g_banner2, String g_banner3,
                               String g_inter1, String g_inter2, String g_inter3,
                               String g_appopen1, String g_appopen2, String g_appopen3,
                               String g_native1, String g_native2, String g_native3,
                               String g_rewarded1, String g_rewarded2, String g_rewarded3,
                               String g_rewardinter1, String g_rewardinter2, String g_rewardinter3,

                               String fb_banner1, String fb_banner2, String fb_banner3,
                               String fb_inter1, String fb_inter2, String fb_inter3,
                               String fb_native1, String fb_native2, String fb_native3,
                               String fb_nb1, String fb_nb2, String fb_nb3,
                               String fb_rewarded1, String fb_rewarded2, String fb_rewarded3,

                               String extra_string1, String extra_string2, String extra_string3, String extra_string4,
                               Boolean extra_boolean1, Boolean extra_boolean2, Boolean extra_boolean3, Boolean extra_boolean4,
                               Integer extra_integer1, Integer extra_integer2, Integer extra_integer3, Integer extra_integer4,

                               String dl1, String dl2, String dl3, Integer dlc,


                               String l1, String l2, String l3, Integer lc, Integer lof, Boolean fl,
                               Integer ctc, Integer ctdelay, Boolean cts,

                               Boolean isUpdate, Boolean isAds, Boolean isNotification,

                               String ad_dialog_title, String ad_app_name, String ad_app_short_desc, String ad_message,
                               String ad_app_url, String ad_icon_url, String ad_banner_url, String ad_button_text, Boolean ad_show_cancel,

                               String not_dialog_title, String not_message, Boolean not_show_cancel,

                               String update_dialog_title, String update_title, String update_app_url, String update_message,
                               String update_version_name, Boolean update_show_cancel
    ) {
        if (adsPreference != null) {

            editor = adsPreference.edit();
            editor.putInt("ads_status", ads_status);
            editor.putBoolean("show_loading", show_loading);

            editor.putString("g_app_id", g_app_id);
            editor.putString("g_banner1", g_banner1);
            editor.putString("g_banner2", g_banner2);
            editor.putString("g_banner3", g_banner3);
            editor.putString("g_inter1", g_inter1);
            editor.putString("g_inter2", g_inter2);
            editor.putString("g_inter3", g_inter3);
            editor.putString("g_appopen1", g_appopen1);
            editor.putString("g_appopen2", g_appopen2);
            editor.putString("g_appopen3", g_appopen3);
            editor.putString("g_native1", g_native1);
            editor.putString("g_native2", g_native2);
            editor.putString("g_native3", g_native3);
            editor.putString("g_rewarded1", g_rewarded1);
            editor.putString("g_rewarded2", g_rewarded2);
            editor.putString("g_rewarded3", g_rewarded3);
            editor.putString("g_rewardinter1", g_rewardinter1);
            editor.putString("g_rewardinter2", g_rewardinter2);
            editor.putString("g_rewardinter3", g_rewardinter3);

            editor.putString("fb_banner1", fb_banner1);
            editor.putString("fb_banner2", fb_banner2);
            editor.putString("fb_banner3", fb_banner3);
            editor.putString("fb_inter1", fb_inter1);
            editor.putString("fb_inter2", fb_inter2);
            editor.putString("fb_inter3", fb_inter3);
            editor.putString("fb_native1", fb_native1);
            editor.putString("fb_native2", fb_native2);
            editor.putString("fb_native3", fb_native3);
            editor.putString("fb_nb1", fb_nb1);
            editor.putString("fb_nb2", fb_nb2);
            editor.putString("fb_nb3", fb_nb3);
            editor.putString("fb_rewarded1", fb_rewarded1);
            editor.putString("fb_rewarded2", fb_rewarded2);
            editor.putString("fb_rewarded3", fb_rewarded3);

            editor.putString("extra_string1", extra_string1);
            editor.putString("extra_string2", extra_string2);
            editor.putString("extra_string3", extra_string3);
            editor.putString("extra_string4", extra_string4);
            editor.putBoolean("extra_boolean1", extra_boolean1);
            editor.putBoolean("extra_boolean2", extra_boolean2);
            editor.putBoolean("extra_boolean3", extra_boolean3);
            editor.putBoolean("extra_boolean4", extra_boolean4);
            editor.putInt("extra_integer1", extra_integer1);
            editor.putInt("extra_integer2", extra_integer2);
            editor.putInt("extra_integer3", extra_integer3);
            editor.putInt("extra_integer4", extra_integer4);

            editor.putString("dl1", dl1);
            editor.putString("dl2", dl2);
            editor.putString("dl3", dl3);
            editor.putInt("dlc", dlc);

            editor.putString("l1", l1);
            editor.putString("l2", l2);
            editor.putString("l3", l3);
            editor.putInt("lc", lc);
            editor.putInt("lof", lof);
            editor.putBoolean("fl", fl);
            editor.putInt("ctc", ctc);
            editor.putInt("ctdelay", ctdelay);
            editor.putBoolean("cts", cts);

            editor.putBoolean("isUpdate", isUpdate);
            editor.putBoolean("isAds", isAds);
            editor.putBoolean("isNotification", isNotification);

            editor.putString("ad_dialog_title", ad_dialog_title);
            editor.putString("ad_app_name", ad_app_name);
            editor.putString("ad_app_short_desc", ad_app_short_desc);
            editor.putString("ad_message", ad_message);
            editor.putString("ad_app_url", ad_app_url);
            editor.putString("ad_icon_url", ad_icon_url);
            editor.putString("ad_banner_url", ad_banner_url);
            editor.putString("ad_button_text", ad_button_text);
            editor.putBoolean("ad_show_cancel", ad_show_cancel);

            editor.putString("not_dialog_title", not_dialog_title);
            editor.putString("not_message", not_message);
            editor.putBoolean("not_show_cancel", not_show_cancel);

            editor.putString("update_dialog_title", update_dialog_title);
            editor.putString("update_title", update_title);
            editor.putString("update_app_url", update_app_url);
            editor.putString("update_message", update_message);
            editor.putString("update_version_name", update_version_name);
            editor.putBoolean("update_show_cancel", update_show_cancel);
            editor.apply();
        }
    }


    public boolean isAdDownloaded() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("is_ad_downloaded", false);
        }
        return output;
    }

    public void setIsAdDownloaded(Boolean showAds) {
        if (adsPreference != null) {
            editor = adsPreference.edit();
            editor.putBoolean("is_ad_downloaded", showAds);
            editor.apply();
        }
    }

    public boolean showAds() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("show_ads", true);
        }
        return output;
    }

    public void setShowAds(Boolean showAds) {
        if (adsPreference != null) {
            editor = adsPreference.edit();
            editor.putBoolean("show_ads", showAds);
            editor.apply();
        }
    }

    public Integer adStatus() {
        int var = 1;
        if (adsPreference != null) {
            var = adsPreference.getInt("ads_status", ConstantAds.adCountDefault);
        }
        return var;
    }

    public Integer appRunCount() {
        int var = 1;
        if (adsPreference != null) {
            var = adsPreference.getInt("app_run_count_lib", 0);
        }
        return var;
    }

    public Integer appRunCountSplash() {
        int var = 1;
        if (adsPreference != null) {
            var = adsPreference.getInt("app_run_count_lib_splash", 0);
        }
        return var;
    }

    public boolean showloading() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("show_loading", true);
        }
        return output;
    }


    public String gAppId() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_app_id", context.getResources().getString(R.string.admob_app_id));
        }
        return var;
    }

    public String gBanner1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_banner1", context.getResources().getString(R.string.admob_banner_id1));
        }
        return var;
    }

    public String gBanner2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_banner2", context.getResources().getString(R.string.admob_banner_id2));
        }
        return var;
    }

    public String gBanner3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_banner3", context.getResources().getString(R.string.admob_banner_id3));
        }
        return var;
    }

    public String gInter1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_inter1", context.getResources().getString(R.string.admob_inter_id1));
        }
        return var;
    }

    public String gInter2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_inter2", context.getResources().getString(R.string.admob_inter_id2));
        }
        return var;
    }

    public String gInter3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_inter3", context.getResources().getString(R.string.admob_inter_id3));
        }
        return var;
    }

    public String gAppopen1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_appopen1", context.getResources().getString(R.string.admob_app_open_id1));
        }
        return var;
    }

    public String gAppopen2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_appopen2", context.getResources().getString(R.string.admob_app_open_id2));
        }
        return var;
    }

    public String gAppopen3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_appopen3", context.getResources().getString(R.string.admob_app_open_id3));
        }
        return var;
    }

    public String gNative1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_native1", context.getResources().getString(R.string.admob_native_id1));
        }
        return var;
    }

    public String gNative2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_native2", context.getResources().getString(R.string.admob_native_id2));
        }
        return var;
    }

    public String gNative3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_native3", context.getResources().getString(R.string.admob_native_id3));
        }
        return var;
    }

    public String gRewarded1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_rewarded1", context.getResources().getString(R.string.admob_rewarded_id1));
        }
        return var;
    }

    public String gRewarded2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_rewarded2", context.getResources().getString(R.string.admob_rewarded_id2));
        }
        return var;
    }

    public String gRewarded3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_rewarded3", context.getResources().getString(R.string.admob_rewarded_id3));
        }
        return var;
    }

    public String gRewardedInter1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_rewardinter1", context.getResources().getString(R.string.admob_rewarded_inter_id1));
        }
        return var;
    }

    public String gRewardedInter2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_rewardinter2", context.getResources().getString(R.string.admob_rewarded_inter_id2));
        }
        return var;
    }

    public String gRewardedInter3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_rewardinter3", context.getResources().getString(R.string.admob_rewarded_inter_id3));
        }
        return var;
    }

    public String fbBanner1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_banner1", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbBanner2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_banner2", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbBanner3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_banner3", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbInter1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_inter1", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbInter2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_inter2", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbInter3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_inter3", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbNative1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_native1", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbNative2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_native2", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbNative3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_native3", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbNativeBanner1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_nb1", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbNativeBanner2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_nb2", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbNativeBanner3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_nb3", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbRewarded1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_rewarded1", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbRewarded2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_rewarded2", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String fbRewarded3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_rewarded3", context.getResources().getString(R.string.fb_ad_id));
        }
        return var;
    }

    public String extraString1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("extra_string1", "na");
        }
        return var;
    }

    public String extraString2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("extra_string2", "na");
        }
        return var;
    }

    public String extraString3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("extra_string3", "na");
        }
        return var;
    }

    public String extraString4() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("extra_string4", "na");
        }
        return var;
    }

    public boolean extraBoolean1() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("extra_boolean1", false);
        }
        return output;
    }

    public boolean extraBoolean2() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("extra_boolean2", false);
        }
        return output;
    }

    public boolean extraBoolean3() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("extra_boolean3", false);
        }
        return output;
    }

    public boolean extraBoolean4() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("extra_boolean4", false);
        }
        return output;
    }

    public Integer extraInt1() {
        int var = 1;
        if (adsPreference != null) {
            var = adsPreference.getInt("extra_integer1", 0);
        }
        return var;
    }

    public Integer extraInt2() {
        int var = 1;
        if (adsPreference != null) {
            var = adsPreference.getInt("extra_integer2", 0);
        }
        return var;
    }

    public Integer extraInt3() {
        int var = 1;
        if (adsPreference != null) {
            var = adsPreference.getInt("extra_integer3", 0);
        }
        return var;
    }

    public Integer extraInt4() {
        int var = 1;
        if (adsPreference != null) {
            var = adsPreference.getInt("extra_integer4", 0);
        }
        return var;
    }

    public String dl1() {
        return adsPreference.getString("dl1", "https://");
    }

    public String dl2() {
        return adsPreference.getString("dl2", "https://");
    }

    public String dl3() {
        return adsPreference.getString("dl3", "https://");
    }

    public Integer dlc() {
        int var = 1;
        if (adsPreference != null) {
            var = adsPreference.getInt("dlc", 0);
        }
        return var;
    }

    public String l1() {
        return adsPreference.getString("l1", "https://");
    }

    public String l2() {
        return adsPreference.getString("l2", "https://");
    }

    public String l3() {
        return adsPreference.getString("l3", "https://");
    }

    public int lc() {
        return adsPreference.getInt("lc", 0);
    }

    public int ctc() {
        return adsPreference.getInt("ctc", 0);
    }

    public int ctdelay() {
        return adsPreference.getInt("ctdelay", 0);
    }

    public Boolean cts() {
        return adsPreference.getBoolean("cts", false);
    }

    public int lof() {
        return adsPreference.getInt("lof", 0);
    }


    public boolean fl() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("fl", false);
        }
        return output;
    }

    public boolean isUpdate() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("isUpdate", false);
        }
        return output;
    }

    public boolean isAds() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("isAds", false);
        }
        return output;
    }

    public boolean isNotification() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("isNotification", false);
        }
        return output;
    }

    public String adDialogTitle() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("ad_dialog_title", "Checkout This");
        }
        return var;
    }

    public String adAppName() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("ad_app_name", "App Name");
        }
        return var;
    }

    public String adShortDesc() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("ad_app_short_desc", "Short Desc");
        }
        return var;
    }

    public String adMessage() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("ad_message", "Message");
        }
        return var;
    }

    public String adAppUrl() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("ad_app_url", "http://");
        }
        return var;
    }

    public String adIconUrl() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("ad_icon_url", "http://");
        }
        return var;
    }

    public String adBannerUrl() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("ad_banner_url", "http://");
        }
        return var;
    }

    public String adButtonText() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("ad_button_text", "Download");
        }
        return var;
    }

    public boolean adShowCancel() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("ad_show_cancel", true);
        }
        return output;
    }


    public String notDialogTitle() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("not_dialog_title", "Attention!");
        }
        return var;
    }

    public String notMessage() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("not_message", "Maintanence Alert!");
        }
        return var;
    }

    public boolean notShowCancel() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("not_show_cancel", true);
        }
        return output;
    }

    public String updateDialogTitle() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("update_dialog_title", "Update Available");
        }
        return var;
    }

    public String updateTitle() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("update_title", "Update App to use latest features");
        }
        return var;
    }

    public String updateAppUrl() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("update_app_url", "http://");
        }
        return var;
    }

    public String updateMessage() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("update_message", "Bug Fixed");
        }
        return var;
    }

    public String updateVersionName() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("update_version_name", "1.0");
        }
        return var;
    }

    public boolean updateShowCancel() {
        boolean output = false;
        if (adsPreference != null) {
            output = adsPreference.getBoolean("update_show_cancel", true);
        }
        return output;
    }

    public void setInHouseLoaded(boolean isLoaded) {
        editor.putBoolean("isInHouseAdLoaded", isLoaded);
        editor.apply();
    }

    public void setAppRunCount() {
        editor.putInt("app_run_count_lib", (appRunCount() + 1));
        editor.apply();
    }

    public void setAppRunCountSplash() {
        editor.putInt("app_run_count_lib_splash", (appRunCountSplash() + 1));
        editor.apply();
    }

    public void setInHouseAdDetails(ArrayList<IhAdsDetail> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.remove("inHouseAdList");
        editor.apply();
        editor.putString("inHouseAdList", json);
        setInHouseLoaded(true);
        editor.apply();
    }

    public ArrayList<IhAdsDetail> getInHouseAds() {
        Gson gson = new Gson();
        String json = adsPreference.getString("inHouseAdList", "");
        Type type = new TypeToken<ArrayList<IhAdsDetail>>() {
        }.getType();
        ArrayList<IhAdsDetail> ihAdsDetails = gson.fromJson(json, type);
        return ihAdsDetails;
    }

    public Boolean isInHouseAdLoaded() {
        return adsPreference.getBoolean("isInHouseAdLoaded", false);
    }


}



