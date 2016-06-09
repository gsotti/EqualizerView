package eu.gsottbauer.equalizerviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import eu.gsottbauer.equalizerview.EqualizerView;

public class MainActivity extends AppCompatActivity {

    private EqualizerView mEqualizerView;
    private Button mPlayPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEqualizerView = (EqualizerView) findViewById(R.id.equalizer);
        mPlayPauseButton = (Button) findViewById(R.id.play_button);
        mPlayPauseButton.setOnClickListener(mPlayButtonOnClickListener);


        mEqualizerView.setBarColor("#ff00ff");
    }



    private View.OnClickListener mPlayButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mEqualizerView.isAnimating())
            {
                mEqualizerView.stopBars();
                mPlayPauseButton.setText(getString(R.string.start));
            }
            else
            {
                mEqualizerView.animateBars();
                mPlayPauseButton.setText(getString(R.string.stop));
            }
        }
    };
}
