package com.example.test_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class examination_activity extends AppCompatActivity {

    DatabaseHelper db;
    TextView t;
    int pos, score,table_pos;
    test_adapter adapter;
    ListView ll;
    int time=0,timer_counter=0;
    CountDownTimer c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_activity);
        db = new DatabaseHelper(getApplicationContext());

        pos = -1;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pos = bundle.getInt("position11");
        }
        List<Integer> table_list=db.countTestNums();
        table_pos=table_list.get(pos);
        t = findViewById(R.id.tv_test_name);
        String name = db.get_test_name(table_pos);
        t.setText(name);
        List<SingleTest> list = new ArrayList<>();
        if (pos != -1) {
            list = db.getQuestion_ans(table_pos);
        }

        SingleTest[] arr = new SingleTest[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }


        adapter = new test_adapter(this, arr);
        ListView lv =  findViewById(R.id.listView);
        lv.setAdapter(adapter);
        time =db.getTimer(table_pos)*1000;
        TextView tv = findViewById(R.id.tv_timer);
        if(time!=0){
           c = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    tv.setText("Времени осталось: " +millisUntilFinished/1000);

                    if((millisUntilFinished/1000)>=10){

                        tv.setTextColor(GREEN);

                    }else{
                        tv.setTextColor(RED);
                    }
                    timer_counter++;

                }

                @Override
                public void onFinish() {
                    View v = findViewById(android.R.id.content).getRootView();
                    examine(v);
                    finish();
                }

            }.start();

        }
    }

    public void examine(View view) {
        ll = findViewById(R.id.listView);
        int count = db.count(table_pos);
        SingleTest[] arr = new SingleTest[count];
        boolean[] booleans = new boolean[count];
        Arrays.fill(booleans,false);
        score = 0;
        List<String> answers_list = db.get_answers(table_pos);
        String[] answers = new String[answers_list.size()];
        for(int i=0;i<answers_list.size();i++){
            answers[i]=answers_list.get(i);
        }

        for (int i = 0; i < count ; i++) {

            arr[i] = adapter.getItem(i);
            String[] answers1=answers[i].split("&");
            String answer="";
            for(int k=0;k<answers1.length;k++){
                answer+=answers1[k];
            }
            String[] variants = arr[i].variants.split("&");
            View v = ll.getChildAt(i);
            String chosen_answers="";
            for (int j = 1; j < 5; j++) {

                int id = this.getResources().getIdentifier("rb" + j, "id", this.getPackageName());
                CheckBox cb = v.findViewById(id);

                if (cb.isChecked()) {

                    chosen_answers +=variants[j];
                }


            }

            if (answer.equals(chosen_answers)) {
                booleans[i]=true;
                score++;
            }
            chosen_answers="";
        }
        Intent i = new Intent(this,final_activity.class);
        i.putExtra("TF_answers",booleans);
        i.putExtra("result",score);
        i.putExtra("quantity",count);
        startActivity(i);
        if(time!=0){
            c.cancel();
        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(time!=0){
            c.cancel();
        }
    }
}