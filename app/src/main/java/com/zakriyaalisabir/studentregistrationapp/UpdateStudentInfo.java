package com.zakriyaalisabir.studentregistrationapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateStudentInfo extends AppCompatActivity implements MyDialogClass.MyDialogClassListner {

    private Spinner spinner;
    private Button btnU;
    private String selectedOption,newDataValue;
    private EditText etSRN;

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_info);

        btnU=(Button)findViewById(R.id.btnUpdateStudentInfo);
        spinner=(Spinner)findViewById(R.id.spinnerUpdateStudentInfo);
        etSRN=(EditText)findViewById(R.id.etStudentIdToUpdate);


        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=etSRN.getText().toString();
                if(id.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter a valid id first !!!",Toast.LENGTH_LONG).show();
                    return;
                }

                mRef= FirebaseDatabase.getInstance().getReference("users").child(id);
                openDialog();
            }
        });



    }

    private void openDialog() {
        MyDialogClass mdc=new MyDialogClass();
        mdc.show(getSupportFragmentManager(),"Update Data");
    }

    @Override
    public void applyTexts(String newData) {
        newDataValue=newData;
//        Toast.makeText(getApplicationContext(),"new Data = "+newDataValue,Toast.LENGTH_LONG).show();

        selectedOption =spinner.getSelectedItem().toString();
//        Toast.makeText(getApplicationContext(),"spinner value = "+selectedOption,Toast.LENGTH_LONG).show();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    StudentProfileInfo spi=dataSnapshot.getValue(StudentProfileInfo.class);
                    if (selectedOption.equals("Name")){
//                        spi.studentName=newDataValue;
                        mRef.child("studentName").setValue(newDataValue);
                    }else  if(selectedOption.equals("Department")){
//                        spi.studentDepartment=newDataValue;
                        mRef.child("studentDepartment").setValue(newDataValue);
                    }else  if(selectedOption.equals("Semester")){
//                        spi.studentSemester=newDataValue;
                        mRef.child("studentSemester").setValue(newDataValue);
                    }else  if(selectedOption.equals("Batch")){
//                        spi.studentBatch=newDataValue;
                        mRef.child("studentBatch").setValue(newDataValue);
                    }else  if(selectedOption.equals("Program")){
//                        spi.studentDegreeProgram=newDataValue;
                        mRef.child("studentDegreeProgram").setValue(newDataValue);
                    }
//
//                    mRef.setValue(spi);
                    Toast.makeText(getApplicationContext(),"Updated New Info",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"Failed To Read database",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed To Fetch database",Toast.LENGTH_LONG).show();
            }
        });
    }
}
