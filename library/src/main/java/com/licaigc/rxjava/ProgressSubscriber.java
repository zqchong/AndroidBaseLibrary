package com.licaigc.rxjava;

/**
 * Created by walfud on 2016/7/29.
 */
public abstract class ProgressSubscriber<T> extends EasySubscriber<T> {
    protected long mMin;
    protected long mMax;
    protected long mCurr;

    public void setRange(long min, long max) {
        mMin = min;
        mMax = max;
        mCurr = min;
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);

        mCurr = getCurrValue(mCurr, mT);
        if (!onProgress(mT, (int) (mCurr * 100 / (mMax - mMin)))) {
            throw new RuntimeException("stopped by `onProgress`");
        }
    }

    /**
     *
     * @param t
     * @param progress [1, 100]
     * @return 返回 false 表明请求取消任务
     */
    public abstract boolean onProgress(T t, int progress);

    //
    /**
     * 根据已有值 `before` 计算当前值. 用于 `onProgress` 计算百分比
     * @param before
     * @param t
     * @return
     */
    protected abstract long getCurrValue(long before, T t);
}
