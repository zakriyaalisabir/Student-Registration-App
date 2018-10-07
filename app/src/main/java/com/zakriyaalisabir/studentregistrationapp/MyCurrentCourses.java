package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCurrentCourses extends AppCompatActivity {

    private TextView tvM;
    private DatabaseReference mRef;

    private String scannedResult;

    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_current_courses);

        scannedResult=getIntent().getStringExtra("scannedResult");

        mRef= FirebaseDatabase.getInstance().getReference("currentCourses");

        tvM=(TextView)findViewById(R.id.tvStudentCurrentCourses);

        tvM.setMovementMethod(new ScrollingMovementMethod());

        arrayList=new ArrayList<String>();

        final ProgressDialog progressDialog=new ProgressDialog(MyCurrentCourses.this);
        progressDialog.setTitle("Fetching Courses");
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        mRef.child(scannedResult).addListenerForSingleValueEvent(new ValueEventListener() {
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
