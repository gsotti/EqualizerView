package eu.gsottbauer.equalizerviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import eu.gsottbauer.equalizerview.EqualizerView;

public class MainActivity extends AppCompatActivity {

    private EqualizerView mEqualizerView;
    private Button mPlayPauseButton;
    private SeekBar mBarCountSeekBar;
    private SeekBar mAnimatorCountSeekBar;
    private TextView mBarCountTextView;
    private TextView mValuesCountTextView;
    private SeekBar mAnimationDurationSeekBar;
    private TextView mAnimationDurationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEqualizerView = (EqualizerView) findViewById(R.id.equalizer);
        mPlayPauseButton = (Button) findViewById(R.id.play_button);
        mBarCountTextView = (TextView) findViewById(R.id.bar_count);
        mValuesCountTextView = (TextView) findViewById(R.id.values_count);
        mAnimationDurationTextView = (TextView) findViewById(R.id.animation_duration);

        mBarCountSeekBar = (SeekBar) findViewById(R.id.barsSeekBar);
        mAnimatorCountSeekBar = (SeekBar) findViewById(R.id.valuesSeekBar);
        mAnimationDurationSeekBar = (SeekBar) findViewById(R.id.animationDurationSeekBar);

        mPlayPauseButton.setOnClickListener(mPlayButtonOnClickListener);
        mBarCountSeekBar.setOnSeekBarChangeListener(mBarCountSeekBarChangeListener);
        mAnimatorCountSeekBar.setOnSeekBarChangeListener(mValuesCountSeekBarChangeListener);
        mAnimationDurationSeekBar.setOnSeekBarChangeListener(mAnimationDurationSeekBarChangeListener);

        mBarCountTextView.setText("40 Bars used");
        mValuesCountTextView.setText("30 Animation Values used");
        mAnimationDurationTextView.setText("2000 ms");

        /*
         * Set Bar Color
         *  mEqualizerView.setBarColor("#ff00ff");
         *  mEqualizerView.setBarColor(ContextCompat.getColor(this,R.color.colorAccent));
         *
         * Set Bar Count
         *  mEqualizerView.setBarCount(10);
         *
         * Set Animation Duration
         *  mEqualizerView.setAnimationDuration(1000);
         *
         * Set Animation Value Count
         *   mEqualizerView.setObjectAnimationValueCount(10);
         */
    }

    private SeekBar.OnSeekBarChangeListener mBarCountSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mEqualizerView.stopBars();
            mPlayPauseButton.setText(getString(R.string.start));
            mEqualizerView.setBarCount(i + 1);
            mBarCountTextView.setText((i + 1) + " bars used");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener mValuesCountSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mEqualizerView.stopBars();
            mPlayPauseButton.setText(getString(R.string.start));
            mEqualizerView.setObjectAnimationValueCount(i + 1);
            mValuesCountTextView.setText((i + 1) + " values used");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener mAnimationDurationSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mEqualizerView.stopBars();
            mPlayPauseButton.setText(getString(R.string.start));
            mEqualizerView.setAnimationDuration((i + 1) * 100);
            mAnimationDurationTextView.setText(((i + 1) * 100) + " ms");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };



    private View.OnClickListener mPlayButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mEqualizerView.isAnimating()) {
                mEqualizerView.stopBars();
                mPlayPauseButton.setText(getString(R.string.start));
            } else {
                mEqualizerView.animateBars();
                mPlayPauseButton.setText(getString(R.string.stop));
            }
        }
    };
}
