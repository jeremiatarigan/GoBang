package com.example.a10.go_bang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

public class UserRegister_Act extends AppCompatActivity {

    EditText ETnamalengkp,ETusername,ETpassword,ETemail;
    Button BTNlogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_);

        ETnamalengkp = findViewById(R.id.nama_lengkap);
        ETusername = findViewById(R.id.username);
        ETpassword = findViewById(R.id.password);
        ETemail = findViewById(R.id.email);
        BTNlogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressbarr);

        BTNlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TambahUser().execute();
            }
        });

    }

    class TambahUser extends AsyncTask<String,String,String> {
        JSONConnection koneksi = new JSONConnection();
        @Override
        protected void onPreExecute() {
            koneksi.addParameter("nama_lengkap",ETnamalengkp.getText().toString());
            koneksi.addParameter("username",ETusername.getText().toString());
            koneksi.addParameter("password",ETpassword.getText().toString());
            koneksi.addParameter("email",ETemail.getText().toString());
            progressBar.setVisibility(View.INVISIBLE);
        }


        @Override
        protected String doInBackground(String... strings) {
            String url = Variabelglobal.Url + "/userregister.php";
            koneksi.proses(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(ETnamalengkp.length() !=0 && ETusername.length() != 0 && ETpassword.length() != 0 ){
                Toast.makeText(UserRegister_Act.this,"Berhasil Disimpan",Toast.LENGTH_LONG).show();

                Intent succes = new Intent(UserRegister_Act.this,SuccesRegisterAct.class);
                startActivity(succes);
                progressBar.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(UserRegister_Act.this,"Fill All Your Data",Toast.LENGTH_LONG).show();
            }
        }
    }
}
