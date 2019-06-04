package com.example.a10.go_bang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DriverEditAct extends AppCompatActivity {

    EditText EdTxtusername,EdTxtpassword;
    Button btn_simpan;
    Integer iddriver;

    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_edit);

        EdTxtusername = findViewById(R.id.EdTxtusername);
        EdTxtpassword = findViewById(R.id.EdTxtpassword);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_back = findViewById(R.id.btn_back);

        Intent idpengemudi = getIntent();
        iddriver = idpengemudi.getIntExtra("iddriver",0);

        String username = idpengemudi.getStringExtra("username");
        String password = idpengemudi.getStringExtra("password");
        EdTxtusername.setText(username);
        EdTxtpassword.setText(password);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               new EditData().execute();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtodriverdashboard = new Intent(DriverEditAct.this,DriverDashboardAct.class);
                startActivity(backtodriverdashboard);
            }
        });



    }

    @SuppressLint("StaticFieldLeak")
    class EditData extends AsyncTask<String, String, String> {
        JSONConnection jsonConnection = new JSONConnection();

        @Override
        protected void onPreExecute() {
            jsonConnection.addParameter("iddriver", iddriver.toString());
            jsonConnection.addParameter("username",EdTxtusername.getText().toString());
            jsonConnection.addParameter("password",EdTxtpassword.getText().toString());

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = Variabelglobal.Url + "/EditDriver.php";
            jsonConnection.proses(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(jsonConnection.getResponString());
                if (jsonObject.getBoolean("gagal")){
                    Toast.makeText(DriverEditAct.this, "Edit Gagal", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DriverEditAct.this, "Sukses", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                Toast.makeText(DriverEditAct.this, "Terjadi  Kesalahan", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
