package com.licaigc.view;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.licaigc.widget.WidgetUtils;

/**
 * Created by walfud on 2016/8/5.
 */
public class ViewUtils extends WidgetUtils {
    public static final String TAG = "ViewUtils";

    /** Simpler version of {@link View#findViewById(int)} which infers the target type. */
    @SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
    @CheckResult
    public static <T extends View> T findViewById(@NonNull View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    /** Simpler version of {@link Activity#findViewById(int)} which infers the target type. */
    @SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
    @CheckResult
    public static <T extends View> T findViewById(@NonNull Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }

    /** Simpler version of {@link Dialog#findViewById(int)} which infers the target type. */
    @SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
    @CheckResult
    public static <T extends View> T findViewById(@NonNull Dialog dialog, @IdRes int id) {
        return (T) dialog.findViewById(id);
    }
}
