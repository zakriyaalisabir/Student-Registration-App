package com.qr.studentregistrationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentregistrationapp.R;

import java.util.ArrayList;

public class MyCurrentCourses extends AppCompatActivity {

    private TextView tvM;
    private DatabaseReference mRef;

    private String scannedResult;

    private ArrayList<String> arrayList;

    private Spinner sp;

    private Button btnLC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_current_courses);

        scannedResult=getIntent().getStringExtra("scannedResult");

        mRef= FirebaseDatabase.getInstance().getReference("currentCourses");

        tvM=(TextView)findViewById(R.id.tvStudentCurrentCourses);
        sp=(Spinner)findViewById(R.id.spSelectSemester);
        btnLC=(Button)findViewById(R.id.btnLoadCourses);

        tvM.setMovementMethod(new ScrollingMovementMethod());

        arrayList=new ArrayList<String>();

        final ProgressDialog progressDialog=new ProgressDialog(MyCurrentCourses.this);
        progressDialog.setTitle("Fetching Courses");
        progressDialog.setMessage("Please wait ...");


        btnLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String sem=sp.getSelectedItem().toString();
                if(sem.equals("Select Semester")){
                    Toast.makeText(getApplicationContext(),"Select semester ",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    mRef.child(scannedResult).child(sem).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            tvM.setText("");
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String key=ds.getKey().toString();
                                String value=ds.getValue().toString();

                                String keyValue=key+" => "+value;

                                arrayList.add(keyValue);

                                tvM.append("*"+keyValue+"\n");

                            }
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Courses Fetched Successfully from Server",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),"Error Connecting to Server",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });


    }
}
