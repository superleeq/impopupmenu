package com.superleeq.impopupmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.superleeq.libimpopupmenu.ImPopupMenu;
import com.superleeq.libimpopupmenu.bean.PopupItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private CheckBox vCb, hCb;
    private String[] items = new String[]{"复制", "转发", "收藏", "删除", "多选", "引用", "提醒", "搜一搜"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testShow();
    }

    private void testShow() {
        findViewById(R.id.lefttop_tv).setOnClickListener(this);
        findViewById(R.id.righttop_tv).setOnClickListener(this);
        findViewById(R.id.left_tv).setOnClickListener(this);
        findViewById(R.id.right_tv).setOnClickListener(this);
        findViewById(R.id.center_tv).setOnClickListener(this);
        findViewById(R.id.bottomleft_tv).setOnClickListener(this);
        findViewById(R.id.bottomright_tv).setOnClickListener(this);
        radioGroup = findViewById(R.id.style_radiogroup);
        vCb = findViewById(R.id.vertical_divider_cb);
        hCb = findViewById(R.id.horizontal_divider_cb);
        radioGroup = findViewById(R.id.style_radiogroup);
    }

    @Override
    public void onClick(View v) {
        doShow(v);
    }

    private void doShow(View anchorView) {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.normalstyle_radiobtn) {
            ImPopupMenu imPopupMenu = new ImPopupMenu(this);
            List<PopupItem> itemList = new ArrayList<>();
            for (int i = 0; i < items.length; i++) {
                PopupItem item = new PopupItem(R.mipmap.ic_test, items[i]);
                itemList.add(item);
            }
            initAttrs(imPopupMenu);
            imPopupMenu.show(anchorView, itemList, new ImPopupMenu.OnImPopupMenuItemClickListener() {
                @Override
                public void onImPopupMenuItemClick(int position, String clickItemText) {
                    Toast.makeText(anchorView.getContext(), "p:" + position + ",text:" + clickItemText, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (checkedId == R.id.simplestyle_radiobtn) {
            ImPopupMenu imPopupMenu = new ImPopupMenu(this);
            List<String> simpleItemList = new ArrayList<>();
            for (int i = 0; i < items.length; i++) {
                simpleItemList.add(items[i]);
            }
            initAttrs(imPopupMenu);
            imPopupMenu.showWithSimpleStyle(anchorView, simpleItemList, new ImPopupMenu.OnImPopupMenuItemClickListener() {
                @Override
                public void onImPopupMenuItemClick(int position, String clickItemText) {
                    Toast.makeText(anchorView.getContext(), "p:" + position + ",text:" + clickItemText, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initAttrs(ImPopupMenu imPopupMenu) {
        //背景色
        imPopupMenu.setBackgroundColor(Color.parseColor("#515151"));
        //分割线颜色
        imPopupMenu.setDividerColor(Color.parseColor("#80B7B7B7"));
        //分割线尺寸
        imPopupMenu.setDividerHeight(0.5f);
        //是否需要垂直方向分割线
        imPopupMenu.setNeedVerticalDivider(vCb.isChecked());
        //是否需要水平方向分割线，默认需要
        imPopupMenu.setNeedHorizontalDivider(hCb.isChecked());
        //水平分割线左右间距
        imPopupMenu.setHorizontalDividerPadding(15);
        //垂直分割线左右间距
        imPopupMenu.setVerticalDividerPadding(10);
        //item按下效果
        imPopupMenu.setItemBackroundPressColor(Color.BLACK);
        //图标尺寸
        imPopupMenu.setIconSize(20);
        //每行item个数
        imPopupMenu.setSpanCount(5);
        //文本颜色
        imPopupMenu.setTextColor(Color.WHITE);
        //文本字体大小
        imPopupMenu.setTextSizeSp(14);
        //每个item间距
        imPopupMenu.setItemPadding(10, 15, 10, 15);
    }


}