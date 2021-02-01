# impopupmenu
高仿微信聊天Android/Ios 长按popup控件

* 高仿微信UI（默认），并支持UI自定义</br>
* 默认从控件上方浮出，侧边、顶部、底部均仿微信优化处理</br>
* popup小箭头仿微信：在触发控件居中</br>
* 支持有图标、无图标style；支持横、竖向分割线；支持自定义每行数目等</br>

## Screenshots</br>
![demo.png](https://github.com/superleeq/loopview/blob/master/app/src/main/res/raw/demo.png)

## 添加依赖：</br>
Step 1.</br>
```javascript
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2.</br>
```javascript
dependencies {
	 implementation 'com.github.superleeq:impopupmenu:1.1.1'
}
```

## 使用：</br>
1，从控件上浮出并绑定事件
```javascript
                    ImPopupMenu imPopupMenu = new ImPopupMenu(this);
                    List<PopupItem> itemList = new ArrayList<>();
                    for (int i = 0; i < items.length; i++) {
                        PopupItem item = new PopupItem(R.mipmap.ic_test, items[i]);
                        itemList.add(item);
                    }
                    //initAttrs(imPopupMenu);
                    imPopupMenu.show(anchorView, itemList, new ImPopupMenu.OnImPopupMenuItemClickListener() {
                        @Override
                        public void onImPopupMenuItemClick(int position, String clickItemText) {
                            //处理点击事件
                            Toast.makeText(anchorView.getContext(), "p:" + position + ",text:" + clickItemText, Toast.LENGTH_SHORT).show();
                        }
                    });
```

2，自定义UI
```javascript
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
```

## 版本记录</br>
v1.1.1 高仿微信UI并支持丰富自定义</br>


## 反馈</br>
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流</br>
* 邮件(superleeq@foxmail.com)
* QQ: 446486198