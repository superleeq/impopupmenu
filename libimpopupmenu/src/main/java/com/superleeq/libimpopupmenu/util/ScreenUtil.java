package com.superleeq.libimpopupmenu.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class ScreenUtil {
    /**
     * 获取屏幕高度(像素)
     */
    public static int getScreenHeightPixels(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static Resources getResources(Context context) {
        if (context == null) {
            return Resources.getSystem();
        } else {
            return context.getResources();
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    public static int dp2px(Context context, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, getResources(context).getDisplayMetrics());
    }

    public static int sp2px(Context context, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                value, getResources(context).getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度(像素)
     */
    public static int getScreenWidthPixels(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
