package com.licaigc;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by walfud on 2016/7/8.
 */
public class PackageUtils {
    public static final String TAG = "PackageUtils";

    // Version code
    public static int getVersionCode() {
        return getVersionCode(AndroidBaseLibrary.getContext().getPackageName());
    }
    public static int getVersionCode(String packageName) {
        return getPackageInfo(packageName, packageInfo -> packageInfo.versionCode);
    }

    // Version name
    public static String getVersionName() {
        return getVersionName(AndroidBaseLibrary.getContext().getPackageName());
    }
    public static String getVersionName(String packageName) {
        return getPackageInfo(packageName, packageInfo -> packageInfo.versionName);
    }

    private static <T> T getPackageInfo(String packageName, IPackageInfoGetter<T> getter) {
        PackageManager packageManager = AndroidBaseLibrary.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return getter.get(packageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //
    private interface IPackageInfoGetter<T> {
        T get(PackageInfo packageInfo);
    }
}
