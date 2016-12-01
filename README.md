### KSnackbar
 
the snack like iOS notification, support:

* cover status bar,

* independent of Activities

### Screenshot

![](https://github.com/kHRYSTAL/KSnackbar/blob/master/screenshot/screenshot.gif)

#### Usage  

```           
 SnackbarUtil.getInstance(this)
                .setMessage("Success~") // your message
                .setMessageColor(0xffffffff) // @ColorInt
                .setBackgroundAlpha(0.9f) //0.0f ~ 1.0f
                .setBackgroundColor(0xffffc026) // @ColorInt
                .setLeftAndRightDrawable(R.drawable.img_prompt_check, null) // resId or drawable
                .setCoverStatusbar(true) // cover status bar
                .setGravity(Gravity.TOP).show();                
```

```
// 6.0 need request permission
    <uses-permission android:name="android.permission.SYSTEM_OVERLAY_WINDOW" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
```


Thanks:  
 
* [HuanHaiLiuXin](https://github.com/HuanHaiLiuXin)  
* [AndreiD](https://github.com/AndreiD)