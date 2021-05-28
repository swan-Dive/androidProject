package com.example.test_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Create_single_test extends AppCompatActivity {
    EditText et;
    String name;
    int time=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_single_test);
         DatabaseHelper db= new DatabaseHelper(getApplicationContext());
    }

    public void onClick(View view) {
        et=findViewById(R.id.NameOfTheTest);
        name=et.getText().toString();

        Intent i = new Intent(this,TestActivity.class);
        AlertDialog.Builder adb = new AlertDialog.Builder(Create_single_test.this);


        adb.setMessage("Поставить таймер? " );

        adb.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i.putExtra("name",name);
                i.putExtra("timer",time);
                startActivity(i);
                finish();

            }
        });
        adb.setPositiveButton("Да", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                final Dialog dialogue = new Dialog(Create_single_test.this);
                dialogue.setContentView(R.layout.dialog);
                dialogue.setTitle("Title...");
                EditText ed =(EditText) dialogue.findViewById(R.id.dialog_et);
                Button bt = (Button) dialogue.findViewById(R.id.button8);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = ed.getText().toString();
                        i.putExtra("name",name);
                        i.putExtra("timer",s);
                        startActivity(i);
                        finish();

                    }
                });
                dialogue.show();


            }
        });
        adb.show();


    }
    }