    package com.example.test_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class edit_activity extends AppCompatActivity {

    EditText question,ans[],timer;
    CheckBox ch_q[],ch_a[];
    DatabaseHelper db;
    int position=-1,table_pos,count,counter=0,counter_ans=0,protector,time=0;
    String variants="";
    String answers="";
    edit_adapter adapter;
    ListView lv;
    TextView tv;
    String TestName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db= new DatabaseHelper(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("pos");
        }

        List<Integer> table_list=db.countTestNums();
        table_pos=table_list.get(position);
        timer = findViewById(R.id.tv_timer);
        time=db.getTimer(table_pos);
        timer.setText(time+"");
        List<SingleTest>question_answers = new ArrayList<>();
        question_answers=db.getQuestion_ans(table_pos);
        edit_class[] ed= new edit_class[db.count(table_pos)];
        TestName=db.get_test_name(table_pos);
        tv=findViewById(R.id.tv_test_name2);
        tv.setText(TestName);
        for(int j=0;j<db.count(table_pos);j++){
            ed[j]=(new edit_class(question_answers.get(j).name,question_answers.get(j).variants));
        }

        lv=findViewById(R.id.EditListView);
       if(ed!=null){

            adapter = new edit_adapter(this, ed);
            lv.setAdapter(adapter);

        }
        count = db.count(table_pos);






    }


    public void Edit(View view) {

        db.deleteChosenData(table_pos);
        protector=0;
        lv=findViewById(R.id.EditListView);
        for(int i=0;i<lv.getCount();i++){
            View v = lv.getChildAt(i);
            EditText ed_question=v.findViewById(R.id.edit_name);
            String question = ed_question.getText().toString();
            ch_q=new CheckBox[4];
            ch_a=new CheckBox[4];
            ans = new EditText[4];
            for(int j=1;j<5;j++){
                int IdAns=this.getResources().getIdentifier("edit_name"+j,"id",this.getPackageName());
                int IdCheckA=this.getResources().getIdentifier("checkBox"+j,"id",this.getPackageName());
                int IdCheckQ=this.getResources().getIdentifier("checkBox1"+j,"id",this.getPackageName());
                ch_q[j-1]=v.findViewById(IdCheckQ);
                ch_a[j-1]=v.findViewById(IdCheckA);
                ans[j-1]=v.findViewById(IdAns);
                if(ch_q[j-1].isChecked()){
                    counter++;
                    variants+="&" +ans[j-1].getText().toString() ;
                }
                if(ch_q[j-1].isChecked()&& ch_a[j-1].isChecked()){
                    counter_ans++;
                    answers+="&" +ans[j-1].getText().toString() ;
                }


            }


            if(counter<1) {
                variants="";
                answers="";
                Toast.makeText(this, "Chose at least 2 answers", Toast.LENGTH_SHORT).show();
            }
            else if(answers.equals("")){
                variants="";
                answers="";
                Toast.makeText(getApplicationContext(),"Choose the right answer",Toast.LENGTH_SHORT).show();
            }
            else if(counter_ans>counter){
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
                variants="";
                answers="";
            }
            else{
                timer = findViewById(R.id.tv_timer);

                time = Integer.parseInt(timer.getText().toString());
                db.addData(table_pos,tv.getText().toString(),question,variants,answers,time);
                variants="";
                answers="";
                protector++;
            }
            counter_ans=0;
            counter=0;


        }
        if(protector==count){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }




    }
}