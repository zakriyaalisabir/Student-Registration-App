package com.zakriyaalisabir.studentregistrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentMenu extends AppCompatActivity {

    private Button btnMCC,btnMOC,btnRNC,btnRAC;

    private String scannedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);


        scannedResult=getIntent().getStringExtra("scannedResult");

        btnMCC=(Button)findViewById(R.id.btnMyCurrentCourses);
        btnMOC=(Button)findViewById(R.id.btnMyCourses);
        btnRNC=(Button)findViewById(R.id.btnStudentAddNewCourse);
        btnRAC=(Button)findViewById(R.id.btnStudentRemoveACourse);

        btnMCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),MyCurrentCourses.class);
                intent.putExtra("scannedResult",scannedResult);
                startActivity(intent);
            }
        });
        btnMOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),MyCoursesHistory.class);
//                intent.putExtra("scannedResult",scannedResult);
//                startActivity(intent);
            }
        });
        btnRNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddStudentCurrentCourses.class);
                intent.putExtra("pending","pending");
                intent.putExtra("scannedResult",scannedResult);
                startActivity(intent);
            }
        });
        btnRAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DeleteStudentCurrentCourses.class);
                intent.putExtra("scannedResult",scannedResult);
                startActivity(intent);
            }
        });

    }
}
