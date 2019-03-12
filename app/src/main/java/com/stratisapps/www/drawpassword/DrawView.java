package com.stratisapps.www.drawpassword;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Handler;

public class DrawView extends View{
    private Path path;
    private Paint paint;
    private Context context;
    private AttributeSet attributeSet;
    private int done = 0;
    private int done2 = 0;
    private int start = 0;
    private ArrayList<ArrayList<Integer>> arrayListPoints = new ArrayList<>();
    private ArrayList<Integer> arrayList = new ArrayList<>();


    public DrawView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        this.context = context;
        this.attributeSet = attributeSet;
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupDraw();
    }

    public void setupDraw(){
        path = new Path();
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(60f);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }
    
    /* This code will be changed later to allow for any drawing to be drawn
    but is limited to 4 touch events for now for creating the animation */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(done < 4){
            canvas.drawPath(path, paint);
        }
        else{
            try {
                if(done == 4){
                    saveObject();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
        else{
            if(arrayListPoints.size() > 0){
                arrayList = arrayListPoints.get(0);
                if(start == 0){
                    setupDraw();
                    start = 1;
                }
                if(done2 == 0){
                    path.moveTo(arrayList.get(0).x,arrayList.get(0).y);
                    done2 = 1;
                }
                canvas.drawPath(path,paint);
                arrayList.remove(0);
                if(arrayList.size() > 1){
                    path.lineTo(arrayList.get(0).x, arrayList.get(0).y);
                    arrayList.remove(0);
                    postInvalidate();
                }
                else{
                    arrayListPoints.remove(0);
                    done2 = 0;
                    if(arrayListPoints.size() != 0){
                        postInvalidate();
                    }
                }
            }
        }*/
    }

    public void saveObject() throws IOException{
        String root = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
        File directory = new File(root + "/tutorialAnimations");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, "smiley.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for(int i = 0; i < 4; i++){
            arrayList = arrayListPoints.get(i);
            for(int j = 0; j < arrayList.size(); j++){
                writer.write(arrayList.get(j).toString());
                writer.newLine();
            }
            writer.write("new");
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                arrayList = new ArrayList<>();
                arrayList.add((int)event.getX());
                arrayList.add((int)event.getY());
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                arrayList.add((int)event.getX());
                arrayList.add((int)event.getY());
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                arrayListPoints.add(arrayList);
                done++;
            default:
                return false;
        }
        postInvalidate();
        return true;
    }
}
