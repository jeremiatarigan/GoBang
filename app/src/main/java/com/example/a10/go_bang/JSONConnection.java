package com.example.a10.go_bang;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class JSONConnection {

    private int responCode = 0;
    private String responString;
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<String> values = new ArrayList<>();

    JSONConnection(){

    }

    void proses(String stringUrl) {
        HttpURLConnection httpURLConnection = null;
        try {
            // Setup HttpURLConnection class to send and receive data from php and mysql
            URL url = new URL(stringUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Accept-Encoding", "utf-8");

            if (keys.size() > 0) {
                String parameter = "";
                for (int a = 0; a < keys.size(); a++) {
                    if (a != 0)
                        parameter += "&";
                    parameter += keys.get(a) + "=" + values.get(a);
                }
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(parameter);
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            responCode = httpURLConnection.getResponseCode();
            //Log.i("xxxxxx", "vv : " + response_code);
            // Check if successful connection made
            if (!isProsesFailed()) {
                // Read data sent from server
                InputStream input = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to onPostExecute method
                //return (result.toString());
                responString = result.toString();
                //Log.i("xxxxx", result.toString());

            } else {
                //return (String.valueOf(response_code));

                if (responCode == HttpURLConnection.HTTP_INTERNAL_ERROR)
                    responString = "Terjadi Masalah Pada Server";
                else if (responCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
                    responString = "Respon Server Terlalu Lama";
                else if (responCode == HttpURLConnection.HTTP_NOT_FOUND)
                    responString = "Halaman Tidak Ditemukan";
                else
                    responString = httpURLConnection.getResponseMessage();
            }

        } catch (SocketTimeoutException e) {
            responCode = 0;
            responString = "Waktu Koneksi Ke Server Habis";
        } catch (UnknownHostException e) {
            responCode = 0;
            responString = "Alamat Tidak Ditemukan";
        } catch (ConnectException e) {
            responCode = 0;
            responString = "Gagal Terhubung Ke Server";
        } catch (IOException e) {
            responCode = 0;
            responString = "Terjadi Kesalahan";
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
    }

    Boolean isProsesFailed() {
        return responCode != HttpURLConnection.HTTP_OK;
    }

    void addParameter(String key, String value) {
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        }
    }

    void addParameter(String key, Double value) {
        addParameter(key, String.valueOf(value));
    }

    void setResponCode(int responCode) {
        this.responCode = responCode;
    }

    void setResponString(String responString) {
        this.responString = responString;
    }

    String getResponString() {
        return responString;
    }
}
