package com.example.earthwhale.cmsc434doodler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by EarthWhale on 3/21/2016.
 */
public class SizeSlider extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sizeoverlay);



        SeekBar slider = (SeekBar) findViewById(R.id.sizeSeekBar);

        // Show previous settings as default when opening activity
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            slider.setProgress(extras.getInt("size"));
            TextView display = (TextView) findViewById(R.id.sizeValue);
            display.setText(Integer.toString(extras.getInt("size") + 1));
        }

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            TextView display = (TextView) findViewById(R.id.sizeValue);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                display.setText(Integer.toString(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button close = (Button) findViewById(R.id.sizeCloseButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("size", ((SeekBar) findViewById(R.id.sizeSeekBar)).getProgress()+1);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
