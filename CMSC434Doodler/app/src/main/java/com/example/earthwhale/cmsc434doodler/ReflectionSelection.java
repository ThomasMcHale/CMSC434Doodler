package com.example.earthwhale.cmsc434doodler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by EarthWhale on 3/21/2016.
 */
public class ReflectionSelection extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reflectionoverlay);


        Button horizontal = (Button) findViewById(R.id.reflectionButtonHorizontal);
        horizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reflection", "Horizontal");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Button vertical = (Button) findViewById(R.id.reflectionButtonVertical);
        vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reflection", "Vertical");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Button none = (Button) findViewById(R.id.reflectionButtonNone);
        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reflection", "None");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Button quad = (Button) findViewById(R.id.reflectionButtonQuad);
        quad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reflection", "Quad");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
