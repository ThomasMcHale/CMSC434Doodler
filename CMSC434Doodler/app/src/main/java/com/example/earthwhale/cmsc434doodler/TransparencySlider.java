package com.example.earthwhale.cmsc434doodler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by EarthWhale on 3/20/2016.
 */
public class TransparencySlider extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transparencyoverlay);

        SeekBar slider = (SeekBar) findViewById(R.id.transparencySeekBar);
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            TextView display = (TextView) findViewById(R.id.transparencyValue);

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                display.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button close = (Button) findViewById(R.id.transparencyCloseButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("transparency", ((SeekBar) findViewById(R.id.transparencySeekBar)).getProgress());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        // Show previous settings as default when opening activity
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            slider.setProgress(extras.getInt("transparency"));
            TextView display = (TextView) findViewById(R.id.transparencyValue);
            display.setText(Integer.toString(extras.getInt("transparency")));
        }

    }
}
