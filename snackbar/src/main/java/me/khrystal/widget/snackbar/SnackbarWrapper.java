package me.khrystal.widget.snackbar;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/11/23
 * update time:
 * email: 723526676@qq.com
 */

class SnackbarWrapper {

    private final int duration;
    private final WindowManager windowManager;
    private final Context appplicationContext;
    @Nullable
    private Snackbar.Callback externalCallback;
    @Nullable
    private Action action;
    private Snackbar mSnackbar;

    @ColorInt
    private int mBackgroundColor = -1;

    @ColorInt
    private int mTextColor = -1;

    @ColorInt
    private int mActionColor = -1;

    @Snackbar.Duration
    private int mDuration = 1;
    private boolean changeDuration;
    private CharSequence text;

    /**
     * 0.0f ~ 1.0f
     */
    private float mAlpha = 1.0f;

    /**
     * 显示位置
     */
    private int mGravity = -1;

    private Drawable mLeftDrawable, mRightDrawable;

    private boolean isMessageCenter;
    private SparseArray<View> mViewArray;
    // margin
    private int l, t, r, b;
    private float radius;
    private int snackbarHeight;
    private boolean overStatus;
    private float mTextSize;

    @NonNull
    public static SnackbarWrapper make(@NonNull Context applicationContext, @NonNull CharSequence text, @Snackbar.Duration int duration) {
        return new SnackbarWrapper(applicationContext, text, duration);
    }

    private SnackbarWrapper(@NonNull final Context appplicationContext, @NonNull CharSequence text, @Snackbar.Duration int duration) {
        this.appplicationContext = appplicationContext;
        this.windowManager = (WindowManager) appplicationContext.getSystemService(Context.WINDOW_SERVICE);
        this.text = text;
        this.duration = duration;
    }

    public Context getContext() {
        return appplicationContext;
    }

    public void setMessage(CharSequence text){
        this.text = text;

    }

    public void show() {
        WindowManager.LayoutParams layoutParams = createDefaultLayoutParams(WindowManager.LayoutParams.TYPE_TOAST, null);
        windowManager.addView(new FrameLayout(appplicationContext) {
            @Override
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                onRootViewAvailable(this);
            }

        }, layoutParams);
    }

    private void onRootViewAvailable(final FrameLayout rootView) {
        final FrameLayout snackbarContainer = new FrameLayout(new ContextThemeWrapper(appplicationContext, R.style.Theme_SnackbarWrapper)) {
            @Override
            public void onAttachedToWindow() {
                super.onAttachedToWindow();
                onSnackbarContainerAttached(rootView, this);
            }
        };
        windowManager.addView(snackbarContainer, createDefaultLayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR, rootView.getWindowToken()));
    }

    private void onSnackbarContainerAttached(final View rootView, final FrameLayout snackbarContainer) {
        mSnackbar = Snackbar.make(snackbarContainer, text, duration);
        TextView messageTv = (TextView) mSnackbar.getView().findViewById(R.id.snackbar_text);
        Button actionBtn = (Button) mSnackbar.getView().findViewById(R.id.snackbar_action);
        if (!TextUtils.isEmpty(text))
            messageTv.setText(text);
        if (changeDuration)
            mSnackbar.setDuration(mDuration);
        if (mBackgroundColor != -1)
            mSnackbar.getView().setBackgroundColor(mBackgroundColor);
        if (mTextColor != -1)
            messageTv.setTextColor(mTextColor);
        if (mTextSize != 0.0f)
            messageTv.setTextSize(mTextSize);
        if (mActionColor != -1)
            actionBtn.setTextColor(mActionColor);
        if (mAlpha != 1.0f)
            mSnackbar.getView().setAlpha(mAlpha);
        if (snackbarHeight != 0 && overStatus) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    mSnackbar.getView().getLayoutParams().width, snackbarHeight);
            mSnackbar.getView().setLayoutParams(params);
        }
        if (mGravity != -1) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mSnackbar.getView().getLayoutParams().width,
                    mSnackbar.getView().getLayoutParams().height);
            params.gravity = mGravity;
            mSnackbar.getView().setLayoutParams(params);
        }
        if (mLeftDrawable != null || mRightDrawable != null) {

            LinearLayout.LayoutParams paramsMessage = (LinearLayout.LayoutParams) messageTv.getLayoutParams();
            paramsMessage = new LinearLayout.LayoutParams(paramsMessage.width, paramsMessage.height, 0.0f);
            messageTv.setLayoutParams(paramsMessage);
            messageTv.setCompoundDrawablePadding(ScreenUtil.dp2px(mSnackbar.getView().getContext(), 8));
            int textSize = (int) messageTv.getTextSize();

            if(mLeftDrawable != null){
                mLeftDrawable.setBounds(0, 0, textSize + 4, textSize + 4);
            }
            if(mRightDrawable!=null){
                mRightDrawable.setBounds(0, 0, textSize + 4, textSize + 4);
            }
            messageTv.setCompoundDrawables(mLeftDrawable, null, mRightDrawable, null);
            LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
            ((Snackbar.SnackbarLayout) mSnackbar.getView()).addView(new Space(mSnackbar.getView().getContext()),1,paramsSpace);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) messageTv.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
        }


        if (l != 0 || t != 0 || r != 0 || b != 0) {
            ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
            ((ViewGroup.MarginLayoutParams) params).setMargins(l, t, r, b);
            mSnackbar.getView().setLayoutParams(params);
        }

        if (isMessageCenter) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                messageTv = (TextView) mSnackbar.getView().findViewById(R.id.snackbar_text);
                messageTv.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                messageTv.setGravity(Gravity.CENTER_VERTICAL| Gravity.RIGHT);
            }
        }

        if (radius != 0.0f) {
            GradientDrawable background = getRadiusDrawable(mSnackbar.getView().getBackground());
            if(background != null){
                radius = radius<=0 ? 12 : radius;
                background.setCornerRadius(radius);
                mSnackbar.getView().setBackgroundDrawable(background);
            }
        }

        if (mViewArray != null) {
            for (int i = 0; i < mViewArray.size(); i++) {
                View addView = mViewArray.get(i);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数
                //设置新建View在Snackbar内垂直居中显示
                params.gravity= Gravity.CENTER_VERTICAL;
                addView.setLayoutParams(params);
                ((Snackbar.SnackbarLayout) mSnackbar.getView()).addView(addView, i);
            }
        }




        mSnackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar Snackbar, int event) {
                super.onDismissed(Snackbar, event);
                // Clean up (NOTE! This callback can be called multiple times)
                if (snackbarContainer.getParent() != null && rootView.getParent() != null) {
                    windowManager.removeView(snackbarContainer);
                    windowManager.removeView(rootView);
                }
                if (externalCallback != null) {
                    externalCallback.onDismissed(Snackbar, event);
                }
            }

            @Override
            public void onShown(Snackbar Snackbar) {
                super.onShown(Snackbar);
                if (externalCallback != null) {
                    externalCallback.onShown(Snackbar);
                }
            }
        });
        if (action != null) {
            mSnackbar.setAction(action.text, action.listener);
        }
        mSnackbar.show();
    }

    private WindowManager.LayoutParams createDefaultLayoutParams(int type, @Nullable IBinder windowToken) {

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = GravityCompat.getAbsoluteGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, ViewCompat.LAYOUT_DIRECTION_LTR);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | (overStatus ?
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN : 0x00000000);
        layoutParams.type = type;
        layoutParams.token = windowToken;
        return layoutParams;
    }

    @NonNull
    public SnackbarWrapper setCallback(@Nullable Snackbar.Callback callback) {
        this.externalCallback = callback;
        return this;
    }

    @NonNull
    public SnackbarWrapper setAction(CharSequence text, final View.OnClickListener listener) {
        action = new Action(text, listener);
        return this;
    }

    private static class Action {
        private final CharSequence text;
        private final View.OnClickListener listener;

        public Action(CharSequence text, View.OnClickListener listener) {
            this.text = text;
            this.listener = listener;
        }
    }

    private GradientDrawable getRadiusDrawable(Drawable backgroundOri){
        GradientDrawable background = null;
        if(backgroundOri instanceof GradientDrawable){
            background = (GradientDrawable) backgroundOri;
        }else if(backgroundOri instanceof ColorDrawable){
            int backgroundColor = ((ColorDrawable)backgroundOri).getColor();
            background = new GradientDrawable();
            background.setColor(backgroundColor);
        }else {
        }
        return background;
    }

// ====== SnackBarUtil method start ======

    public void setBackgroundColor(@ColorInt int backgroundColor){
        mBackgroundColor = backgroundColor;
    }

    public void setTextColor(@ColorInt int textColor) {
        mTextColor = textColor;
    }

    public void setActionColor(@ColorInt int actionColor) {
        mActionColor = actionColor;
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    public SnackbarWrapper setDuration(int duration) {
        changeDuration = true;
        mDuration = duration;
        return this;
    }

    public void setGrivaty(int grivaty) {
        mGravity = grivaty;
    }

    public void setLeftAndRightDrawable(Drawable leftDrawable, Drawable rightDrawable) {
        mLeftDrawable = leftDrawable;
        mRightDrawable = rightDrawable;
    }

    public void setMessageCenter(boolean isCenter) {
        isMessageCenter = isCenter;
    }

    public void addChildView(View childView, int index) {
        if (mViewArray == null)
            mViewArray = new SparseArray<>();
        mViewArray.put(index, childView);
    }

    public void setMargins(int ... margins) {
        l = margins[0];
        t = margins[1];
        r = margins[2];
        b = margins[3];
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setOverStatusHeight(boolean overStatus, int statusHeight) {
        this.overStatus = overStatus;
        this.snackbarHeight = statusHeight;
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
    }
}
