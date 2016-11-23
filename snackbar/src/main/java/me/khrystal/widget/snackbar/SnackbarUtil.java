package me.khrystal.widget.snackbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/11/23
 * update time:
 * email: 723526676@qq.com
 */

public class SnackbarUtil {

    private static final int color_info = 0XFF2094F3;
    private static final int color_confirm = 0XFF4CB04E;
    private static final int color_warning = 0XFFFEC005;
    private static final int color_danger = 0XFFF44336;

    private SnackbarWrapper mWrapper = null;

    /**
     * this context must be ApplicationContext
     */
    private static Context mContext;

    private SnackbarUtil(@NonNull SnackbarWrapper wrapper){
        this.mWrapper = wrapper;
    }

    public SnackbarWrapper getWrapper() {
        return mWrapper;
    }

    public static SnackbarUtil setShort(Context context, String message) {
        mContext = context;
        return new SnackbarUtil(SnackbarWrapper.make(context, message, Snackbar.LENGTH_SHORT))
                .setBackgroundColor(0XFF323232);
    }

    public static SnackbarUtil setLong(Context context, String message) {
        mContext = context;
        return new SnackbarUtil(SnackbarWrapper.make(context, message, Snackbar.LENGTH_LONG))
                .setBackgroundColor(0XFF323232);
    }

    public static SnackbarUtil setIndefinite(Context context, String message){
        mContext = context;
        return new SnackbarUtil(SnackbarWrapper.make(context, message, Snackbar.LENGTH_INDEFINITE))
                .setBackgroundColor(0XFF323232);
    }

    public SnackbarUtil setCustom(Context context, String message, int duration) {
        mContext = context;
        return new SnackbarUtil(SnackbarWrapper.make(context, message, Snackbar.LENGTH_SHORT).setDuration(duration))
                .setBackgroundColor(0XFF323232);
    }

    public SnackbarUtil info() {
        mWrapper.setBackgroundColor(color_info);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil confirm() {
        mWrapper.setBackgroundColor(color_confirm);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil warning() {
        mWrapper.setBackgroundColor(color_warning);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil danger() {
        mWrapper.setBackgroundColor(color_danger);
        return new SnackbarUtil(mWrapper);
    }

    /**
     * set Snackbar background
     *
     * @param backgroundColor
     * @return
     */
    public SnackbarUtil setBackgroundColor(@ColorInt int backgroundColor) {
        mWrapper.setBackgroundColor(backgroundColor);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setMessageColor(@ColorInt int messageColor) {
        mWrapper.setTextColor(messageColor);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setActionColor(@ColorInt int actionColor) {
        mWrapper.setActionColor(actionColor);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setColors(@ColorInt int backgroundColor, @ColorInt int messageColor,
                                   @ColorInt int actionColor) {
        mWrapper.setBackgroundColor(backgroundColor);
        mWrapper.setTextColor(messageColor);
        mWrapper.setActionColor(actionColor);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setAlpha(float alpha) {
        alpha = alpha >= 1.0f ? 1.0f : (alpha <= 0.0f ? 0.0f : alpha);
        mWrapper.setAlpha(alpha);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setGrivaty(int grivaty) {
        mWrapper.setGrivaty(grivaty);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setAction(@StringRes int resId, View.OnClickListener listener){
        return setAction(mContext.getResources().getText(resId), listener);
    }

    public SnackbarUtil setAction(CharSequence text, View.OnClickListener listener){
        mWrapper.setAction(text,listener);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setCallback(Snackbar.Callback callback) {
        mWrapper.setCallback(callback);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil leftAndRightDrawable(@Nullable @DrawableRes Integer leftDrawable, @Nullable @DrawableRes Integer rightDrawable){
        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        if(leftDrawable != null){
            try {
                drawableLeft = mContext.getResources().getDrawable(leftDrawable.intValue());
            }catch (Exception e){
                Log.e("SnackbarUtil", "leftDrawable");
                e.printStackTrace();
            }
        }
        if(rightDrawable != null){
            try {
                drawableRight = mContext.getResources().getDrawable(rightDrawable.intValue());
            }catch (Exception e){
                Log.e("SnackbarUtil", "rightDrawable");
                e.printStackTrace();
            }
        }
        return leftAndRightDrawable(drawableLeft,drawableRight);
    }

    public SnackbarUtil leftAndRightDrawable(@Nullable Drawable leftDrawable, @Nullable Drawable rightDrawable){
        mWrapper.setLeftAndRightDrawable(leftDrawable, rightDrawable);
        return new SnackbarUtil(mWrapper);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackbarUtil setMessageCenter(boolean isCenter) {
        mWrapper.setMessageCenter(isCenter);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil addView(int layoutId, int index) {
        View addView = LayoutInflater.from(mContext).inflate(layoutId,null);
        return addView(addView,index);
    }

    public SnackbarUtil addView(View addView, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数
        params.gravity= Gravity.CENTER_VERTICAL;
        addView.setLayoutParams(params);
        mWrapper.addChildView(addView,index);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil margins(int margin){
        return margins(margin,margin,margin,margin);
    }

    /**
     * 调用margins后再调用 gravityFrameLayout,则margins无效.
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public SnackbarUtil margins(int left, int top, int right, int bottom){
        mWrapper.setMargins(left, top, right, bottom);
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setRadius(float radius){
        mWrapper.setRadius(radius);
        return new SnackbarUtil(mWrapper);
    }

    public void show(){
        if(mWrapper != null){
            mWrapper.show();
        }else {
            throw new NullPointerException("SnackbarWrapper is null!");
        }
    }

    public SnackbarUtil setHeightOverStatus(boolean overStatus, float titlebarHeight) {
        if (overStatus) {
            int snackbarHeight = ScreenUtil.getStatusHeight(mContext) + ScreenUtil.dp2px(mContext, titlebarHeight);
            mWrapper.setOverStatusHeight(overStatus, snackbarHeight);
        }
        return new SnackbarUtil(mWrapper);
    }

    public SnackbarUtil setHeightOverStatus(boolean overStatus) {
        if (overStatus) {
            int snackbarHeight = ScreenUtil.getStatusHeight(mContext) +
                    ScreenUtil.getActionBarHeight(mContext);
            mWrapper.setOverStatusHeight(overStatus, snackbarHeight);
        }
        return new SnackbarUtil(mWrapper);
    }

}
