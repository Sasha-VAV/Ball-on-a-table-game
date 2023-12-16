package com.example.ballonatable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.View;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MyDraw extends View {
    boolean first = true;
    int x=0;
    int y=0;
    int rad=100;
    int scale = 8;
    public MyDraw(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        if (first){
            x=getWidth()/2;
            y=getHeight() / 2;
            first=false;
        }
        Log.d("res", String.valueOf(MainActivity.x)+" "+String.valueOf(MainActivity.y)+" "+String.valueOf(MainActivity.z));
        canvas.drawCircle(x, y, rad, paint);
        //x+=9.81*Math.cos(MainActivity.z)*scale/2;
        //y-=9.81*Math.cos(MainActivity.y)*scale/2;
        x+=9.81*MainActivity.z/2;
        y-=9.81*MainActivity.y/2;
        if (x<=rad)
            x=rad;
        if (x>=getWidth()-rad)
            x=getWidth()-rad;
        if (y<=rad)
            y=rad;
        if (y>=getHeight()-rad)
            y=getHeight()-rad;
        invalidate();
    }
}
