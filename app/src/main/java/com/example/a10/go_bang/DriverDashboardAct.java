package com.example.a10.go_bang;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DriverDashboardAct extends AppCompatActivity {

    TextView ViewnamaLengkap,Viewusername,Viewalamat,Viewno_hp,Viewemail,Viewkode_angkot;
    DatabaseHelper databaseHelper;
    LinearLayout btn_back;

    Button btn_edit,btnLogout,btnMyLocation;


    Integer iddiver = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);

        ViewnamaLengkap = findViewById(R.id.ViewnamaLengkap);
        Viewusername = findViewById(R.id.Viewusername);
        Viewalamat      = findViewById(R.id.Viewalamat);
        Viewno_hp       = findViewById(R.id.Viewno_hp);
        Viewemail       = findViewById(R.id.Viewemail);
        Viewkode_angkot      = findViewById(R.id.Viewkode_angkot);
        btn_back      = findViewById(R.id.btn_back);
        btnLogout = findViewById(R.id.btnLogout);
        btnMyLocation = findViewById(R.id.btnMyLocation);

        btn_edit = findViewById(R.id.btn_edit);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.selectTable(Variabelglobal.driver);
        databaseHelper.hasRow();
        Viewusername.setText(databaseHelper.getUsername());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driveredit = new Intent(DriverDashboardAct.this,DriverEditAct.class);

                driveredit.putExtra("iddriver",iddiver);
                driveredit.putExtra("username",Viewusername.getText().toString());

                startActivity(driveredit);


            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Variabelglobal.statusLogin,0);
                databaseHelper.updateRecord(Variabelglobal.driver,contentValues);
                Variabelglobal.myLocationListener.stop();
                finish();

                startActivity(new Intent(DriverDashboardAct.this,DashboardUtamaAct.class));

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtodashboardutama = new Intent(DriverDashboardAct.this,DashboardUtamaAct.class);
                startActivity(backtodashboardutama);
            }
        });

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Driverlocation = new Intent(DriverDashboardAct.this,DriverMapsAct.class);
                startActivity(Driverlocation);
            }
        });



        //Intent data = getIntent();
        //Viewusername.setText(data.getStringExtra("username"));


        Variabelglobal.myLocationListener = new MyLocationListener(this);
         new TampilData().execute();


    }

    @SuppressLint("StaticFieldLeak")
    class TampilData extends AsyncTask<String, String, String> {
        JSONConnection jsonConnection = new JSONConnection();

        @Override
        protected void onPreExecute() {
            jsonConnection.addParameter("username", Viewusername.getText().toString());
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = Variabelglobal.Url + "/dashboarddriver.php";
            jsonConnection.proses(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(jsonConnection.getResponString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                jsonObject = jsonArray.getJSONObject(0);

                    String nama_lengkap = jsonObject.getString("nama_lengkap");
                    String username = jsonObject.getString("username");
                    String email = jsonObject.getString("email");
                    String no_hp = jsonObject.getString("no_hp");
                    String kode_angkot = jsonObject.getString("kode_angkot");
                    String alamat = jsonObject.getString("alamat");

                    iddiver = jsonObject.getInt("iddriver");

                    ViewnamaLengkap.setText(nama_lengkap);
                    Viewusername.setText(username);
                    Viewalamat.setText(alamat);
                    Viewno_hp.setText(no_hp);
                    Viewemail.setText(email);
                    Viewkode_angkot.setText(kode_angkot);



            } catch (JSONException e) {
                Toast.makeText(DriverDashboardAct.this, "Terjadi  Kesalahan", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
