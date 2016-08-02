package com.licaigc.rxjava;

import rx.Subscriber;

/**
 * Created by walfud on 2016/3/4.
 */
public abstract class EasySubscriber<T> extends Subscriber<T> {
    protected T mT = null;

    @Override
    public void onNext(T t) {
        mT = t;
    }

    @Override
    public void onCompleted() {
        try {
            onSuccess(mT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        onFinish(true, mT, null);
    }

    @Override
    public void onError(Throwable throwable) {
        try {
            onFail(throwable.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        onFinish(false, null, throwable);
    }

    //
    public abstract void onStart();

    /**
     * 与 `onFail` 互斥
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 与 `onSuccess` 互斥
     *
     * @param reason
     */
    public abstract void onFail(String reason);

    /**
     * 总会被调用
     * @param suc 成功返回 `true`, 失败返回 `false`
     * @param result 成功时有效
     * @param throwable 失败时有效
     */
    public abstract void onFinish(boolean suc, T result, Throwable throwable);
}
