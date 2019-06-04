package com.example.a10.go_bang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccesRegisterAct extends AppCompatActivity {

    Animation bottomtotop,toptobottom,invisibletovisible,btn_animation;
    TextView namaapp;
    ImageView logo;
    Button btnSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_register);

        toptobottom = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.toptobottom);
        invisibletovisible = AnimationUtils.loadAnimation(this, R.anim.invisible_to_visible2);
        btn_animation = AnimationUtils.loadAnimation(this, R.anim.button_animation);


        namaapp = findViewById(R.id.namaapp);
        logo = findViewById(R.id.logo);
        btnSee = findViewById(R.id.btnSee);


        logo.startAnimation(bottomtotop);
        namaapp.startAnimation(invisibletovisible);
        btnSee.startAnimation(bottomtotop);

        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent succes = new Intent(SuccesRegisterAct.this,UserDashboardAct.class);
                startActivity(succes);
                btnSee.startAnimation(btn_animation);
                finish();
            }
        });




    }
}
