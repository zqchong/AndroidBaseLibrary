package com.licaigc.update;


import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;

import com.licaigc.AndroidBaseLibrary;
import com.licaigc.PackageUtils;
import com.licaigc.PermissionUtils;
import com.licaigc.rxjava.SimpleEasySubscriber;

import java.io.File;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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

    /**
     * 需要 'WRITE_EXTERNAL_STORAGE' 权限
     * @param context
     * @param appKey
     */
    public static void checkUpdate(final Context context, String appKey) {
        if (!PermissionUtils.hasPermission("android.permission.WRITE_EXTERNAL_STORAGE")) {
            return;
        }

        IUpdate updateInterface = getUpdateInterface();
        final Context appContext = AndroidBaseLibrary.getContext();
        PackageInfo packageInfo = null;
        try {
            packageInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        updateInterface.checkUpdate(appKey, packageInfo.versionName, packageInfo.versionCode, ManifestUtils.getMeta("UMENG_CHANNEL"))
        updateInterface.checkUpdate("com.baidu.appsearch", "2.3.0", 1, "baidushoujizhushou")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseCheckUpdate, Void>() {
                    @Override
                    public Void call(final ResponseCheckUpdate responseCheckUpdate) {
                        if ("none".equalsIgnoreCase(responseCheckUpdate.update)) {
                            return null;
                        }

                        final AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog)
                                .setTitle("有新版本啦")
                                .setMessage(responseCheckUpdate.version)
                                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(responseCheckUpdate.url));
                                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                        request.setDestinationInExternalFilesDir(AndroidBaseLibrary.getContext(), null, responseCheckUpdate.md5);
                                        final DownloadManager downloadManager = (DownloadManager) appContext.getSystemService(Context.DOWNLOAD_SERVICE);
                                        final long id = downloadManager.enqueue(request);
                                        appContext.registerReceiver(new BroadcastReceiver() {
                                            @Override
                                            public void onReceive(Context context, Intent intent) {
                                                Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(id).setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL));
                                                if (cursor.moveToFirst()) {
                                                    Uri apk = Uri.parse(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
                                                    PackageUtils.installPackage(new File(apk.getPath()));
                                                }
                                                cursor.close();
                                            }
                                        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                                        dialog.dismiss();
                                    }
                                });
                        if (!responseCheckUpdate.force) {
                            builder.setNegativeButton("算了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                        builder.show();
                        return null;
                    }
                }).subscribe(new SimpleEasySubscriber<Void>());
    }

    public interface OnCheckUpdate {
        /**
         * @param suc
         * @param file `suc == true` 时有效, 表示下载的文件位置
         * @param err  `suc == false` 是有效, 表示错误的原因
         */
        void onFinish(boolean suc, File file, String err);
    }

    public static void checkUpdate(String appKey, final OnCheckUpdate onCheckUpdate) {
        // TODO:
    }

    //

}
