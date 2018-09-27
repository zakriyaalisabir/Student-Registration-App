package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {

    private DatabaseReference myRef;
    private TextView tvSN,tvSRN,tvSS,tvSB,tvSDP,tvSD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        myRef = FirebaseDatabase.getInstance().getReference("users");

        tvSN=(TextView)findViewById(R.id.tvStudentName);
        tvSRN=(TextView)findViewById(R.id.tvStudentRollNo);
        tvSS=(TextView)findViewById(R.id.tvStudentSemester);
        tvSB=(TextView)findViewById(R.id.tvStudentBatch);
        tvSDP=(TextView)findViewById(R.id.tvStudentDegreeProgram);
        tvSD=(TextView)findViewById(R.id.tvStudentDepartment);

        final ProgressDialog progressDialog=new ProgressDialog(StudentProfile.this  );
        progressDialog.setTitle("Checking Database");
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        final String scannedResult=getIntent().getStringExtra("scannedResult");

        myRef.child(scannedResult).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    Toast.makeText(getApplicationContext(),scannedResult.toString(),Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    },2000);
                    StudentProfileInfo spi=dataSnapshot.getValue(StudentProfileInfo.class);
                    tvSN.setText("Name : "+spi.studentName.toString());
                    tvSRN.setText("Roll No : "+spi.studentRollNo.toString());
                    tvSS.setText("Semester : "+spi.studentSemester.toString());
                    tvSB.setText("Batch : "+spi.studentBatch.toString());
                    tvSDP.setText("Degree : "+spi.studentDegreeProgram.toString());
                    tvSD.setText("Department : "+spi.studentDepartment.toString());
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"irrelevant QR code",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),QRScanner.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),QRScanner.class));
                finish();
            }
        });

    }
}
