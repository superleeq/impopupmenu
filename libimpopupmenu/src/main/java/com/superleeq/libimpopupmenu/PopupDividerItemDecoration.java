package com.superleeq.libimpopupmenu;

import android.drm.DrmRights;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
/**
 * @author superleeq@foxmail.com
 */
public class PopupDividerItemDecoration extends RecyclerView.ItemDecoration {

    private float mDividerHeight = 0.5f; //线的高度
    private Paint mPaint;           //画笔将自己做出来的分割线矩形画出颜色
    private float horizontalPadding = 0;       //左右偏移量
    private float verticalPadding = 0;       //上下偏移量
    private boolean needVerticalDivider;//是否需要垂直方向的分割线
    private boolean needHorizontalDivider = true;
    private int verticalMoreSize;//垂直方向分割线多余的size

    public PopupDividerItemDecoration() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);          //抗锯齿
    }

    //    public PopupDividerItemDecoration setSimpleStyle(boolean simpleStyle) {
//        isSimpleStyle = simpleStyle;
//        return this;
//    }


    public void setNeedHorizontalDivider(boolean needHorizontalDivider) {
        this.needHorizontalDivider = needHorizontalDivider;
    }

    public PopupDividerItemDecoration setVerticalMoreSize(int verticalMoreSize) {
        if (verticalPadding > 0) {
            this.verticalMoreSize = verticalMoreSize;
        }
        return this;
    }

    public PopupDividerItemDecoration setNeedVerticalDivider(boolean needVerticalDivider) {
        this.needVerticalDivider = needVerticalDivider;
        return this;
    }

    public PopupDividerItemDecoration setHorizontalPadding(float horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
        return this;
    }

    public PopupDividerItemDecoration setVerticalPadding(float verticalPadding) {
        this.verticalPadding = verticalPadding;
        return this;
    }

    public PopupDividerItemDecoration setColor(int color) {
        mPaint.setColor(color);
        return this;
    }

    //设置分割线高度
    public PopupDividerItemDecoration setDividerHeight(float height) {
        this.mDividerHeight = height;
        return this;
    }

//    //在这里就已经把宽度的偏移给做好了
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//        //第一个ItemView不需要在上面绘制分割线
//        if (parent.getChildAdapterPosition(view) != 0) {
//            outRect.top = (int) mDividerHeight;//指相对itemView顶部的偏移量
//        }
//    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (!needHorizontalDivider && !needVerticalDivider) {
            return;
        }
        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount = 0;
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (childCount <= spanCount || childCount == 1) {
            return;
        }
        if (needHorizontalDivider) {
            float beforDrawTop = -1;
            for (int i = spanCount + 1; i < childCount; i++) {
                View view = parent.getChildAt(i);
                float dividerTop = view.getTop() - mDividerHeight;
                if (beforDrawTop == dividerTop) {
                    continue;
                }
                float dividerLeft = horizontalPadding;
                float dividerRight = parent.getWidth() - horizontalPadding;
                float dividerBottom = view.getTop();
                c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
                beforDrawTop = dividerTop;
            }
        }
        if (needVerticalDivider) {
            for (int i = 0; i < childCount - 1; i++) {
                if (i != 0 && i == spanCount - 1) {
                    continue;
                }
                View view = parent.getChildAt(i);
                float dividerLeft = view.getX() + view.getWidth();
                float dividerTop = view.getY() + verticalPadding + verticalMoreSize;
                float dividerRight = dividerLeft + mDividerHeight;
                float dividerBottom = view.getY() + view.getHeight() - verticalPadding;
                c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
            }
        }
    }
}