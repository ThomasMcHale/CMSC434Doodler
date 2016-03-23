package com.example.earthwhale.cmsc434doodler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by EarthWhale on 3/9/2016.
 */
public class DoodleView extends View {

    private Paint _paintDoodle = new Paint();
    private ArrayList<PathData> _paths = new ArrayList<PathData>();

    private PathData _currentPath;
    private int _currentColor = 0;
    private boolean _currentMode = false;
    private float _currentSize = 4;
    private int _currentTransparency = 255;
    private String _currentReflection = "None";

    public DoodleView(Context context) {
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle){
        _paintDoodle.setColor(Color.RED);
        _paintDoodle.setAntiAlias(true);
        _paintDoodle.setStyle(Paint.Style.STROKE);

    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        // Will need to be adjusted for erasing.
        for (PathData path :_paths){

            // Grab this path's properties and update our brush.
            if (path.eraser){
                // When we erase, we're just going to paint with the same color as the background.
                _paintDoodle.setColor(((ColorDrawable) this.getBackground()).getColor());
                _paintDoodle.setAlpha(255);
            } else {
                // Okay, lets grab the paint settings.
                _paintDoodle.setColor(path.color);
                _paintDoodle.setAlpha(path.transparency);
            }
            _paintDoodle.setStrokeWidth(path.size);

            for (Path p : path.path) {
                canvas.drawPath(p, _paintDoodle);
            }
        }
        if (_currentPath != null){
            // Grab this path's properties and update our brush.
            if (_currentPath.eraser){
                // When we erase, we're just going to paint with the same color as the background.
                _paintDoodle.setColor(((ColorDrawable) this.getBackground()).getColor());
                _paintDoodle.setAlpha(255);
            } else {
                // Okay, lets grab the paint settings.
                _paintDoodle.setColor(_currentPath.color);
                _paintDoodle.setAlpha(_currentPath.transparency);
            }
            _paintDoodle.setStrokeWidth(_currentPath.size);

            for (Path p : _currentPath.path) {
                canvas.drawPath(p, _paintDoodle);
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                // make a new PathData.

                _currentPath = new PathData();
                _currentPath.color = _currentColor;
                _currentPath.eraser = _currentMode;
                _currentPath.size = _currentSize;
                _currentPath.transparency = _currentTransparency;

                // Sketch Finger line
                _currentPath.path.add(new Path());
                _currentPath.path.get(0).moveTo(touchX, touchY);
                // Sketch reflections
                if (_currentReflection.equals("Horizontal")){
                    _currentPath.path.add(new Path());
                    _currentPath.path.get(1).moveTo((this.getWidth()/2 - touchX) + this.getWidth()/2, touchY);
                } else if (_currentReflection.equals("Vertical")){
                    _currentPath.path.add(new Path());
                    _currentPath.path.get(1).moveTo(touchX, (this.getHeight()/2 - touchY) + this.getHeight()/2);
                } else if (_currentReflection.equals("Quad")){
                    _currentPath.path.add(new Path());
                    _currentPath.path.get(1).moveTo((this.getWidth()/2 - touchX) + this.getWidth()/2, touchY);

                    _currentPath.path.add(new Path());
                    _currentPath.path.get(2).moveTo(touchX, (this.getHeight()/2 - touchY) + this.getHeight()/2);

                    _currentPath.path.add(new Path());
                    _currentPath.path.get(3).moveTo((this.getWidth()/2 - touchX) + this.getWidth()/2, (this.getHeight()/2 - touchY) + this.getHeight()/2);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                _currentPath.path.get(0).lineTo(touchX, touchY);
                if (_currentReflection.equals("Horizontal")){
                    _currentPath.path.get(1).lineTo((this.getWidth()/2 - touchX) + this.getWidth()/2, touchY);
                } else if (_currentReflection.equals("Vertical")) {
                    _currentPath.path.get(1).lineTo(touchX, (this.getHeight()/2 - touchY) + this.getHeight()/2);
                } else if (_currentReflection.equals("Quad")){
                    _currentPath.path.add(new Path());
                    _currentPath.path.get(1).lineTo((this.getWidth()/2 - touchX) + this.getWidth()/2, touchY);

                    _currentPath.path.add(new Path());
                    _currentPath.path.get(2).lineTo(touchX, (this.getHeight()/2 - touchY) + this.getHeight()/2);

                    _currentPath.path.add(new Path());
                    _currentPath.path.get(3).lineTo((this.getWidth()/2 - touchX) + this.getWidth()/2, (this.getHeight()/2 - touchY) + this.getHeight()/2);
                }
                break;
            case MotionEvent.ACTION_UP:
                _paths.add(_currentPath);
                _currentPath = null;
                break;
        }

        invalidate();
        return true;
    }

    // Removes the last path drawn.
    public boolean undo() {
        if (_paths.size() > 0) {
            _paths.remove(_paths.size() - 1);
            invalidate();
            return true;
        }
        return false;
    }

    public void clear() {
        _paths.clear();
        invalidate();
    }

    public void setTransparency(int transparency) {
        _currentTransparency = (int) (((100.0 - (double) transparency) / 100.0) * 255);
    }

    public void setDoodleBackground(int[] colors) {
        // Set the background color
        int color = Color.argb(255, colors[0], colors[1], colors[2]);
        setBackgroundColor(color);
        // Update all eraser paths
        for (PathData path :_paths){
            if (path.eraser){
                path.color = color;
            }
        }
        // invalidate to redraw.
        invalidate();
    }

    public void setDoodleColor(int[] colors) {
        _currentColor = Color.argb(255, colors[0], colors[1], colors[2]);
    }

    public void setDoodleMode(boolean isChecked) {
        _currentMode = isChecked;
    }

    public void setSize(int size) {
        _currentSize = size;
    }

    public void setReflection(String reflection) {
        _currentReflection = reflection;
    }

    private class PathData {
        private ArrayList<Path> path = new ArrayList<Path>();
        private boolean eraser;
        private int transparency;
        private float size;
        private int color;
    }
}
