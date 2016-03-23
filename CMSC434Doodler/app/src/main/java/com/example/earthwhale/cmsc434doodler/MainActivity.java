package com.example.earthwhale.cmsc434doodler;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    static final int CHANGE_TRANSPARENCY_REQUEST = 1;
    static final int CHANGE_BACKGROUND_REQUEST = 2;
    static final int CHANGE_COLOR_REQUEST = 3;
    static final int CHANGE_SIZE_REQUEST = 4;
    static final int CHANGE_REFLECTION_REQUEST = 5;

    // Save previous values for activities
    private int[] lastColor = {0,0,0};
    private int[] lastBackground = {0,0,0};
    private int lastTransparency = 0;
    private int lastSize = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        // Set up Undo:
        Button undoButton = (Button) findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
                doodleView.undo();
            }
        });

        // Set Up Clear:
        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
                doodleView.clear();
            }
        });

        //Transparency needs to launch a new activity to change transparency.
        Button transparencyButton = (Button) findViewById(R.id.transparencyButton);
        transparencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransparencySlider.class);
                intent.putExtra("transparency", lastTransparency);
                startActivityForResult(intent, CHANGE_TRANSPARENCY_REQUEST);
            }
        });

        //Size also needs to launch a new activity so the user can change size.
        //Note: In the future, I should make a Slider menu that is generic enough to be used for transparency AND size.
        Button sizeButton = (Button) findViewById(R.id.sizeButton);
        sizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SizeSlider.class);
                intent.putExtra("size", lastSize);
                startActivityForResult(intent, CHANGE_SIZE_REQUEST);
            }
        });

        // Background needs to launch the color chooser activity
        Button backgroundButton = (Button) findViewById(R.id.backgroundButton);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorChooser.class);
                intent.putExtra("color", lastBackground);
                startActivityForResult(intent, CHANGE_BACKGROUND_REQUEST);
            }
        });

        // Color needs to launch the color chooser activity
        Button colorButton = (Button) findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorChooser.class);
                intent.putExtra("color",lastColor);
                startActivityForResult(intent, CHANGE_COLOR_REQUEST);
            }
        });

        //Reflection button launches a reflection selection panel.
        Button reflectionButton = (Button) findViewById(R.id.reflectionButton);
        reflectionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ReflectionSelection.class), CHANGE_REFLECTION_REQUEST);
            }
        });

        // This is the toggle that swaps between Draw mode and erase mode.
        ToggleButton modeButton = (ToggleButton) findViewById(R.id.toggleButton);
        modeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
                doodleView.setDoodleMode(isChecked);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case (CHANGE_TRANSPARENCY_REQUEST) : {
                if (resultCode == Activity.RESULT_OK){
                    DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
                    doodleView.setTransparency(data.getIntExtra("transparency", 0));
                    lastTransparency = data.getIntExtra("transparency", 0);
                }
                break;
            }
            case (CHANGE_BACKGROUND_REQUEST) : {
                if (resultCode == Activity.RESULT_OK) {
                    DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
                    doodleView.setDoodleBackground(data.getIntArrayExtra("color"));
                    lastBackground = data.getIntArrayExtra("color");
                }
                break;
            }
            case (CHANGE_COLOR_REQUEST) : {
                if (resultCode == Activity.RESULT_OK) {
                    DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
                    doodleView.setDoodleColor(data.getIntArrayExtra("color"));
                    lastColor = data.getIntArrayExtra("color");
                }
                break;
            }
            case (CHANGE_SIZE_REQUEST) : {
                if (resultCode == Activity.RESULT_OK){
                    DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
                    doodleView.setSize(data.getIntExtra("size",1));
                    lastSize = data.getIntExtra("size",1);
                }
                break;
            }
            case (CHANGE_REFLECTION_REQUEST) : {
                if (resultCode == Activity.RESULT_OK){
                    DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
                    doodleView.setReflection(data.getStringExtra("reflection"));
                }
            }
        }
    }
}
