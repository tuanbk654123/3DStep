package com.example.a3dstep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtTrackyourHelth,txt3DStep;
    ImageView img3D;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addConTrol();
        addEvent();
    }

    private void addEvent() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
        img3D.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent intent = new Intent(MainActivity.this,StartActivity.class);

                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void addConTrol() {
        txtTrackyourHelth = (TextView) findViewById(R.id.txtTrackyourHelth);
        txt3DStep = (TextView) findViewById(R.id.txt3DStep);
        img3D = (ImageView) findViewById(R.id.img3D);
       //this.getSupportActionBar().hide();
    }
}
