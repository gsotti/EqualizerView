package eu.gsottbauer.equalizerview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by claucookie on 01/02/15.
 * Modified by gsotti 06/01/2016
 */
public class EqualizerView extends LinearLayout {


    public static final int DEFAULT_DURATION = 3000;
    public static final int DEFAULT_BAR_COUNT = 20;
    public static final int DEFAULT_ANIMATION_COUNT = 30;
    public static final int DEFAULT_COLOR = Color.DKGRAY;
    public static final int DEFAULT_WIDTH = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int DEFAULT_MARGIN_LEFT = 1;
    public static final int DEFAULT_MARGIN_RIGHT = 1;

    ArrayList<View> mBars = new ArrayList<>();
    ArrayList<Animator> mAnimators = new ArrayList<>();

    AnimatorSet mPlayingSet;
    AnimatorSet mStopSet;
    Boolean isAnimating = false;

    int mForegroundColor = DEFAULT_COLOR;
    int mAnimationDuration = DEFAULT_DURATION;
    int mBarCount = DEFAULT_BAR_COUNT;
    int mBarWidth = DEFAULT_WIDTH;
    int mMarginLeft = DEFAULT_MARGIN_LEFT;
    int mMarginRight = DEFAULT_MARGIN_RIGHT;

    public EqualizerView(Context context) {
        super(context);
        initView();
    }

    public EqualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        initView();
    }

    public EqualizerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getAttrs(context, attrs);
        initView();
    }

    private void getAttrs(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EqualizerView, 0, 0);

        try {

            mForegroundColor = a.getInt(R.styleable.EqualizerView_barColor, DEFAULT_COLOR);
            mAnimationDuration = a.getInt(R.styleable.EqualizerView_animationDuration, DEFAULT_DURATION);
            mBarCount = a.getInt(R.styleable.EqualizerView_barCount, DEFAULT_BAR_COUNT);
            mBarWidth = (int)a.getDimension(R.styleable.EqualizerView_barWidth,DEFAULT_WIDTH);
            mMarginLeft = (int)a.getDimension(R.styleable.EqualizerView_marginLeft,DEFAULT_MARGIN_LEFT);
            mMarginRight = (int)a.getDimension(R.styleable.EqualizerView_marginRight,DEFAULT_MARGIN_RIGHT);

        } finally {
            a.recycle();
        }
    }

    private void initView() {

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        for (int i = 0; i < mBarCount; i++) {

            View view = new View(getContext());

            LinearLayout.LayoutParams params = new LayoutParams(mBarWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = (mBarWidth <= -1) ? 1 : 0;
            params.setMargins(mMarginLeft, 0, mMarginRight, 0);
            view.setLayoutParams(params);

            view.setBackgroundColor(mForegroundColor);
            addView(view);

            setPivots(view);
            mBars.add(view);
        }
    }

    private void resetView() {
        removeAllViews();
        mBars.clear();
        mAnimators.clear();
        mPlayingSet = null;
        mStopSet = null;
    }


    private void setPivots(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (view.getHeight() > 0) {
                    view.setPivotY(view.getHeight());
                    if (Build.VERSION.SDK_INT >= 16) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    public void animateBars() {

        isAnimating = true;

        if (mPlayingSet == null) {

            for (int i = 0; i < mBars.size(); i++) {

                Random rand = new Random();
                float[] values = new float[DEFAULT_ANIMATION_COUNT];

                for (int j = 0; j < DEFAULT_ANIMATION_COUNT; j++) {
                    values[j] = rand.nextFloat();
                }

                ObjectAnimator scaleYbar = ObjectAnimator.ofFloat(mBars.get(i), "scaleY", values);
                scaleYbar.setRepeatCount(ValueAnimator.INFINITE);
                scaleYbar.setRepeatMode(ObjectAnimator.REVERSE);
                mAnimators.add(scaleYbar);
            }

            mPlayingSet = new AnimatorSet();
            mPlayingSet.playTogether(mAnimators);
            mPlayingSet.setDuration(mAnimationDuration);
            mPlayingSet.setInterpolator(new LinearInterpolator());
            mPlayingSet.start();

        } else if (Build.VERSION.SDK_INT < 19) {
            if (!mPlayingSet.isStarted()) {
                mPlayingSet.start();
            }
        } else {
            if (mPlayingSet.isPaused()) {
                mPlayingSet.resume();
            }
        }

    }

    public void stopBars() {
        isAnimating = false;

        if (mPlayingSet != null && mPlayingSet.isRunning() && mPlayingSet.isStarted()) {
            if (Build.VERSION.SDK_INT < 19) {
                mPlayingSet.end();
            } else {
                mPlayingSet.pause();
            }
        }

        if (mStopSet == null) {
            mAnimators.clear();

            for (int i = 0; i < mBars.size(); i++) {
                mAnimators.add(ObjectAnimator.ofFloat(mBars.get(i), "scaleY", 0.1f));
            }

            mStopSet = new AnimatorSet();
            mStopSet.playTogether(mAnimators);
            mStopSet.setDuration(200);
            mStopSet.start();
        } else if (!mStopSet.isStarted()) {
            mStopSet.start();
        }
    }

    /**
     * setBarColor
     *
     * @param mForegroundColor foreground color as hex string
     */
    public void setBarColor(String mForegroundColor) {
        this.mForegroundColor = Color.parseColor(mForegroundColor);
        resetView();
        initView();
    }

    /**
     * setBarColor
     *
     * @param mForegroundColor foreground color as integer
     */
    public void setBarColor(int mForegroundColor) {
        this.mForegroundColor = mForegroundColor;
        resetView();
        initView();
    }

    /**
     * setBarCount
     *
     * @param mBarCount bar count
     */
    public void setBarCount(int mBarCount) {
        this.mBarCount = mBarCount;
        resetView();
        initView();
    }

    /**
     * setAnimationDuration
     *
     * @param mAnimationDuration duration in milliseconds
     */
    public void setAnimationDuration(int mAnimationDuration) {
        this.mAnimationDuration = mAnimationDuration;
        resetView();
        initView();
    }

    /**
     * setBarWidth
     *
     * @param mBarWidth bar width in pixel
     */
    public void setBarWidth(int mBarWidth) {
        this.mBarWidth = mBarWidth;
        resetView();
        initView();
    }

    /**
     * setMarginRight
     *
     * @param mMarginRight margin right in pixel
     */
    public void setMarginRight(int mMarginRight) {
        this.mMarginRight = mMarginRight;
        resetView();
        initView();
    }

    /**
     * setMarginLeft
     *
     * @param mMarginLeft margin left in pixel
     */
    public void setMarginLeft(int mMarginLeft) {
        this.mMarginLeft = mMarginLeft;
        resetView();
        initView();
    }

    public Boolean isAnimating() {
        return isAnimating;
    }



}