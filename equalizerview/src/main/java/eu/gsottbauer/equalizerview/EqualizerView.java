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


    public final static int DEFAULT_DURATION = 3000;
    public final static int DEFAULT_BAR_COUNT = 30;
    public final static int DEFAULT_ANIMATION_COUNT = 30;
    public final static int DEFAULT_COLOR = Color.DKGRAY;

    ArrayList<View> mBars = new ArrayList<>();
    ArrayList<Animator> mAnimators = new ArrayList<>();

    AnimatorSet mPlayingSet;
    AnimatorSet mStopSet;
    Boolean isAnimating = false;

    int mForegroundColor = DEFAULT_COLOR;
    int mAnimationDuration = DEFAULT_DURATION;
    int mBarCount = DEFAULT_BAR_COUNT;
    int mAnimatorValueCount = DEFAULT_ANIMATION_COUNT;

    public EqualizerView(Context context) {
        super(context);
        initView();
    }

    public EqualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context, attrs);
        initView();
    }

    public EqualizerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttrs(context, attrs);
        initView();
    }

    private void setAttrs(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EqualizerView, 0, 0);

        try {

            mForegroundColor = a.getInt(R.styleable.EqualizerView_barColor, DEFAULT_COLOR);
            mAnimationDuration = a.getInt(R.styleable.EqualizerView_barAnimationDuration, DEFAULT_DURATION);
            mBarCount = a.getInt(R.styleable.EqualizerView_barCount, DEFAULT_BAR_COUNT);
            mAnimatorValueCount = a.getInt(R.styleable.EqualizerView_animationValueCount, DEFAULT_ANIMATION_COUNT);

        } finally {
            a.recycle();
        }
    }

    private void initView() {

        setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < mBarCount; i++) {

            View view = new View(getContext());

            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.setMargins(1, 1, 1, 1);
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

            for (int i = 0; i < mBarCount; i++) {

                Random rand = new Random();
                float[] values = new float[mAnimatorValueCount];

                for (int j = 0; j < mAnimatorValueCount; j++) {
                    values[j] = rand.nextFloat();
                }

                ObjectAnimator scaleYbar = ObjectAnimator.ofFloat(mBars.get(i), "scaleY", values);
                scaleYbar.setRepeatCount(ValueAnimator.INFINITE);
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

            for (int i = 0; i < mBarCount; i++) {
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


    public void setBarColor(String color) {
        mForegroundColor = Color.parseColor(color);
        resetView();
        initView();
    }


    public void setBarColor(int color) {
        mForegroundColor = color;
        resetView();
        initView();
    }


    public void setBarCount(int count) {
        mBarCount = count;
        resetView();
        initView();
    }


    public void setAnimationDuration(int duration) {
        this.mAnimationDuration = duration;
        resetView();
        initView();
    }

    public void setObjectAnimationValueCount(int count) {
        this.mAnimatorValueCount = count;
        resetView();
        initView();
    }


    public Boolean isAnimating() {
        return isAnimating;
    }
}