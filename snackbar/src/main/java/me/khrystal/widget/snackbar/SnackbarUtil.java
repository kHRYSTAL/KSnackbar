package me.khrystal.widget.snackbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;


/**
 * SnackBar 工具类
 */
public class SnackbarUtil {

    // 提示信息类型
    public static final int TYPE_INFO = 0;
    public static final int TYPE_CONFIRM = 1;
    public static final int TYPE_WARNING = 2;
    public static final int TYPE_DANGER = 3;

    private static final int color_info = 0XFF2094F3;
    private static final int color_confirm = 0XFF4CB04E;
    private static final int color_warning = 0XFFFEC005;
    private static final int color_danger = 0XFFF44336;

    private SnackbarWrapper mWrapper = null;
    private static SnackbarUtil instance = null;
    /**
     * this context must be ApplicationContext
     */
    private Context mContext;

    public static SnackbarUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SnackbarUtil.class) {
                if (instance == null) {
                    instance = new SnackbarUtil(SnackbarWrapper.make(context.getApplicationContext(), "", Snackbar.LENGTH_SHORT));
                }
            }
        }
        return instance;
    }

    private SnackbarUtil(@NonNull SnackbarWrapper wrapper) {
        this.mWrapper = wrapper;
        mContext = mWrapper.getContext();
    }

    /**
     * 设置文案
     */
    public SnackbarUtil setMessage(CharSequence message) {
        mWrapper.setMessage(message);
        return instance;
    }

    /**
     * 设置SnackBar 显示类型
     *
     * @param showType SnackBarView.LENGTH_SHORT or SnackBarView.LENGTH_LONG or SnackBarView.LENGTH_INDEFINITE
     */
    public SnackbarUtil setShowType(int showType) {
        mWrapper.setDuration(showType);
        return instance;
    }

    /**
     * 设置 SnackBar 类型，显示不同的背景色
     */
    public SnackbarUtil setSnackBarType(int snackBarType) {
        switch (snackBarType) {
            case TYPE_INFO:
                mWrapper.setBackgroundColor(color_info);
                break;
            case TYPE_CONFIRM:
                mWrapper.setBackgroundColor(color_confirm);
                break;
            case TYPE_WARNING:
                mWrapper.setBackgroundColor(color_warning);
                break;
            case TYPE_DANGER:
                mWrapper.setBackgroundColor(color_danger);
                break;
        }
        return instance;
    }

    /**
     * 设置显示时长
     */
    public SnackbarUtil setDuration(@Snackbar.Duration int duration) {
        mWrapper.setDuration(duration);
        return instance;
    }

    /**
     * 设置背景颜色
     */
    public SnackbarUtil setBackgroundColor(@ColorInt int backgroundColor) {
        mWrapper.setBackgroundColor(backgroundColor);
        return instance;
    }

    /**
     * 设置文字颜色
     */
    public SnackbarUtil setMessageColor(@ColorInt int messageColor) {
        mWrapper.setTextColor(messageColor);
        return instance;
    }

    /**
     * 设置点击颜色
     */
    public SnackbarUtil setActionColor(@ColorInt int actionColor) {
        mWrapper.setActionColor(actionColor);
        return instance;
    }

    /**
     * 设置透明度
     */
    public SnackbarUtil setBackgroundAlpha(float alpha) {
        alpha = alpha >= 1.0f ? 1.0f : (alpha <= 0.0f ? 0.0f : alpha);
        mWrapper.setAlpha(alpha);
        return instance;
    }

    /**
     * 设置 Gravity  默认为top
     */
    public SnackbarUtil setGravity(int gravity) {
        mWrapper.setGrivaty(gravity);
        return instance;
    }

    /**
     * 支持右侧添加按钮
     *
     * @param text     按钮文案
     * @param listener 按钮点击事件
     */
    public SnackbarUtil setAction(CharSequence text, View.OnClickListener listener) {
        mWrapper.setAction(text, listener);
        return instance;
    }

    /**
     * 设置回调
     */
    public SnackbarUtil setCallback(Snackbar.Callback callback) {
        mWrapper.setCallback(callback);
        return instance;
    }

    /**
     * 设置文字左右图片
     */
    public SnackbarUtil setLeftAndRightDrawable(@Nullable @DrawableRes Integer leftDrawable, @Nullable @DrawableRes Integer rightDrawable) {
        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        if (leftDrawable != null) {
            try {
                drawableLeft = mContext.getResources().getDrawable(leftDrawable.intValue());
            } catch (Exception e) {
                Log.e("SnackbarUtil", "leftDrawable");
                e.printStackTrace();
            }
        }
        if (rightDrawable != null) {
            try {
                drawableRight = mContext.getResources().getDrawable(rightDrawable.intValue());
            } catch (Exception e) {
                Log.e("SnackbarUtil", "rightDrawable");
                e.printStackTrace();
            }
        }
        return setLeftAndRightDrawable(drawableLeft, drawableRight);
    }

    /**
     * 设置文字左右图片
     */
    public SnackbarUtil setLeftAndRightDrawable(@Nullable Drawable leftDrawable, @Nullable Drawable rightDrawable) {
        mWrapper.setLeftAndRightDrawable(leftDrawable, rightDrawable);
        return instance;
    }

    /**
     * 设置文字居中 APIversion > 17
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackbarUtil setMessageCenter(boolean isCenter) {
        mWrapper.setMessageCenter(isCenter);
        return instance;
    }

    /**
     * 添加自定义view
     */
    public SnackbarUtil addView(View addView, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数
        params.gravity = Gravity.CENTER_VERTICAL;
        addView.setLayoutParams(params);
        mWrapper.addChildView(addView, index);
        return instance;
    }

    /**
     * 设置上下左右边距
     */
    public SnackbarUtil setMargins(int left, int top, int right, int bottom) {
        mWrapper.setMargins(left, top, right, bottom);
        return instance;
    }

    /**
     * 设置四个角度值
     */
    public SnackbarUtil setRadius(float radius) {
        mWrapper.setRadius(radius);
        return instance;
    }

    /**
     * 设置是否覆盖状态栏
     *
     * @param titlebarHeight 工具栏高度 传递参数单位是px
     */
    public SnackbarUtil setCoverStatusbar(boolean overStatus, float titlebarHeight) {
        if (overStatus) {
            int snackbarHeight = ScreenUtil.getStatusHeight(mContext) + (int) titlebarHeight;
            mWrapper.setOverStatusHeight(overStatus, snackbarHeight);
        }
        return instance;
    }

    /**
     * 设置是否覆盖状态栏
     */
    public SnackbarUtil setCoverStatusbar(boolean overStatus) {
        if (overStatus) {
            int snackbarHeight = ScreenUtil.getStatusHeight(mContext) +
                    ScreenUtil.getActionBarHeight(mContext);
            mWrapper.setOverStatusHeight(overStatus, snackbarHeight);
        }
        return instance;
    }

    /**
     * 设置字体大小
     */
    public SnackbarUtil setMessageTextSize(float spSize) {
        mWrapper.setTextSize(spSize);
        return instance;
    }

    /**
     * 显示snack bar
     */
    public void show() {
        if (mWrapper != null) {
            mWrapper.show();
        } else {
            throw new NullPointerException("SnackBarWrapper is null!");
        }
    }
}
