package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RemoveStudentFromDB extends AppCompatActivity {

    private Button btnR;
    private EditText etN,etId,etC;

    private DatabaseReference mRef;

    private String name,cnic,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_student_from_db);

        mRef= FirebaseDatabase.getInstance().getReference("users");

        btnR=(Button)findViewById(R.id.btnRemoveStudentFromDB);
        etN=(EditText)findViewById(R.id.etStudentName);
        etId=(EditText)findViewById(R.id.etStudentId);
        etC=(EditText)findViewById(R.id.etStudentCnic);

        final ProgressDialog progressDialog=new ProgressDialog(RemoveStudentFromDB.this);
        progressDialog.setTitle("Removing Student");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);


        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                name=etN.getText().toString();
                id=etId.getText().toString();
                cnic=etC.getText().toString();

                if(name.isEmpty() || id.isEmpty() || cnic.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Invalid info",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                mRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        StudentProfileInfo spi=dataSnapshot.getValue(StudentProfileInfo.class);
                        if(cnic.equals(spi.studentCnic) && name.equals(spi.studentName)){
                            mRef.child(id).removeValue();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Student Successfully removed from DB",Toast.LENGTH_LONG).show();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Invalid Info",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"invalid info",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



    }
}
