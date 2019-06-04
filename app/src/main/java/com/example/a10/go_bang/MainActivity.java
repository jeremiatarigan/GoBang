package com.example.a10.go_bang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Animation bottomtotop,toptobottom;
    TextView namaapp;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toptobottom = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.toptobottom);

        namaapp = findViewById(R.id.namaapp);
        logo = findViewById(R.id.logo);

        logo.startAnimation(toptobottom);
        namaapp.startAnimation(bottomtotop);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toDashboard = new Intent(MainActivity.this,DashboardUtamaAct.class);
                startActivity(toDashboard);
                finish();
            }
        },2000);

    }

}
