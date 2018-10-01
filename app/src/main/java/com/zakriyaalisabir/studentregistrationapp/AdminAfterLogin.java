package com.zakriyaalisabir.studentregistrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class AdminAfterLogin extends AppCompatActivity {

    private ImageButton btnANS,btnUS,btnACOS,btnANCTDB,btnASR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_after_login);

        Toast.makeText(getApplicationContext(),"Successfull Login",Toast.LENGTH_LONG).show();

        btnANS=(ImageButton)findViewById(R.id.btnAddStudent);
        btnUS=(ImageButton)findViewById(R.id.btnUpdateStudent);
        btnACOS=(ImageButton)findViewById(R.id.btnAddStudentCurrentCourses);
        btnANCTDB=(ImageButton)findViewById(R.id.btnAddNewCourses);
        btnASR=(ImageButton)findViewById(R.id.btnAddResultForAStudent);

        btnANS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddNewStudent.class));
            }
        });

        btnUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UpdateStudentInfo.class));
            }
        });

        btnACOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddStudentCurrentCourses.class));
            }
        });

        btnANCTDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddNewCoursesToDB.class));
            }
        });

        btnASR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddStudentResult.class));
            }
        });

    }
}
