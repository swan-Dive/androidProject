package com.example.test_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class final_activity extends AppCompatActivity {
    int score,quantity;
    LinearLayout ll;
    TextView tv,newTV;
    boolean[] booleans;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        tv=findViewById(R.id.textView4);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            booleans=bundle.getBooleanArray("TF_answers");
            score = bundle.getInt("result");
            quantity = bundle.getInt("quantity");
            String text = "Ваш результат: "+score+"/"+quantity;
            tv.setText(text);
            ll=findViewById(R.id.find_layout);
            for(int i=0;i<quantity;i++){
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lparams.gravity=1;
                 newTV=new TextView(this);
                 newTV.setTextSize(20);
                newTV.setLayoutParams(lparams);
                int j=i+1;
                newTV.setText("Вопрос №"+j);
                if(booleans==null){
                    Toast.makeText(this, "popa)", Toast.LENGTH_SHORT).show();
                }else{
                    if(booleans[i]) newTV.setTextColor(GREEN);
                    else newTV.setTextColor(RED);

                }
                ll.addView(newTV);

            }

        }else{
            tv.setText("Что-то пошло не так");
        }
    }

    public void GoBack(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        
    }
}