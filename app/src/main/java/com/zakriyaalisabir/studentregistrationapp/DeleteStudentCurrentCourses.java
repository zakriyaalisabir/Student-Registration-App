package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteStudentCurrentCourses extends AppCompatActivity {
    private Button btnAC,btnLC;
    private EditText etSRN;
    private Spinner spSC,spSS;

    private TextView tvN;

    private String id,sem;

    private DatabaseReference mRef,mRefCourseHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student_current_courses);

        mRef= FirebaseDatabase.getInstance().getReference("currentCourses");
        mRefCourseHistory=FirebaseDatabase.getInstance().getReference();

        btnAC=(Button)findViewById(R.id.btnDeleteStudentCurrentCourseByAdmin);
        btnLC=(Button)findViewById(R.id.btnLoadStudentCurrentCourses);
        etSRN=(EditText)findViewById(R.id.etStudentIdToDeleteCurrentCourse);
        spSC=(Spinner)findViewById(R.id.spinnerToDeleteCurrentCourseOfStudent);
        spSS=(Spinner)findViewById(R.id.spSelectSemester);
        tvN=(TextView)findViewById(R.id.tvNote);

        btnAC.setVisibility(View.INVISIBLE);
        spSC.setVisibility(View.INVISIBLE);
        tvN.setVisibility(View.INVISIBLE);


        if(getIntent().hasExtra("scannedResult")){
            id=getIntent().getStringExtra("scannedResult").toString();
            etSRN.setText(id);
        }

        final ProgressDialog progressDialog=new ProgressDialog(DeleteStudentCurrentCourses.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Student's Courses From Database");
        progressDialog.setMessage("Please wait ...");

        final List<String> spinnerArrayList=new ArrayList<String>();

        btnLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                id=etSRN.getText().toString();
                sem=spSS.getSelectedItem().toString();
                if(id.isEmpty() || sem.equals("Select Semester")){
                    Toast.makeText(getApplicationContext(),"Invalid Info ",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                mRef.child(id).child(sem).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            String cName=ds.getKey().toString();
                            spinnerArrayList.add(cName);
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                                DeleteStudentCurrentCourses.this,
                                android.R.layout.simple_spinner_item,
                                spinnerArrayList);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spSC.setAdapter(adapter);

                        tvN.setVisibility(View.VISIBLE);
                        spSC.setVisibility(View.VISIBLE);
                        btnAC.setVisibility(View.VISIBLE);

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
            }
        });

        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                progressDialog.setTitle("Deleting A Course For A Student");
                progressDialog.show();

                String courseName=spSC.getSelectedItem().toString();

                if(courseName.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Course name is invalid ",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }

                mRefCourseHistory.child("currentCourses").child(id).child(sem).child(courseName).removeValue();
                mRefCourseHistory.child("results").child(id).child(sem).child(courseName).removeValue();

                Toast.makeText(getApplicationContext(),"Course Successfully Removed",Toast.LENGTH_LONG).show();

                progressDialog.dismiss();

                startActivity(new Intent(getApplicationContext(),DeleteStudentCurrentCourses.class));
                finish();
            }
        });

    }
}
