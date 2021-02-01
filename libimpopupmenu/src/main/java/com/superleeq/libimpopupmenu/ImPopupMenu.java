package com.superleeq.libimpopupmenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.superleeq.libimpopupmenu.adapter.ImPopupMenuAdapter;
import com.superleeq.libimpopupmenu.bean.PopupItem;
import com.superleeq.libimpopupmenu.util.ScreenUtil;

import java.util.List;
/**
 * @author superleeq@foxmail.com
 */
public class ImPopupMenu {

    private Context mContext;
    private PopupWindow mPopupWindow;
    private View mIndicatorView;
    private GradientDrawable mCornerBackground;
    private PopupDividerItemDecoration mPopupDividerItemDecoration;
    private static final int DEFAULT_BACKGROUND_RADIUS_DP = 5;
    private static final int DEFAULT_SPAN_COUNT = 5;
    private boolean needVerticalDivider;
    private boolean needHorizontalDivider = true;//是否需要横竖方向的分割线
    private int mPopupSpace;//左右靠边时弹出偏移量
    private int mSpanCount;
    private int mTextSizeSp;
    private int mTextColor;
    private int mIconSize;
    private int mDividerPadding = 10;
    private float mDividerHeight = 0.5f;
    private int mDividerColor = Color.parseColor("#80B7B7B7");
    private int mBackgroundColor = Color.parseColor("#515151");
    private int mBackgroundRadius;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mPopupWindowWidth;
    private int mPopupWindowHeight;
    private int mItemPaddingLeft, mItemPaddingRight, mItemPaddingTop, mItemPaddingBottom;
    private int mItemBackgroundPressColor;
    private ImPopupMenuAdapter mImPopupMenuAdapter;

    public void setItemBackroundPressColor(int pressColor) {
        this.mItemBackgroundPressColor = pressColor;
    }

    public void setNeedVerticalDivider(boolean needVerticalDivider) {
        this.needVerticalDivider = needVerticalDivider;
    }

    public void setNeedHorizontalDivider(boolean needHorizontalDivider) {
        this.needHorizontalDivider = needHorizontalDivider;
    }

    public ImPopupMenu(Context context) {
        this.mContext = context;
        mPopupDividerItemDecoration = getTargetItemDecoration(mDividerPadding, mDividerColor, mDividerHeight);
        mBackgroundRadius = ScreenUtil.dp2px(mContext, DEFAULT_BACKGROUND_RADIUS_DP);
        mItemBackgroundPressColor = Color.BLACK;
        mPopupSpace = mBackgroundRadius * 2;
        mSpanCount = DEFAULT_SPAN_COUNT;
        initBackground();
    }

    public void setItemPadding(int left, int top, int right, int bottom) {
        this.mItemPaddingLeft = ScreenUtil.dp2px(mContext, left);
        this.mItemPaddingTop = ScreenUtil.dp2px(mContext, top);
        this.mItemPaddingRight = ScreenUtil.dp2px(mContext, right);
        this.mItemPaddingBottom = ScreenUtil.dp2px(mContext, bottom);
    }

    public void setSpanCount(int spanCount) {
        if (spanCount <= 0) {
            this.mSpanCount = 1;
        } else if (spanCount > 10) {
            this.mSpanCount = 10;
        } else {
            this.mSpanCount = spanCount;
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
        initBackground();
    }

    private void initBackground() {
        mCornerBackground = new GradientDrawable();
        mCornerBackground.setColor(mBackgroundColor);
        mCornerBackground.setCornerRadius(mBackgroundRadius);
        mIndicatorView = getDefaultIndicatorView(mContext);
    }

    public void setTextSizeSp(int textSizeSp) {
        this.mTextSizeSp = textSizeSp;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public void setIconSize(int iconSize) {
        this.mIconSize = iconSize;
    }

    public void setDividerHeight(float dividerHeight) {
        this.mDividerHeight = dividerHeight;
        mPopupDividerItemDecoration.setDividerHeight(ScreenUtil.dp2px(mContext, dividerHeight));
    }

    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        mPopupDividerItemDecoration.setColor(mDividerColor);
    }

    public void setHorizontalDividerPadding(int dividerPadding) {
        this.mDividerPadding = dividerPadding;
        mPopupDividerItemDecoration.setHorizontalPadding(ScreenUtil.dp2px(mContext, dividerPadding));
    }

    public void setVerticalDividerPadding(int verticalPadding) {
        this.mDividerPadding = verticalPadding;
        mPopupDividerItemDecoration.setVerticalPadding(ScreenUtil.dp2px(mContext, verticalPadding));
    }

    private PopupDividerItemDecoration getTargetItemDecoration(int paddingLeftRight, int dividerColor, float dividerHeight) {
        PopupDividerItemDecoration popupDividerItemDecoration = new PopupDividerItemDecoration();
        popupDividerItemDecoration.setHorizontalPadding(ScreenUtil.dp2px(mContext, paddingLeftRight)).setColor(dividerColor).setDividerHeight(dividerHeight);
        return popupDividerItemDecoration;
    }

    public void setCustomItemDecoration(PopupDividerItemDecoration popupDividerItemDecoration) {
        this.mPopupDividerItemDecoration = popupDividerItemDecoration;
    }

    public void show(View anchorView, List<PopupItem> popupItemList, OnImPopupMenuItemClickListener listener) {
        doShow(anchorView, popupItemList, false, listener);
    }

    public void showWithSimpleStyle(View anchorView, List<String> popupItemList, OnImPopupMenuItemClickListener listener) {
        doShow(anchorView, popupItemList, true, listener);
    }

    private void doShow(View anchorView, List popupItemList, boolean isSimpleStyle, OnImPopupMenuItemClickListener listener) {
        if (anchorView == null || popupItemList == null || popupItemList.isEmpty()) {
            return;
        }
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
            return;
        }
        this.mOnImPopupMenuItemClickListener = listener;
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        //
        LinearLayout contentView = new LinearLayout(mContext);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        contentView.setOrientation(LinearLayout.VERTICAL);
        //rv
        RecyclerView rv = new RecyclerView(mContext);
        rv.setBackground(mCornerBackground);
        mSpanCount = popupItemList.size() > mSpanCount ? mSpanCount : popupItemList.size();
        rv.setLayoutManager(new GridLayoutManager(mContext, mSpanCount));
        rv.setPadding(mBackgroundRadius, 0, mBackgroundRadius, 0);
        mImPopupMenuAdapter = new ImPopupMenuAdapter();
        mImPopupMenuAdapter.bindImPopupMenu(this);
        mImPopupMenuAdapter.setItemPadding(mItemPaddingLeft, mItemPaddingTop, mItemPaddingRight, mItemPaddingBottom);
        mImPopupMenuAdapter.setItemList(popupItemList);
        mImPopupMenuAdapter.setIconSize(mIconSize);
        mImPopupMenuAdapter.setTextColor(mTextColor);
        mImPopupMenuAdapter.setTextSizeSp(mTextSizeSp);
        mImPopupMenuAdapter.setItemPressColor(mItemBackgroundPressColor);
        mImPopupMenuAdapter.setOnImPopupMenuItemClickListener(mOnImPopupMenuItemClickListener);
        //分割线
        if (needVerticalDivider) {
            if (isSimpleStyle) {
                mPopupDividerItemDecoration.setVerticalMoreSize(ScreenUtil.dp2px(mContext, 3));
            }
            mPopupDividerItemDecoration.setNeedVerticalDivider(true);
        }
        mPopupDividerItemDecoration.setNeedHorizontalDivider(needHorizontalDivider);
        rv.addItemDecoration(mPopupDividerItemDecoration);
        //
        rv.setAdapter(mImPopupMenuAdapter);
        //计算宽高
        safeSetSpanCount(rv, mImPopupMenuAdapter);
        if (mIndicatorView != null && mIndicatorWidth == 0) {
            LinearLayout.LayoutParams layoutParams;
            if (mIndicatorView.getLayoutParams() == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            } else {
                layoutParams = (LinearLayout.LayoutParams) mIndicatorView.getLayoutParams();
            }
            layoutParams.gravity = Gravity.LEFT;
            mIndicatorView.setLayoutParams(layoutParams);
            if (mIndicatorView.getLayoutParams().width > 0) {
                mIndicatorWidth = mIndicatorView.getLayoutParams().width;
            } else {
                mIndicatorWidth = getViewWidth(mIndicatorView);
            }
        }
        if (mIndicatorView != null && mIndicatorHeight == 0) {
            if (mIndicatorView.getLayoutParams().height > 0) {
                mIndicatorHeight = mIndicatorView.getLayoutParams().height;
            } else {
                mIndicatorHeight = getViewHeight(mIndicatorView);
            }
        }
        if (mPopupWindowHeight == 0) {
            mPopupWindowHeight = getViewHeight(rv) + mIndicatorHeight;
        }
        mPopupWindow = new PopupWindow(contentView, mPopupWindowWidth, mPopupWindowHeight, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //当前view上方是否有足够空间
        int screenWidth = ScreenUtil.getScreenWidthPixels(mContext);
        int x = location[0];
        int y = location[1] - mPopupWindowHeight;
        int translationX = 0;
        if (x + mPopupWindowWidth + mPopupSpace <= screenWidth) {
            //正常从x浮出
            translationX = anchorView.getWidth() / 2 - mBackgroundRadius;
            if (x <= mPopupSpace) {
                if (x + mPopupWindowWidth <= screenWidth + mPopupSpace) {
                    x = mPopupSpace;
                    translationX -= mPopupSpace;
                }
            }
        } else {
            //浮出x开始已不够popup宽度,靠右显示
            int spaceWidth = screenWidth - mPopupWindowWidth;
            translationX = location[0] - spaceWidth + anchorView.getWidth() / 2 - mBackgroundRadius;
            if (mPopupWindowWidth + mPopupSpace <= screenWidth) {
                x = screenWidth - mPopupWindowWidth - mPopupSpace;
                translationX += mPopupSpace;
            }
        }
        if (location[1] - mPopupWindowHeight > ScreenUtil.getStatusHeight(mContext)) {
            contentView.addView(rv);
            contentView.addView(mIndicatorView);
        } else {
            mIndicatorView.setRotation(180f);
            contentView.addView(mIndicatorView);
            contentView.addView(rv);
            y = location[1] + anchorView.getHeight();
        }
        mIndicatorView.setTranslationX(translationX);
        mPopupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y);
    }

    private void safeSetSpanCount(RecyclerView rv, ImPopupMenuAdapter adapter) {
        int screenWidth = ScreenUtil.getScreenWidthPixels(mContext);
        int popupWindowWidth = 0;
        do {
            if (popupWindowWidth > 0) {
                mSpanCount--;
            }
            rv.setLayoutManager(new GridLayoutManager(mContext, mSpanCount));
            rv.setAdapter(adapter);
            popupWindowWidth = getViewWidth(rv);
        } while (popupWindowWidth > screenWidth);
        this.mPopupWindowWidth = popupWindowWidth;
        mImPopupMenuAdapter.setSpanCount(mSpanCount);
        mImPopupMenuAdapter.notifyDataSetChanged();
    }


    public void dismiss() {
        if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
            return;
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public View getDefaultIndicatorView(Context context) {
        return getTriangleIndicatorView(context, ScreenUtil.dp2px(mContext, 10), ScreenUtil.dp2px(mContext, 6), mBackgroundColor);
    }

    public View getTriangleIndicatorView(Context context, final float widthPixel, final float heightPixel, final int color) {
        ImageView indicator = new ImageView(context);
        Drawable drawable = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                Path path = new Path();
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(color);
                paint.setStyle(Paint.Style.FILL);
                path.moveTo(0f, 0f);
                path.lineTo(widthPixel, 0f);
                path.lineTo(widthPixel / 2, heightPixel);
                path.close();
                canvas.drawPath(path, paint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
            }

            @Override
            public int getIntrinsicWidth() {
                return (int) widthPixel;
            }

            @Override
            public int getIntrinsicHeight() {
                return (int) heightPixel;
            }
        };
        indicator.setImageDrawable(drawable);
        return indicator;
    }

    private int getViewWidth(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return view.getMeasuredWidth();
    }

    private int getViewHeight(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return view.getMeasuredHeight();
    }

    private OnImPopupMenuItemClickListener mOnImPopupMenuItemClickListener;

    public void setOnImPopupMenuItemClickListener(OnImPopupMenuItemClickListener mOnImPopupMenuItemClickListener) {
        this.mOnImPopupMenuItemClickListener = mOnImPopupMenuItemClickListener;
    }

    public interface OnImPopupMenuItemClickListener {
        void onImPopupMenuItemClick(int position, String clickItemText);
    }


}
