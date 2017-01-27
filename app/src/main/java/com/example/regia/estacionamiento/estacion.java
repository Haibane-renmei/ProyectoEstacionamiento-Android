package com.example.regia.estacionamiento;

import android.content.Intent;
import android.os.Bundle;
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
 * Created by regia on 1/22/17.
 */

public class estacion extends AppCompatActivity {

    //Bundle b = getIntent().getExtras();
    int id;
    String puestom;


    public void init(Bundle B) {
        id = 1;
        if (B != null )
        {
            id = B.getInt("id");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle B = getIntent().getExtras();
        init(B);

        if (id == 1) {
            setContentView(R.layout.estacion1);
        } else {
            setContentView(R.layout.estacion2);
        }
    };

    public void goPuesto (View view) {
        final String puesto = view.getTag().toString();
        puestom = puesto;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try

                {
                    System.out.println("POST Puesto " + puesto);
                    String query = "puesto=" + puesto;

                    URL url = new URL("http://192.168.43.220:3000/setpuesto");
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
                        writer.close();
                        setContentView(R.layout.salida);
                    } else {
                        writer.close();
                    }
                } catch (
                        Exception e
                        )

                {
                    Log.e("MYAPP", "exception", e);
                }
            }
        });
        thread.start();
    }

    public boolean salida(View view) {
        final String puesto = puestom;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try

                {
                    String query = "puesto =" + puesto;

                    URL url = new URL("ttp://192.168.43.220:3000/setsalida");
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
                        writer.close();
                        setContentView(R.layout.selector);
                    } else {
                        writer.close();
                    }
                } catch (
                        Exception e
                        )

                {
                    Log.e("MYAPP", "exception", e);
                }
            }
        });
        return true;

    }
}
