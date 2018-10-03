package com.zakriyaalisabir.studentregistrationapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MyDialogClass extends AppCompatDialogFragment {

    public EditText etND;
    private MyDialogClassListner myDialogClassListner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.my_dialogue_box,null);

        etND=view.findViewById(R.id.etUpdateNewData);

        builder.setView(view)
                .setTitle("Enter New Data : ")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String string=etND.getText().toString();
                        if(string.isEmpty()){
                            return;
                        }else {
                            myDialogClassListner.applyTexts(string);
                        }
                    }
                });



        return  builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            myDialogClassListner=(MyDialogClassListner)context;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public interface MyDialogClassListner{
        void applyTexts(String newData);

    }
}
