package com.stratisapps.www.drawpassword;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TutorialView extends View {
    private Path path;
    private Paint paint;
    private Context context;
    private AttributeSet attributeSet;
    private ArrayList<ArrayList<Integer>> arrayListPoints = new ArrayList<>();
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private int pathValue = 0;
    private int posValue = 0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            path = new Path();
            pathValue = 0;
            posValue = 0;
            postInvalidate();
        }
    };

    public TutorialView(Context context, AttributeSet attributeSet) {
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
        try {
            showObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
        if(pathValue < arrayListPoints.size()){
            arrayList = arrayListPoints.get(pathValue);
            if(posValue < arrayList.size()){
                if(posValue == 0){
                    path.moveTo(arrayList.get(posValue),arrayList.get(posValue+1));
                    posValue+=2;
                    postInvalidate();
                }
                else{
                    path.lineTo(arrayList.get(posValue), arrayList.get(posValue+1));
                    posValue+=2;
                    postInvalidate();
                }
            }
            else {
                posValue = 0;
                pathValue++;
                postInvalidate();
            }
        }
        else {
            handler.postDelayed(runnable, 3000);
        }
    }

    public void showObject() throws IOException{
        AssetManager assetManager = context.getAssets();
        InputStream input = assetManager.open("smiley.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String test = reader.readLine();
        while(test != null) {
            if (test.equals("new")) {
                arrayListPoints.add(arrayList);
                arrayList = new ArrayList<>();
            } else {
                arrayList.add(Integer.parseInt(test));
            }
            try{
                test = reader.readLine();
            }
            catch (NumberFormatException e){

            }
        }
        postInvalidate();
    }
}
