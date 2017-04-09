package com.example.cabanni.lokalpatriot_brackel;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by cabanni on 28.12.16.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.splash_screen_layout);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        animation.setDuration(5000);
        // final Animation animation2= AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        imageView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // imageView.startAnimation(animation2);
                finish();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
