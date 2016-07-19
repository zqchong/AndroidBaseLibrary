package com.licaigc.widget;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by walfud on 2016/7/18.
 */
public class WidgetUtils {
    public static final String TAG = "WidgetUtils";

    /**
     * 判断某个 `MotionEvent` 事件是否在某控件内.
     * @param motionEvent
     * @param view
     * @return
     */
    public static boolean isTouchInView(MotionEvent motionEvent, View view) {
        int[] viewXy = new int[2];
        view.getLocationOnScreen(viewXy);
        Rect viewRect = new Rect(viewXy[0], viewXy[1], viewXy[0] + view.getWidth(), viewXy[1] + view.getHeight());

        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();

        return viewRect.contains((int) x, (int) y);
    }
}
