package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewCoursesToDB extends AppCompatActivity {

    private DatabaseReference mRef;
    private EditText etCN,etCPRN;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_courses_to_db);

        mRef= FirebaseDatabase.getInstance().getReference();

        btnAdd=(Button)findViewById(R.id.btnAddNewCourseToDb);
        etCN=(EditText)findViewById(R.id.etNewCourseName);
        etCPRN=(EditText)findViewById(R.id.etNewCoursePreReqName);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog=new ProgressDialog(AddNewCoursesToDB.this);
                progressDialog.setTitle("Adding new course");
                progressDialog.setMessage("Please wait ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                final String a=etCN.getText().toString().toUpperCase();
                final String b=etCPRN.getText().toString().toUpperCase();

                if(a.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Course name is not valid",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }

                Course course=new Course(a,b);

                mRef.child("courses").child(a).setValue(course);
                Toast.makeText(getApplicationContext(),"New Course Added Successfully To DB",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                etCN.setText("");
                etCPRN.setText("");

            }
        });

    }
}
