package eu.gsottbauer.equalizerviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import eu.gsottbauer.equalizerview.EqualizerView;

public class MainActivity extends AppCompatActivity {

    private EqualizerView mEqualizerView;
    private Button mPlayPauseButton;
    private SeekBar mBarCountSeekBar;
    private TextView mBarCountTextView;
    private SeekBar mAnimationDurationSeekBar;
    private TextView mAnimationDurationTextView;
    private SeekBar mBarWidthSeekBar;
    private TextView mBarWidthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEqualizerView = (EqualizerView) findViewById(R.id.equalizer);
        mPlayPauseButton = (Button) findViewById(R.id.play_button);
        mBarCountTextView = (TextView) findViewById(R.id.bar_count);
        mAnimationDurationTextView = (TextView) findViewById(R.id.animation_duration);
        mBarWidthTextView = (TextView) findViewById(R.id.bar_width_textview);

        mBarCountSeekBar = (SeekBar) findViewById(R.id.bar_count_seekbar);
        mAnimationDurationSeekBar = (SeekBar) findViewById(R.id.animation_duration_seekbar);
        mBarWidthSeekBar= (SeekBar) findViewById(R.id.bar_width_seekbar);

        mPlayPauseButton.setOnClickListener(mPlayButtonOnClickListener);
        mBarCountSeekBar.setOnSeekBarChangeListener(mBarCountSeekBarChangeListener);
        mAnimationDurationSeekBar.setOnSeekBarChangeListener(mAnimationDurationSeekBarChangeListener);
        mBarWidthSeekBar.setOnSeekBarChangeListener(mBarWidthSeekBarChangeListener);

        mBarCountTextView.setText("40 Bars used");
        mAnimationDurationTextView.setText("2000 ms");
        mBarWidthTextView.setText("2 dp");

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
         *
         * Set MarginLeft/Right
         *   mEqualizerView.setMarginLeft(10);
         *   mEqualizerView.setMarginRight(10);
         *
         * Set Bar Width
         *   mEqualizerView.setBarWidth(5);
         *
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


    private SeekBar.OnSeekBarChangeListener mBarWidthSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mEqualizerView.stopBars();
            mPlayPauseButton.setText(getString(R.string.start));

            int width = (i <= 0) ? -1 : (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics());

            mEqualizerView.setBarWidth(width);
            mBarWidthTextView.setText(((i)) + " dp");
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
