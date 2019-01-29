package com.stratisapps.www.drawpassword;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class PasswordAuthenticator extends AppCompatActivity {

    private ImageView correctButton;
    private ImageView deleteButton;
    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_authenticator);
    }

    public void showEditingTools(){
        AlphaAnimation animCorrectButton = new AlphaAnimation(0,1);
        AlphaAnimation animDeleteButton = new AlphaAnimation(0,1);
        animCorrectButton.setDuration(300);
        animDeleteButton.setDuration(300);
        correctButton.setAnimation(animCorrectButton);
        deleteButton.setAnimation(animDeleteButton);
        correctButton.startAnimation(animCorrectButton);
        deleteButton.startAnimation(animDeleteButton);
    }
}