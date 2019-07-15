package com.example.floatwindow;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.floatwindow.R;
import com.example.floatwindow.imageutils.ImageLoaderUtil;

public class FloatWindow {
    private static final String TAG = "FloatWindow";
    private Activity mActivity;
    private WindowManager mWindowManager;
    private FrameLayout mFloatLayout;
    private View mVFloatWindow;
    private ImageView mCloseImg;
    private FrameLayout mDecorView;
    private FrameLayout.LayoutParams mVFloatLayoutParams;
    private FrameLayout.LayoutParams mFloatWindowParams;
    private FrameLayout.LayoutParams mCloseLayoutParams;
    private DisplayMetrics mDisplayMetrics;
    private float mStartX = 0;
    private float mStartY = 0;
    private float mTouchX = 0;
    private float mTouchY = 0;
    private float mMoveX = 0;
    private float mMoveY = 0;
    private int mStatusBarHeight = 0;
    private int mTitlePushupHeight = 0;

    public FloatWindow(Activity act) {
        mActivity = act;
        mDisplayMetrics = new DisplayMetrics();
        mWindowManager = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        View decor = act.getWindow().getDecorView();

        if (decor instanceof FrameLayout) {
            mDecorView = (FrameLayout) decor;
        }
        mTitlePushupHeight = 0;
    }

    public void showWindow(int flag) {
        Log.d(TAG, "hideWindow");
        if (mActivity == null || mDecorView == null) {
            return;
        }
        View floatLayout = findFloatLayout();
        if (floatLayout == null) {
            return;
        }
        floatLayout.setVisibility(flag);
    }

    private View findFloatWindow() {
        View v = null;
        if (mActivity == null || mDecorView == null) {
            return null;
        }

        int cnt = mDecorView.getChildCount();
        for (int i = 0; i < cnt; i++) {
            v = mDecorView.getChildAt(i);
            if ("floatwindow".equals(v.getTag(R.id.viewtype))) {
                return v;
            }
        }
        return null;
    }

    private View findFloatLayout() {
        View v = null;
        if (mActivity == null || mDecorView == null) {
            return null;
        }

        int cnt = mDecorView.getChildCount();
        for (int i = 0; i < cnt; i++) {
            v = mDecorView.getChildAt(i);
            if ("floatlayout".equals(v.getTag(R.id.viewtype))) {
                return v;
            }
        }
        return null;
    }

    public FrameLayout getFloatLayout() {
        return mFloatLayout;
    }

    public void showWindow(final String picurl, final String jumpurl) {
        Runnable action = new Runnable() {
            @Override
            public void run() {
                showWindowInUI(picurl, jumpurl);
            }
        };
        mActivity.runOnUiThread(action);
    }

    private void showWindowInUI(final String picurl, final String jumpurl) {
        Log.d(TAG, "showWindow");
        if (mActivity == null || mDecorView == null) {
            return;
        }
        Log.d(TAG, "showWindow picurl=" + picurl + " jumpurl=" + jumpurl);
        mFloatLayout = (FrameLayout) findFloatLayout();
        mVFloatWindow = findFloatWindow();
        int width = mActivity.getResources().getDimensionPixelSize(R.dimen.float_window_width);
        int height = mActivity.getResources().getDimensionPixelSize(R.dimen.float_window_height);

        int max_width = mDecorView.getMeasuredWidth();
        int max_height = mDecorView.getMeasuredHeight();
        if (max_width > 0 && max_width < mDisplayMetrics.widthPixels) {
            mDisplayMetrics.widthPixels = max_width;
        }
        if (max_height > 0 && max_height < mDisplayMetrics.heightPixels) {
            mDisplayMetrics.heightPixels = max_height;
        }
        mVFloatLayoutParams = new FrameLayout.LayoutParams(width, height);
        mVFloatLayoutParams.gravity = Gravity.NO_GRAVITY;
        mVFloatLayoutParams.leftMargin = mDisplayMetrics.widthPixels - width;
        mVFloatLayoutParams.topMargin = mDisplayMetrics.heightPixels - height;

        mFloatWindowParams = new FrameLayout.LayoutParams(width, height);
        mFloatWindowParams.topMargin = 8;
        mFloatWindowParams.rightMargin = 8;

        mCloseLayoutParams = new FrameLayout.LayoutParams((int) (width / 3.1), (int) (height / 3.1));
        mCloseLayoutParams.gravity = Gravity.TOP | Gravity.RIGHT;

        if (mFloatLayout == null || mVFloatWindow == null) {
            mFloatLayout = new FrameLayout(mActivity);
            mFloatLayout.setTag(R.id.viewtype, "floatlayout");

            mVFloatWindow = new ImageView(mActivity);
            mVFloatWindow.setTag(R.id.viewtype, "floatwindow");
            final ImageView img = ((ImageView) mVFloatWindow);
            img.setScaleType(ScaleType.FIT_CENTER);

            mCloseImg = new ImageView(mActivity);
            final ImageView closeImg = mCloseImg;
            closeImg.setImageResource(R.drawable.floatwindow_close);

            final FrameLayout layout = mDecorView;
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mFloatLayout.getParent() != null) {
                        return;
                    }
                    //mVFloatWindow和close按钮添加到mFloatLayout中
                    mFloatLayout.addView(img, mFloatWindowParams);
                    mFloatLayout.addView(closeImg, mCloseLayoutParams);
                    //再添加mFloatLayout到layout
                    layout.addView(mFloatLayout, mVFloatLayoutParams);
                    mFloatLayout.setVisibility(View.GONE);

                    ImageLoaderUtil.loadImage(mActivity, picurl, img);
                    Animation ani = AnimationUtils.loadAnimation(
                            mActivity, R.anim.floatwindow_right_in);
                    ani.setAnimationListener(new AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            mFloatLayout.setVisibility(View.VISIBLE);
                            closeImg.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Animation ani = AnimationUtils.loadAnimation(
                                    mActivity, R.anim.floatwindow_rotate);
                            ani.setAnimationListener(new AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    closeImg.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            mFloatLayout.startAnimation(ani);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                    });
                    mFloatLayout.startAnimation(ani);
                }
            });
        } else {
            mFloatLayout.setLayoutParams(mVFloatLayoutParams);
            ImageView img = ((ImageView) mVFloatWindow);
            img.setScaleType(ScaleType.FIT_CENTER);
            mCloseImg.setVisibility(View.VISIBLE);
            ImageLoaderUtil.loadImage(mActivity, picurl, img);
        }
        mVFloatWindow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //跳转如何处理自定义
            }
        });
        mCloseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatLayout != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mFloatLayout.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
        mVFloatWindow.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                float x = event.getRawX();
                float y = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchX = x;
                        mTouchY = y;
                        mStartX = x;
                        mStartY = y;
                        mMoveX = 0;
                        mMoveY = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mMoveX = x - mTouchX;
                        mMoveY = y - mTouchY;
                        mTouchX = x;
                        mTouchY = y;
                        updateViewPosition(mMoveX, mMoveY);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(mTouchX - mStartX) < 10
                                && Math.abs(mTouchY - mStartY) < 10) {
                            if (mVFloatWindow != null && mVFloatWindow.isEnabled()) {
                                mVFloatWindow.performClick();
                            }
                        } else {
                            forceUpdateViewPosition();
                        }
                        break;
                }
                return true;
            }
        });
    }


    private void forceUpdateViewPosition() {
        View view = mFloatLayout;
        FrameLayout.LayoutParams flParams = mVFloatLayoutParams;
        if (flParams != null && view != null) {
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            flParams.leftMargin = mDisplayMetrics.widthPixels - width;
            if (flParams.topMargin < (mTitlePushupHeight + mStatusBarHeight)) {
                flParams.topMargin = (mTitlePushupHeight + mStatusBarHeight);
            } else if (flParams.topMargin + height > mDisplayMetrics.heightPixels) {
                flParams.topMargin = mDisplayMetrics.heightPixels - height;
            }
            view.setLayoutParams(flParams);
        }
    }

    private void updateViewPosition(float moveX, float moveY) {
        View view = mFloatLayout;
        FrameLayout.LayoutParams flParams = mVFloatLayoutParams;
        if (flParams != null && view != null) {
            flParams.leftMargin += (int) (moveX);
            flParams.topMargin += (int) (moveY);

            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();

            if (flParams.leftMargin < 0) {
                flParams.leftMargin = 0;
            } else if (flParams.leftMargin + width > mDisplayMetrics.widthPixels) {
                flParams.leftMargin = mDisplayMetrics.widthPixels - width;
            }

            if (flParams.topMargin < (mTitlePushupHeight + mStatusBarHeight)) {
                flParams.topMargin = (mTitlePushupHeight + mStatusBarHeight);
            } else if (flParams.topMargin + height > mDisplayMetrics.heightPixels) {
                flParams.topMargin = mDisplayMetrics.heightPixels - height;
            }
            Log.d(TAG, "flParams.leftMargin=" + flParams.leftMargin + ",flParams.topMargin=" + flParams.topMargin);
            view.setLayoutParams(flParams);
        }
    }

}
