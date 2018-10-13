package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
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

public class AddStudentResult extends AppCompatActivity {

    private Button btnLC,btnAM;
    private Spinner spSC,spSS;
    private EditText etSI,etCM;
    private TextView tvN2;

    private String sId,sem;

    private DatabaseReference mRef,mRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_result);

        mRef= FirebaseDatabase.getInstance().getReference("currentCourses");
        mRef2=FirebaseDatabase.getInstance().getReference("results");

        btnAM=(Button)findViewById(R.id.btnUpdateMarks);
        btnLC=(Button)findViewById(R.id.btnLoadStudentCurrentCourses);
        spSC=(Spinner)findViewById(R.id.spinnerToSelectCurrentCourseOfStudentToAddMarks);
        spSS=(Spinner)findViewById(R.id.spSelectSemester);
        etCM=(EditText) findViewById(R.id.etSubjectMarks);
        etSI=(EditText) findViewById(R.id.etStudentIdToAdResult);
        tvN2=(TextView)findViewById(R.id.tvNote2);

        spSC.setVisibility(View.INVISIBLE);
        btnAM.setVisibility(View.INVISIBLE);
        tvN2.setVisibility(View.INVISIBLE);
        etCM.setVisibility(View.GONE);

        final ProgressDialog progressDialog=new ProgressDialog(AddStudentResult.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Students Data");
        progressDialog.setMessage("Please wait...");

        final List<String> spinnerArrayList=new ArrayList<String>();

        btnLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sId=etSI.getText().toString();
                sem=spSS.getSelectedItem().toString();
                progressDialog.show();
                if(sId.isEmpty() || sem.equals("Select Semester")){
                    Toast.makeText(getApplicationContext(),"Invalid Id",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }

                mRef.child(sId).child(sem).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            String cName=ds.getKey().toString();
                            spinnerArrayList.add(cName);
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                                AddStudentResult.this,
                                android.R.layout.simple_spinner_item,
                                spinnerArrayList);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spSC.setAdapter(adapter);

                        tvN2.setVisibility(View.VISIBLE);
                        spSC.setVisibility(View.VISIBLE);
                        btnAM.setVisibility(View.VISIBLE);
                        etCM.setVisibility(View.VISIBLE);

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });

            }
        });

        btnAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Updating Marks");
                progressDialog.show();
                String marks=etCM.getText().toString();
                String cc=spSC.getSelectedItem().toString();
                if(marks.isEmpty() || cc.isEmpty()){
                    Toast.makeText(getApplicationContext(),"invalid subject or marks",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }

                mRef2.child(sId).child(sem).child(cc).setValue(marks);

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Marks Succesfully Updated",Toast.LENGTH_LONG).show();

            }
        });

    }
}
