package com.qr.studentregistrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentregistrationapp.R;

public class AfterListviewRequestClick extends AppCompatActivity {

    private TextView tv;
    private Button btnA,btnR;

    private String request;
    private String reqId;

    private DatabaseReference mRef1,mRef3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_listview_request_click);

        mRef1= FirebaseDatabase.getInstance().getReference("currentCourses");
        mRef3= FirebaseDatabase.getInstance().getReference("registrationRequests");

        tv=(TextView)findViewById(R.id.tvRequestFromListview);
        btnA=(Button)findViewById(R.id.btnAcceptRequest);
        btnR=(Button)findViewById(R.id.btnRejectRequest);

        if(!getIntent().hasExtra("request")){
            finish();
            return;
        }

        request=getIntent().getStringExtra("request");
        reqId=getIntent().getStringExtra("requestId");

        final String sid=request.substring(0,21);
        final String sem=request.substring(22,23);
        final String course=request.substring(24);

        tv.setText("Student id : "+sid+"\nSemester : "+sem+"\nCourse : "+course+"\nWants to enroll (Accept or Reject) ?");

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef1.child(sid).child(sem).child(course).setValue("registered");
                mRef3.child(sid).child(sem).child(course).removeValue();
                Intent intent=new Intent(getApplicationContext(),ViewRegistrationRequests.class);
                intent.putExtra("reqId",reqId);
                startActivity(intent);
                finish();
            }
        });

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef1.child(sid).child(sem).child(course).setValue("rejected");
                mRef3.child(sid).child(sem).child(course).removeValue();
                Intent intent=new Intent(getApplicationContext(),ViewRegistrationRequests.class);
                intent.putExtra("reqId",reqId);
                startActivity(intent);
                finish();
            }
        });



    }
}
