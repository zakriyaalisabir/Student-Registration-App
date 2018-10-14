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

public class RemoveCourseFromDB extends AppCompatActivity {

    private Button btnR,btnLC;
    private Spinner spSC,spSS;

    private String course;
    private String semester;

    private DatabaseReference mRef;

    private ProgressDialog progressDialog;

    private List<String> coursesNamesList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_course_from_db);

        mRef= FirebaseDatabase.getInstance().getReference("courses");

        btnLC=(Button)findViewById(R.id.btnLoadCoursesForDel);
        btnR=(Button)findViewById(R.id.btnRemoveCourseFromDb);
        spSC=(Spinner)findViewById(R.id.spSelectCourseToDel);
        spSS=(Spinner) findViewById(R.id.spSelectSemesterToDelCourse);

        progressDialog=new ProgressDialog(RemoveCourseFromDB.this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Please wait ....");
        progressDialog.setCancelable(false);

        coursesNamesList=new ArrayList<String>();
        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,coursesNamesList);

        btnLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                coursesNamesList.clear();
                arrayAdapter.clear();

                semester=spSS.getSelectedItem().toString();
                if(semester.isEmpty() || semester.equals("Select Semester")){
                    Toast.makeText(getApplicationContext(),"Please Choose Semester (Invalid Semester)",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }else {
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                    Course course=ds.getValue(Course.class);
                                    if(semester.equals(course.semester)){
                                        coursesNamesList.add(course.name);
                                    }
                                }
                                progressDialog.dismiss();
                                spSC.setAdapter(arrayAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });
                }

            }
        });

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course=spSC.getSelectedItem().toString();

                progressDialog.setTitle("Removing Course");
                progressDialog.show();

                if(course.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Invalid Course Name",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                mRef.child(course).removeValue();
            }
        });

    }
}
