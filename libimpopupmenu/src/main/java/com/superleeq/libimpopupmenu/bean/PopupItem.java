package com.superleeq.libimpopupmenu.bean;
/**
 * @author superleeq@foxmail.com
 */
public class PopupItem {
    private int itemIconResId;
    private String itemText;

    public PopupItem() {
    }

    public PopupItem(int itemIconResId, String itemText) {
        this.itemIconResId = itemIconResId;
        this.itemText = itemText;
    }

    public int getItemIconResId() {
        return itemIconResId;
    }

    public void setItemIconResId(int itemIconResId) {
        this.itemIconResId = itemIconResId;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}
