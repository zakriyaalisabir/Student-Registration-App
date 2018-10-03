package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewStudent extends AppCompatActivity {


    private Button btnAdd;
    private EditText etSN,etSRN,etSB,etSS,etSD,etSP;

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        mRef=FirebaseDatabase.getInstance().getReference("users");

        btnAdd=(Button)findViewById(R.id.btnAddStudentInfo);

        etSN=(EditText)findViewById(R.id.etAddStudentName);
        etSRN=(EditText)findViewById(R.id.etAddStudentRollNo);
        etSB=(EditText)findViewById(R.id.etAddStudentBatch);
        etSD=(EditText)findViewById(R.id.etAddStudentDept);
        etSP=(EditText)findViewById(R.id.etAddStudentProgram);
        etSS=(EditText)findViewById(R.id.etAddStudentSemester);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=etSN.getText().toString();
                final String rollNo=etSRN.getText().toString();
                final String batch=etSB.getText().toString();
                final String semester=etSS.getText().toString();
                final String department=etSD.getText().toString();
                final String program=etSP.getText().toString();

                final ProgressDialog progressDialog=new ProgressDialog(AddNewStudent.this);
                progressDialog.setTitle("Adding New Student");
                progressDialog.setMessage("Please wait ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if(name.isEmpty() || rollNo.isEmpty() || batch.isEmpty() || semester.isEmpty()
                        || department.isEmpty() || program.isEmpty()){
                    Toast.makeText(getApplicationContext(),"invalid info",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }else {
                    StudentProfileInfo spi=new StudentProfileInfo();
                    spi.studentName=name;
                    spi.studentRollNo=rollNo;
                    spi.studentDegreeProgram=program;
                    spi.studentDepartment=department;
                    spi.studentBatch=batch;
                    spi.studentSemester=semester;

                    mRef.child(rollNo).setValue(spi);

                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(),"New Student Added Successfully.",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(),AddNewStudent.class));
                    finish();
                }

            }
        });

    }
}
