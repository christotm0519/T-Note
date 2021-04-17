package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import android.graphics.Path;
import static com.example.t_note.Model.display.current_brush;
import static com.example.t_note.Model.display.colorList;
import static com.example.t_note.Model.display.pathList;
public class DibuixarActivity extends AppCompatActivity {
    public static Path path = new Path();
    public static Paint paint_brush = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dibuixar);
    }

    public void eraser(View view){
        pathList.clear();
        colorList.clear();
        path.reset();
    }

    public void goma(View view){
        paint_brush.setColor(Color.WHITE);
        currentColor(paint_brush.getColor());
    }

    public void pencil(View view){
     paint_brush.setColor(Color.BLACK);
     currentColor(paint_brush.getColor());
    }
    public void redColor(View view){
        paint_brush.setColor(Color.RED);
        currentColor(paint_brush.getColor());
    }
    public void yelloColor(View view){
        paint_brush.setColor(Color.YELLOW);
        currentColor(paint_brush.getColor());
    }
    public void greenColor(View view){
        paint_brush.setColor(Color.GREEN);
        currentColor(paint_brush.getColor());

    }
    public void magentaColor(View view){
        paint_brush.setColor(Color.MAGENTA);
        currentColor(paint_brush.getColor());
    }
    public void blueColor(View view){
        paint_brush.setColor(Color.BLUE);
        currentColor(paint_brush.getColor());
    }
    public void currentColor(int c){
        current_brush = c;
        path = new Path();
    }
}

/*
Dibuixar select bar
activity
NO BORRAR
<com.google.android.material.slider.Slider
            android:id="@+id/slider"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:value="20.0"
            android:valueFrom="0.0"
            android:valueTo="100.0" />
 */
/*
public ViewGroup.LayoutParams params;
pubic static int current_brush = Color.BLACK;
public display(Context context){
    super(context);
    init(context);
    }
public display(Context context, @Nullable AttributeSet attrs){
 super(context,attrs);
 }
public display(Context context, @Nullable AttributeSet attrs,int defStyleAttr){
 super(context,attrs,defStyleAttr);
 }
 private void init(Context context){
    paint_brush.setAntiAlias(true);
    paint_brush.setColor(Color.BLACK);
    paint_brush.setStyle(Paint.Style.STROKE);
    paint_brush.setStrokeCap(Paint.Cap.ROUND);
    paint_brush.setStrokeJoin(Paint.Join.ROUND);
    paint_brush.setStrokeWidth(10f);
    params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        switch
 */