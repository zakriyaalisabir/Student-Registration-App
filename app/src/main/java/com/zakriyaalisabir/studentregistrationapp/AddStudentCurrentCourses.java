package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddStudentCurrentCourses extends AppCompatActivity {

    private Button btnAC;
    private EditText etSRN;
    private Spinner spSC;

    private DatabaseReference mRef,mRefCourseHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_current_courses);

        mRef=FirebaseDatabase.getInstance().getReference("courses");
        mRefCourseHistory=FirebaseDatabase.getInstance().getReference();

        btnAC=(Button)findViewById(R.id.btnAddStudentCurrentCourseByAdmin);
        etSRN=(EditText)findViewById(R.id.etStudentIdToAddCurrentCourse);
        spSC=(Spinner)findViewById(R.id.spinnerToSelectCurrentCourseOfStudent);

        final ProgressDialog progressDialog=new ProgressDialog(AddStudentCurrentCourses.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Courses From Server");
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        final List<String> spinnerArrayList=new ArrayList<String>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Course course=ds.getValue(Course.class);
                    spinnerArrayList.add(course.name);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                        AddStudentCurrentCourses.this,
                        android.R.layout.simple_spinner_item,
                        spinnerArrayList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSC.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Adding Course For A Student");
                progressDialog.show();

                String id=etSRN.getText().toString();
                if(id.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter a alid student id ",Toast.LENGTH_LONG).show();
                    return;
                }

                String courseName=spSC.getSelectedItem().toString();

                if(courseName.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Course name is invalid ",Toast.LENGTH_LONG).show();
                    return;
                }

                mRefCourseHistory.child("currentCourses").child(id).child(courseName).setValue(true);

                Toast.makeText(getApplicationContext(),"Course Successfully added",Toast.LENGTH_LONG).show();

//                etSRN.setText("");

                progressDialog.dismiss();
            }
        });

    }
}
