### KSnackbar
 
the snack like iOS notification, support:

* cover status bar,

* independent of Activities

### Screenshot

![]()

#### Usage  

```
// notice: must use applicationContext
SnackbarUtil.setShort(getApplicationContext(), your message)
                .setMessageColor(0xffffffff) // @ColorInt
                .setAlpha(0.8f) // 0.0f ~ 1.0f
                .setBackgroundColor(0xffffc026) // @ColorInt
                .leftAndRightDrawable(your drawable, null) // resId or drawable
                .setHeightOverStatus(true) // cover status bar
                .setGrivaty(Gravity.TOP).show();
```

```
// 6.0 need request permission
    <uses-permission android:name="android.permission.SYSTEM_OVERLAY_WINDOW" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
```


Thanks:  
 
* [HuanHaiLiuXin](https://github.com/HuanHaiLiuXin)  
* [AndreiD](https://github.com/AndreiD)