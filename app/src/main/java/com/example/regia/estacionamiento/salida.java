package com.example.regia.estacionamiento;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jose on 1/27/2017.
 */

public class salida extends AppCompatActivity {

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salida);
    };


    public void goExit (View view) throws InterruptedException {
        final String puesto = String.valueOf(getIntent().getExtras().getInt("puesto"));
        final int[][] status = {new int[1]};
        Thread thread = new Thread(new Runnable() {

            private volatile int value;

            @Override
            public void run() {
                try

                {
                    System.out.println("POST Puesto " + puesto);
                    String query = "puesto=" + puesto;

                    URL url = new URL("http://192.168.43.220:3000/setsalida");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //connection.setRequestProperty("Cookie", cookie);
                    //Set to POST
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(10000);
                    Writer writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(query);
                    writer.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    System.out.println("POST Respon " + sb.toString());
                    if (Long.parseLong(sb.toString()) == 0) {
                        status[0][0] = Integer.parseInt(puesto);
                        writer.close();
                    } else {
                        status[0][0] = 0;
                        writer.close();
                    }
                } catch (
                        Exception e
                        )

                {
                    Log.e("MYAPP", "exception", e);
                }

            }

            public int getValue() {
                return value;
            }


        });
        thread.start();
        thread.join();
        finish();

    }
}
