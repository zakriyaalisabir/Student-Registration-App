package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewRegistrationRequests extends AppCompatActivity {

    private ListView lv;
    private ArrayList <String> requests;

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registration_requests);

        requests=new ArrayList<String>();

        mRef= FirebaseDatabase.getInstance().getReference("registrationRequests").child("CIIT");

        lv=(ListView)findViewById(R.id.lvRequests);

        final ProgressDialog progressDialog=new ProgressDialog(ViewRegistrationRequests.this);
        progressDialog.setTitle("Fetching Requests");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Log.d("ds = ", ""+ds);
                    String req=ds.getValue(String.class);
                    requests.add(req);
                }
                Log.d("requestArray ==> ", ""+requests);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });

        if (requests.size()<1){
            Toast.makeText(getApplicationContext(),"no requests yet",Toast.LENGTH_LONG).show();
            return;
        }

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.my_list_view_layout,R.id.tvListView,requests);

        lv.setAdapter(arrayAdapter);

    }
}
