package com.example.regia.estacionamiento;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
    String email;
    String password;

    public void init(Bundle B) {
        if (B != null) {
            email = B.getString("email");
            password = B.getString("password");
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

    public void goPuesto (View view) throws InterruptedException {
        final String puesto = view.getTag().toString();
        final int[][] status = {new int[1]};
        puestom = puesto;
        Thread thread = new Thread(new Runnable() {

            private volatile int value;

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

        if (status[0][0] > 0) {
            Intent i = new Intent(this, salida.class);
            i.putExtra("puesto", status[0][0]);
            startActivity(i);
            finish();
        } else {

            Context context = view.getContext();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage("Puesto Ocupado");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();


        }

    }
}