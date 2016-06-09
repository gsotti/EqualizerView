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

    ArrayList<View> mBars = new ArrayList<>();
    ArrayList<Animator> mAnimators = new ArrayList<>();

    AnimatorSet playingSet;
    AnimatorSet stopSet;
    Boolean isAnimating = false;

    int foregroundColor = Color.WHITE;
    int duration = 3000;
    int barcount = 3;


    public EqualizerView(Context context) {
        super(context);
        initViews();
    }

    public EqualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context, attrs);
        initViews();
    }

    public EqualizerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttrs(context, attrs);
        initViews();
    }

    private void setAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EqualizerView,
                0, 0);

        try {

            foregroundColor = a.getInt(R.styleable.EqualizerView_barColor, Color.WHITE);
            duration = a.getInt(R.styleable.EqualizerView_barAnimationDuration, 3000);
            barcount = a.getInt(R.styleable.EqualizerView_barCount, 3);

        } finally {
            a.recycle();
        }
    }

    private void initViews() {

        setOrientation(LinearLayout.HORIZONTAL);
        removeAllViews();
        mBars.clear();

        for (int i = 0; i < barcount; i++) {

            View view = new View(getContext());

            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.setMargins(1, 1, 1, 1);
            view.setLayoutParams(params);

            view.setBackgroundColor(foregroundColor);
            addView(view);

            setPivots(view);
            mBars.add(view);
        }
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

        if (playingSet == null) {

            for (int i = 0; i < barcount; i++) {


                Random rand = new Random();
                ObjectAnimator scaleYbar = ObjectAnimator.ofFloat(mBars.get(i), "scaleY", rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(),
                                                                           rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(),
                                                                           rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(),
                                                                           rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());

                scaleYbar.setRepeatCount(ValueAnimator.INFINITE);

                mAnimators.add(scaleYbar);
            }

            playingSet = new AnimatorSet();
            playingSet.playTogether(mAnimators);
            playingSet.setDuration(duration);
            playingSet.setInterpolator(new LinearInterpolator());
            playingSet.start();

        } else if (Build.VERSION.SDK_INT < 19) {
            if (!playingSet.isStarted()) {
                playingSet.start();
            }
        } else {
            if (playingSet.isPaused()) {
                playingSet.resume();
            }
        }

    }

    public void stopBars() {
        isAnimating = false;
        if (playingSet != null && playingSet.isRunning() && playingSet.isStarted()) {
            if (Build.VERSION.SDK_INT < 19) {
                playingSet.end();
            } else {
                playingSet.pause();
            }
        }

        if (stopSet == null) {
            // Animate stopping bars

            mAnimators.clear();

            for (int i = 0; i < barcount; i++) {
                mAnimators.add(ObjectAnimator.ofFloat(mBars.get(i), "scaleY", 0.1f));
            }


            stopSet = new AnimatorSet();
            stopSet.playTogether(mAnimators);
            stopSet.setDuration(200);
            stopSet.start();
        } else if (!stopSet.isStarted()) {
            stopSet.start();
        }
    }


    public void setBarColor(String color) {
        foregroundColor = Color.parseColor(color);
        initViews();
    }


    public void setBarColor(int color) {
        foregroundColor = color;
        initViews();
    }

    public Boolean isAnimating() {
        return isAnimating;
    }

    public void setBarCount(int count)
    {
        barcount = count;
        initViews();
    }
}