package com.licaigc.rxjava;

/**
 * Created by walfud on 2016/7/29.
 */
public class SimpleProgressSubscriber<T> extends ProgressSubscriber<T> {
    @Override
    public void onStart() {

    }

    @Override
    public boolean onProgress(T t, int progress) {
        return true;
    }


    @Override
    public void onSuccess(T t) {

    }

    @Override
    public void onFail(String reason) {

    }

    @Override
    public void onFinish(boolean suc, T result, Throwable throwable) {

    }

    //

    @Override
    protected long getCurrValue(long before, T t) {
        if (false) {
            // Stub
        } else if (t instanceof byte[]) {
            return before + ((byte[]) t).length;
        }

        throw new RuntimeException("You should impl your own `getCurrValue`");
    }
}
