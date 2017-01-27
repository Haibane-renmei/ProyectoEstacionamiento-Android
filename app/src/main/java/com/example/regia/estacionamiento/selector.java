package com.example.regia.estacionamiento;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

/**
 * Created by regia on 1/21/17.
 */

public class selector extends AppCompatActivity {

    String email;
    String password;

    public void init(Bundle B) {
        if (B != null)
        {
            email = B.getString("email");
            password = B.getString("password");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector);
        };

    public void goEstac(View view) {
        String pageNumber = view.getTag().toString();
        Intent i = new Intent(this, estacion.class);
        i.putExtra("email",email);
        i.putExtra("pass",password);

        if (pageNumber.equals("1")) {
            i.putExtra("id", 1);
            startActivity(i);
        } else {
            i.putExtra("id", 2);
            startActivity(i);
        }
    }
}
