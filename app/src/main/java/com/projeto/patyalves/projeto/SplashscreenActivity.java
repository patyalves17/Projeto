package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashscreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH=300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        animation.reset();
        ImageView imageView= (ImageView) findViewById(R.id.ivSplash);

        if(imageView!=null){
            imageView.clearAnimation();
            imageView.startAnimation(animation);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashscreenActivity.this, InicialActivity.class));
                SplashscreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashscreenActivity.this, MainActivity.class));
                SplashscreenActivity.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);*/

    }
}
