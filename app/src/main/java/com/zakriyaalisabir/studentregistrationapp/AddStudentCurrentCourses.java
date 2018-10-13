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
    private Spinner spSC,spSS;

    private String pending;
    private String sid;

    private int canRegister;
    private String isRegistered;
    private String preReq;

    private DatabaseReference mRef,mReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_current_courses);

        canRegister=0;
        isRegistered="false";

        mRef=FirebaseDatabase.getInstance().getReference("courses");
        mReff=FirebaseDatabase.getInstance().getReference();

        btnAC=(Button)findViewById(R.id.btnAddStudentCurrentCourseByAdmin);
        etSRN=(EditText)findViewById(R.id.etStudentIdToAddCurrentCourse);
        spSC=(Spinner)findViewById(R.id.spinnerToSelectCurrentCourseOfStudent);
        spSS=(Spinner)findViewById(R.id.spSelectSemester);

        if(getIntent().hasExtra("scannedResult")){
            sid=getIntent().getStringExtra("scannedResult").toString();
            etSRN.setText(sid);
        }

        final ProgressDialog progressDialog=new ProgressDialog(AddStudentCurrentCourses.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Courses From Server");
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        final List<String> spinnerArrayList=new ArrayList<String>();
        final List<Course> coursesList=new ArrayList<Course>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Course course=ds.getValue(Course.class);
                    coursesList.add(course);
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
                    Toast.makeText(getApplicationContext(),"Enter a valid student id ",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }

                String courseName=spSC.getSelectedItem().toString();
                String semester=spSS.getSelectedItem().toString();

                if(courseName.isEmpty() || semester.equals("Select Semester")){
                    Toast.makeText(getApplicationContext(),"Invalid info",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }

                for(int i=0;i<coursesList.size();i++ ){
                    if (!coursesList.get(i).name.equals(courseName)){
                        if(canRegister==1){
                            break;
                        }
                        continue;
                    }
                    preReq=coursesList.get(i).preReq;

                    if(preReq.equals("")){
                        canRegister=1;
                        break;
                    }

                    mReff.child("currentCourses").child(id).child(semester).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(preReq)){
//                                Toast.makeText(getApplicationContext(),"datasnapshot for preReq = "+dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                                isRegistered=dataSnapshot.child(preReq).getValue(String.class);
                                if(isRegistered.equals("registered")){
                                    canRegister=1;
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }


                if(canRegister==0){
                    Toast.makeText(getApplicationContext(),"You cannot register this course bcs preReq="+preReq+" is not registered.",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }


                if(getIntent().hasExtra("pending")){
                    pending=getIntent().getStringExtra("pending").toString();
                    mReff.child("currentCourses").child(id).child(semester).child(courseName).setValue(pending);
                    mReff.child("registrationRequests").child(id).child(semester).child(courseName).setValue(pending);
                }else {
                    mReff.child("currentCourses").child(id).child(semester).child(courseName).setValue("registered");
                }


                Toast.makeText(getApplicationContext(),"Course Successfully added",Toast.LENGTH_LONG).show();

//                etSRN.setText("");

                progressDialog.dismiss();
            }
        });

    }
}
