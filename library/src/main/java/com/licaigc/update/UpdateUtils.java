package com.licaigc.update;


import android.content.Context;
import android.content.pm.PackageInfo;

import com.licaigc.AndroidBaseLibrary;
import com.licaigc.ManifestUtils;

import java.io.File;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by walfud on 2016/7/28.
 */
public class UpdateUtils {
    public static final String TAG = "UpdateUtils";

    private static IUpdate getUpdateInterface() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(String.format("%s/v%s/", "http://192.168.10.115:34000", 1))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(IUpdate.class);
    }

    public static Observable<ResponseCheckUpdate> checkUpdate(String appKey) {
        IUpdate updateInterface = getUpdateInterface();
        Context appContext = AndroidBaseLibrary.getContext();
        PackageInfo packageInfo = null;
        try {
            packageInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateInterface.checkUpdate(appKey, packageInfo.versionName, packageInfo.versionCode, ManifestUtils.getMeta("UMENG_CHANNEL"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface OnCheckUpdate {
        /**
         * @param suc
         * @param file `suc == true` 时有效, 表示下载的文件位置
         * @param err `suc == false` 是有效, 表示错误的原因
         */
        void onFinish(boolean suc, File file, String err);
    }

    public static void checkUpdate(String appKey, final OnCheckUpdate onCheckUpdate) {
        // TODO:
    }

    //

}
