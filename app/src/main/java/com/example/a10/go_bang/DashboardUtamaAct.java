package com.example.a10.go_bang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DashboardUtamaAct extends AppCompatActivity {

    LinearLayout btn_driver,btnuser;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_utama);

        btn_driver = findViewById(R.id.btn_driver);
        btnuser = findViewById(R.id.btnuser);


        btn_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekGPS();
            }
        });

        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotouser = new Intent(DashboardUtamaAct.this,UserLoginAct.class);
                startActivity(gotouser);
            }
        });
    }


    public void cekGPS () {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Toast.makeText(this, "Terjadi kesalahan. Silahkan coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
        } else {
            Boolean aktif = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (aktif) {
                Intent gotodriver = new Intent(DashboardUtamaAct.this,LoginAct.class);
                startActivity(gotodriver);
            } else {
                final AlertDialog alertDialog = new AlertDialog.Builder(DashboardUtamaAct.this).create();
                alertDialog.setTitle("PEMBERITAHUAN");
                alertDialog.setMessage("GPS tidak aktif ! Aktifkan ?");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent setting = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(setting);
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        Toast.makeText(DashboardUtamaAct.this, "Anda harus mengaktifkan GPS", Toast.LENGTH_LONG).show();
                    }
                });

                alertDialog.show();
            }
        }

    }

}
