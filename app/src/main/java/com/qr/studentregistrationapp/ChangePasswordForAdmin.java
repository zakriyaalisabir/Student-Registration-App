package com.qr.studentregistrationapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studentregistrationapp.R;

public class ChangePasswordForAdmin extends AppCompatActivity {

    private EditText etOP,etNP;
    private Button btnNP;

    private FirebaseUser user;

    private String currentUserEmail,CurrentUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_for_admin);

        user=FirebaseAuth.getInstance().getCurrentUser();
        currentUserEmail=user.getEmail();

        etOP=(EditText)findViewById(R.id.etOldPassword);
        etNP=(EditText)findViewById(R.id.etNewPassword);

        btnNP=(Button)findViewById(R.id.btnAdminNewPassword);


        final ProgressDialog progressDialog=new ProgressDialog(ChangePasswordForAdmin.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Please wait ...");

        btnNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String op=etOP.getText().toString();
                final String np=etNP.getText().toString();
                if(op.isEmpty() || np.isEmpty()){
                    Toast.makeText(getApplicationContext(),"invalid inputs",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }else if(op.equals(np)){
                    Toast.makeText(getApplicationContext(),"New password cannot be same as old one",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                AuthCredential authCredential=EmailAuthProvider.getCredential(currentUserEmail,op);
                user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            user.updatePassword(np).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        etNP.setText("");
                                        etOP.setText("");
                                        Toast.makeText(getApplicationContext(), "Password Updated Successfully", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"New Password is not valid",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Old Password is not correct",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
}
