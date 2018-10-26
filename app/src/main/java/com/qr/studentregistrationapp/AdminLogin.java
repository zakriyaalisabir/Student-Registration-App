package com.qr.studentregistrationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.studentregistrationapp.R;

public class AdminLogin extends AppCompatActivity {

    private EditText etUN,etUP;
    private Button btnL;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth=FirebaseAuth.getInstance();

        btnL=(Button)findViewById(R.id.btnLogin);
        etUN=(EditText) findViewById(R.id.etUsername);
        etUP=(EditText)findViewById(R.id.etPassword);

        final ProgressDialog progressDialog=new ProgressDialog(AdminLogin.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Please wait ...");

        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String u=etUN.getText().toString();
                String p=etUP.getText().toString();

                if(u.isEmpty() || p.isEmpty()){
                    Toast.makeText(getApplicationContext(),"invalid info",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }

                mAuth.signInWithEmailAndPassword(u,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),AdminAfterLogin.class));
                            finish();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


    }
}
