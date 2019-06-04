package com.example.a10.go_bang;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserLoginAct extends AppCompatActivity {

    EditText username,password;
    Button btn_login;
    ProgressBar progressbarr;
    ImageView btn_back;

    TextView userregister;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin_act);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        progressbarr = findViewById(R.id.progressbarr);
        btn_back = findViewById(R.id.btn_back);

        userregister = findViewById(R.id.userregister);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback = new Intent(UserLoginAct.this,DashboardUtamaAct.class);
                startActivity(goback);
            }
        });

        userregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gonewuser = new Intent(UserLoginAct.this,UserMapAct.class);
                startActivity(gonewuser);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Eksekusi().execute();

            }

        });

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.selectTable(Variabelglobal.driver);

        if (databaseHelper.hasRow()) {
            if (databaseHelper.isLogin()) {
                Intent i = new Intent(UserLoginAct.this, UserMapAct.class);
                finish();
                startActivity(i);
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    class Eksekusi extends AsyncTask<String,String,String> {
        JSONConnection koneksi = new JSONConnection();
        @Override
        protected void onPreExecute() {
            koneksi.addParameter("username",username.getText().toString());
            koneksi.addParameter("password",password.getText().toString());
            progressbarr.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... strings) {
            String url = Variabelglobal.Url + "/loginuser.php";
            koneksi.proses(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressbarr.setVisibility(View.INVISIBLE);
            try {
                JSONObject jsonObject = new JSONObject(koneksi.getResponString());
                if (jsonObject.getBoolean("gagal")){
                    Toast.makeText(UserLoginAct.this,jsonObject.getString("pesan"),Toast.LENGTH_SHORT).show();
                }else{

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Variabelglobal.idDriver, jsonObject.getInt(Variabelglobal.idDriver));
                    contentValues.put(Variabelglobal.username, username.getText().toString());
                    contentValues.put(Variabelglobal.statusLogin, 1);

                    if(databaseHelper.hasRow())
                        databaseHelper.updateRecord(Variabelglobal.driver, contentValues);
                    else
                        databaseHelper.insertRecord(Variabelglobal.driver, Variabelglobal.idDriver, contentValues);

                    Toast.makeText(UserLoginAct.this,"Berhasil Login loh",Toast.LENGTH_SHORT).show();
                    Intent adminnn = new Intent(UserLoginAct.this,UserMapAct.class);

                    //adminnn.putExtra("username",username.getText().toString());
                    finish();


                    startActivity(adminnn);
                }
            } catch (JSONException e) {
                Toast.makeText(UserLoginAct.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }


}
