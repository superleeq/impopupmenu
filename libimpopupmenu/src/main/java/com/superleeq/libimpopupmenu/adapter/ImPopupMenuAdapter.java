package com.superleeq.libimpopupmenu.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.superleeq.libimpopupmenu.ImPopupMenu;
import com.superleeq.libimpopupmenu.R;
import com.superleeq.libimpopupmenu.bean.PopupItem;
import com.superleeq.libimpopupmenu.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * @author superleeq@foxmail.com
 */
public class ImPopupMenuAdapter extends RecyclerView.Adapter<ImPopupMenuAdapter.VH> implements View.OnClickListener {

    private ImPopupMenu.OnImPopupMenuItemClickListener mOnImPopupMenuItemClickListener;
    private ImPopupMenu mImPopupMenu;
    private int mTextSizeSp;
    private int mTextColor;
    private int mIconSize;
    private int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;
    private List mItemList = new ArrayList<>();
    private int mItemPressColor;
    private int mSpanCount;

    public void setSpanCount(int spanCount) {
        this.mSpanCount = spanCount;
    }

    public void setItemPadding(int left, int top, int right, int bottom) {
        this.mPaddingLeft = left;
        this.mPaddingTop = top;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
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

    public void setOnImPopupMenuItemClickListener(ImPopupMenu.OnImPopupMenuItemClickListener mOnImPopupMenuItemClickListener) {
        this.mOnImPopupMenuItemClickListener = mOnImPopupMenuItemClickListener;
    }

    public void bindImPopupMenu(ImPopupMenu imPopupMenu) {
        this.mImPopupMenu = imPopupMenu;
    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_impopupmenu, null);
        root.setOnClickListener(this);
        return new VH(root);
    }

    public void setItemList(List mItemList) {
        this.mItemList = mItemList;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Object item = mItemList.get(position);
        if (item instanceof String) {
            holder.iconIv.setVisibility(View.GONE);
            holder.itemTv.setText(mItemList.get(position).toString());
        } else if (item instanceof PopupItem) {
            holder.iconIv.setImageResource(((PopupItem) item).getItemIconResId());
            holder.itemTv.setText(((PopupItem) item).getItemText());
        }
        //set backround
        if (mSpanCount > 0 && mItemPressColor != 0) {
            holder.itemView.setBackground(getItemBackgroundDrawable());
        }
        if (mPaddingLeft > 0 || mPaddingTop > 0 || mPaddingRight > 0 || mPaddingBottom > 0) {
            holder.panel.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        }
        if (mIconSize > 0) {
            int iconSize = ScreenUtil.dp2px(holder.itemView.getContext(), mIconSize);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(iconSize, iconSize);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            holder.iconIv.setLayoutParams(lp);
        }
        if (mTextColor != 0) {
            holder.itemTv.setTextColor(mTextColor);
        }
        if (mTextSizeSp != 0) {
            holder.itemTv.setTextSize(mTextSizeSp);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnImPopupMenuItemClickListener != null) {
            int position = (int) v.getTag();
            Object item = mItemList.get(position);
            String clickText = null;
            if (item instanceof PopupItem) {
                clickText = ((PopupItem) item).getItemText();
            } else if (item instanceof String) {
                clickText = item.toString();
            }
            mOnImPopupMenuItemClickListener.onImPopupMenuItemClick(position, clickText);
            //
            if (mImPopupMenu != null) {
                mImPopupMenu.dismiss();
            }
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        View panel;
        ImageView iconIv;
        TextView itemTv;

        public VH(@NonNull View itemView) {
            super(itemView);
            panel = itemView.findViewById(R.id.adapter_impopupmenu_panel);
            iconIv = itemView.findViewById(R.id.adapter_impopupmenu_iv);
            itemTv = itemView.findViewById(R.id.adapter_impopupmenu_tv);
        }
    }

    public void setItemPressColor(int pressColor) {
        this.mItemPressColor = pressColor;
    }

    private StateListDrawable getItemBackgroundDrawable() {
        StateListDrawable result = new StateListDrawable();
        GradientDrawable centerItemPressedDrawable = new GradientDrawable();
        centerItemPressedDrawable.setColor(mItemPressColor);
        GradientDrawable centerItemNormalDrawable = new GradientDrawable();
        centerItemNormalDrawable.setColor(Color.TRANSPARENT);
        result.addState(new int[]{android.R.attr.state_pressed}, centerItemPressedDrawable);
        result.addState(new int[]{}, centerItemNormalDrawable);
        return result;
    }
}
