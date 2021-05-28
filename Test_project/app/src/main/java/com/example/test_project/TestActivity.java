package com.example.test_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintsChangedListener;

import com.google.android.material.badge.BadgeUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestActivity extends AppCompatActivity {



    EditText question,ans[];
    CheckBox ch_q[],ch_a[];
    String name,questions,variants="",answers="";
    int counter=0,counter_ans=0;
     int test_count=0,time=0;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_test_activity);
        db = new DatabaseHelper(getApplicationContext());
            Bundle arguments = getIntent().getExtras();
            name = arguments.get("name").toString();

                if(arguments.get("timer").toString()!=null) {
                    time = Integer.parseInt(arguments.get("timer").toString());
                }


        if(db.count_tests()>0){
            test_count=db.get_max()+1;
        }
        question = findViewById(R.id.NameOfTheTest);
        ch_q=new CheckBox[4];
        ch_a=new CheckBox[4];
        ans = new EditText[4];
        for(int i=1;i<5;i++){
            int IdAns=this.getResources().getIdentifier("editTextTextPersonName"+i,"id",this.getPackageName());
            int IdCheckA=this.getResources().getIdentifier("checkBox"+i,"id",this.getPackageName());
            int IdCheckQ=this.getResources().getIdentifier("checkBox1"+i,"id",this.getPackageName());
            ch_q[i-1]=findViewById(IdCheckQ);
            ch_a[i-1]=findViewById(IdCheckA);
            ans[i-1]=findViewById(IdAns);
            int finalI = i;
            ch_q[i-1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        ans[finalI-1].setVisibility(View.VISIBLE);
                        ch_a[finalI-1].setVisibility(View.VISIBLE);
                    }else{
                        ans[finalI-1].setVisibility(View.INVISIBLE);
                        ch_a[finalI-1].setVisibility(View.INVISIBLE);
                    }
                }
            });

        }


    }

    public void onClicking(View view) {

     for(int i=1;i<5;i++){
         if(ch_q[i-1].isChecked() && !ans[i-1].equals("") && ans[i-1].isCursorVisible()){
             counter++;
             variants+="&" +ans[i-1].getText().toString() ;
         }
         if(ch_q[i-1].isChecked()&& ch_a[i-1].isChecked()&& !ans[i-1].equals("") && ans[i-1].isCursorVisible()){
             counter_ans++;
             answers+="&" +ans[i-1].getText().toString() ;
         }
     }
        questions=question.getText().toString();

        if(answers.equals("")){
            Toast.makeText(getApplicationContext(),"Choose the right answer",Toast.LENGTH_SHORT).show();
        }else if(counter<1) Toast.makeText(this, "Chose at least 2 answers", Toast.LENGTH_SHORT).show();
        else if(counter_ans>counter){
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
        else{
            db.addData(test_count,name,questions,variants,answers,time);
            question.setText("");
            for(int i=0;i<2;i++){
                ans[i].setText("");
                ch_q[i].setChecked(true);
                ch_a[i].setChecked(false);
            } for(int i=2;i<4;i++){
                ans[i].setText("");
                ch_q[i].setChecked(false);
                ch_a[i].setChecked(false);
            }

            variants="";
            answers="";
            Toast.makeText(this, "Успешно создан вопрос №"+db.count(test_count), Toast.LENGTH_SHORT).show();
        }



    }

    public void onCreateTest(View view) {
    Intent i = new Intent(this,MainActivity.class);
    startActivity(i);


    }




}