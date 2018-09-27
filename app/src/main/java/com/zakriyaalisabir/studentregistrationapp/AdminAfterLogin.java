package com.zakriyaalisabir.studentregistrationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AdminAfterLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_after_login);

        Toast.makeText(getApplicationContext(),"Successfull Login",Toast.LENGTH_LONG).show();


    }
}
