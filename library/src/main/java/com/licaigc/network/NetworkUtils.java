package com.licaigc.network;

import com.licaigc.io.IoUtils;
import com.licaigc.rxjava.ProgressSubscriber;
import com.licaigc.rxjava.SimpleProgressSubscriber;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.observers.SafeSubscriber;
import rx.schedulers.Schedulers;

/**
 * Created by walfud on 2016/7/28.
 */
public class NetworkUtils {
    public static final String TAG = "NetworkUtils";

    private static INetwork getNetworkInterface() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://empty")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(INetwork.class);
    }

    /**
     * @param url
     * @return 如果下载失败, 则返回 `null`
     */
    public static Observable<byte[]> get(String url) {
        INetwork networkInterface = getNetworkInterface();
        return networkInterface.get(url)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Response<ResponseBody>, byte[]>() {
                    @Override
                    public byte[] call(Response<ResponseBody> response) {
                        byte[] bytes = null;
                        if (response.isSuccessful()) {
                            try {
                                bytes = response.body().bytes();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        return bytes;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Void> downloadFile(String url, final File target) {
        return get(url)
                .observeOn(Schedulers.io())
                .map(new Func1<byte[], Void>() {
                    @Override
                    public Void call(byte[] bytes) {
                        IoUtils.output(target, bytes);
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注意! 此函数必须返回的 Observable 不能再有 map 之类的任何中间 subscriber, 否则 `onProgress` 回调会出问题
     *
     * @param url
     * @return
     */
    public static Observable<byte[]> getLarge(final String url) {
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(final Subscriber<? super byte[]> subscriber) {
                INetwork networkInterface = getNetworkInterface();
                networkInterface.getLarge(url)
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<Response<ResponseBody>, InputStream>() {
                            @Override
                            public InputStream call(Response<ResponseBody> response) {
                                Throwable err = null;
                                if (response.isSuccessful()) {
                                    if (subscriber instanceof SafeSubscriber) {
                                        Subscriber actual = ((SafeSubscriber) subscriber).getActual();
                                        if (actual instanceof ProgressSubscriber) {
                                            ((ProgressSubscriber) actual).setRange(0, response.body().contentLength());
                                        }
                                    }
                                    InputStream inputStream = response.body().byteStream();
                                    final int bufLen = 8 * 1024;
                                    byte[] buf = new byte[bufLen];
                                    try {
                                        int readLen;
                                        while ((readLen = inputStream.read(buf)) != -1) {
                                            if (readLen == bufLen) {
                                                subscriber.onNext(buf);
                                            } else {
                                                // 最后一次, 可能读取不足 bufLen
                                                subscriber.onNext(Arrays.copyOf(buf, readLen));
                                            }
                                        }
                                        return null;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        err = e;
                                    }
                                }
                                throw new RuntimeException(err);
                            }
                        })
                        .subscribe(new Subscriber<InputStream>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(InputStream inputStream) {

                            }
                        });
            }
        });
    }

    public interface OnDownloadFile {
        void onStart();

        /**
         * @param progress [0, 100]
         */
        void onProgress(int progress);

        void onFinish(boolean suc);
    }

    public static void downloadFileWithProgress(final String url, final File target, final OnDownloadFile onDownloadFile) {
        getLarge(url).subscribe(new SimpleProgressSubscriber<byte[]>() {
            private OutputStream mOutputStream = null;

            @Override
            public void onStart() {
                super.onStart();
                try {
                    mOutputStream = new BufferedOutputStream(new FileOutputStream(target));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (onDownloadFile != null) {
                    onDownloadFile.onStart();
                }
            }

            @Override
            public boolean onProgress(byte[] bytes, int progress) {
                try {
                    mOutputStream.write(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                if (onDownloadFile != null) {
                    onDownloadFile.onProgress(progress);
                }
                return true;
            }

            @Override
            public void onFinish(boolean suc, byte[] result, Throwable throwable) {
                super.onFinish(suc, result, throwable);
                boolean realSuc = false;
                try {
                    mOutputStream.close();
                    realSuc = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (onDownloadFile != null) {
                    onDownloadFile.onFinish(suc && realSuc);
                }
            }
        });
    }
}
