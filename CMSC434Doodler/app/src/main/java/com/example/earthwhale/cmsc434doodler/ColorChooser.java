package com.example.earthwhale.cmsc434doodler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by EarthWhale on 3/21/2016.
 */
public class ColorChooser extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.colorchooser);




        // RED SLIDER
        SeekBar redSlider = (SeekBar) findViewById(R.id.chooserRedBar);
        redSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            TextView display = (TextView) findViewById(R.id.chooserRedValue);
            TextView colorDisplay = (TextView) findViewById(R.id.chooserColorDisplay);

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                display.setText(Integer.toString(progress));
                ColorDrawable cd = (ColorDrawable) colorDisplay.getBackground();
                colorDisplay.setBackgroundColor(Color.argb(255, progress, Color.green(cd.getColor()), Color.blue(cd.getColor())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // GREEN SLIDER
        SeekBar greenSlider = (SeekBar) findViewById(R.id.chooserGreenBar);
        greenSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            TextView display = (TextView) findViewById(R.id.chooserGreenValue);
            TextView colorDisplay = (TextView) findViewById(R.id.chooserColorDisplay);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                display.setText(Integer.toString(progress));
                ColorDrawable cd = (ColorDrawable) colorDisplay.getBackground();
                colorDisplay.setBackgroundColor(Color.argb(255, Color.red(cd.getColor()), progress , Color.blue(cd.getColor())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // BLUE SLIDER
        SeekBar blueSlider = (SeekBar) findViewById(R.id.chooserBlueBar);
        blueSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            TextView display = (TextView) findViewById(R.id.chooserBlueValue);
            TextView colorDisplay = (TextView) findViewById(R.id.chooserColorDisplay);

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                display.setText(Integer.toString(progress));
                ColorDrawable cd = (ColorDrawable) colorDisplay.getBackground();
                colorDisplay.setBackgroundColor(Color.argb(255, Color.red(cd.getColor()), Color.green(cd.getColor()), progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Show previous settings as default when opening activity
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int[] color = extras.getIntArray("color");

            redSlider.setProgress(color[0]);
            TextView redDisplay = (TextView) findViewById(R.id.chooserRedValue);
            redDisplay.setText(Integer.toString(color[0]));

            greenSlider.setProgress(color[1]);
            TextView greenDisplay = (TextView) findViewById(R.id.chooserGreenValue);
            greenDisplay.setText(Integer.toString(color[1]));

            blueSlider.setProgress(color[2]);
            TextView blueDisplay = (TextView) findViewById(R.id.chooserBlueValue);
            blueDisplay.setText(Integer.toString(color[2]));



        }

        Button close = (Button) findViewById(R.id.chooserAccept);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();

                // Get the color value.
                int[] color = new int[3];
                TextView colorDisplay = (TextView) findViewById(R.id.chooserColorDisplay);
                ColorDrawable cd = (ColorDrawable) colorDisplay.getBackground();
                color[0] = Color.red(cd.getColor());
                color[1] = Color.green(cd.getColor());
                color[2] = Color.blue(cd.getColor());

                resultIntent.putExtra("color", color);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}
