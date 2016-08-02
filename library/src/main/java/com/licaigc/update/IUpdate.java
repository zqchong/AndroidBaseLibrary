package com.licaigc.update;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by walfud on 2016/7/28.
 * <a href="http://www.tuluu.com/platform/ymir/wikis/home">doc</a>
 */
public interface IUpdate {
    @GET("update/check")
    Observable<ResponseCheckUpdate> checkUpdate(@Query("pkg_name") String pkgName, @Query("version") String versionName, @Query("code") int versionCode, @Query("channel") String channel);
}
