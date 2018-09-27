package com.zakriyaalisabir.studentregistrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    private EditText etUN,etUP;
    private Button btnL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btnL=(Button)findViewById(R.id.btnLogin);
        etUN=(EditText) findViewById(R.id.etUsername);
        etUP=(EditText)findViewById(R.id.etPassword);

        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u=etUN.getText().toString();
                String p=etUP.getText().toString();

                if(u.isEmpty() || p.isEmpty()){
                    Toast.makeText(getApplicationContext(),"invalid info",Toast.LENGTH_LONG).show();
                    return;
                }

                startActivity(new Intent(getApplicationContext(),AdminAfterLogin.class));
                finish();

            }
        });


    }
}
