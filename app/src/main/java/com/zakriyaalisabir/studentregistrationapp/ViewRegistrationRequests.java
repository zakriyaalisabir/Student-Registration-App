package com.zakriyaalisabir.studentregistrationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewRegistrationRequests extends AppCompatActivity {

    private ListView lv;
    private List<String> requests;

    private DatabaseReference mRef;

    private int delPosition;

    private ArrayAdapter<String> arrayAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registration_requests);

        requests=new ArrayList<String>();

        arrayAdapter=new ArrayAdapter<String>(this,R.layout.my_list_view_layout,requests);

        lv=(ListView)findViewById(R.id.lvRequests);

        if(getIntent().hasExtra("reqId")){
            requests.clear();
            arrayAdapter.clear();
            arrayAdapter.notifyDataSetChanged();
            lv.invalidateViews();
        }

        mRef= FirebaseDatabase.getInstance().getReference("registrationRequests").child("CIIT");


        progressDialog=new ProgressDialog(ViewRegistrationRequests.this);
        progressDialog.setTitle("Fetching Requests");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        fetchRequests();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),AfterListviewRequestClick.class);
                String request=((TextView)view).getText().toString();
                intent.putExtra("request",request);
                intent.putExtra("requestId",position);
                delPosition=position;
                startActivity(intent);
                finish();
            }
        });


        lv.invalidateViews();
        arrayAdapter.notifyDataSetChanged();

    }

    private void fetchRequests() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    String sid=ds.getKey().toString();
                    for(DataSnapshot ds1:ds.getChildren()){
                        String city=ds1.getKey().toString();
                        for(DataSnapshot ds2:ds1.getChildren()){
                            String sem=ds2.getKey().toString();
                            for(DataSnapshot ds3:ds2.getChildren()){
                                String course=ds3.getKey().toString();
                                String req="CIIT/"+sid+"/"+city+"#"+sem+"#"+course;
//                                Toast.makeText(getApplicationContext(),"req = "+req,Toast.LENGTH_LONG).show();
                                int index=requests.size();
                                requests.add(index,req);
                                Log.d("req = ", ""+req);
                            }
                        }
                    }
                }
                if (requests.size()<1){
                    Toast.makeText(getApplicationContext(),"no requests yet",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                    return;
                }

                lv.setAdapter(arrayAdapter);
//                Toast.makeText(getApplicationContext(),"Requests fetched successfully",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });
    }
}
