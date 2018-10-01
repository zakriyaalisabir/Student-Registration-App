package com.zakriyaalisabir.studentregistrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentMenu extends AppCompatActivity {

    private Button btnMCC,btnMOC,btnMCR,btnMOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        btnMCC=(Button)findViewById(R.id.btnMyCurrentCourses);
        btnMOC=(Button)findViewById(R.id.btnMyCourses);
        btnMCR=(Button)findViewById(R.id.btnMyCurrentResults);
        btnMOR=(Button)findViewById(R.id.btnMyOverallResult);

        btnMCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyCurrentCourses.class));
            }
        });
        btnMOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyCoursesHistory.class));
            }
        });
        btnMCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyCurrentResults.class));
            }
        });
        btnMOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyOverallResult.class));
            }
        });

    }
}
