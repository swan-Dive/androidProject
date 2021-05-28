package com.example.test_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class edit_adapter extends ArrayAdapter<edit_class> {
    private edit_class[] ed;
    public edit_adapter( Context context, edit_class[] objects) {
        super(context, R.layout.edit_adapter, objects);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final edit_class val = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_adapter, null);
        }

        String question=val.question;
        String[] answers=val.answer.split("&");
        ((EditText) convertView.findViewById(R.id.edit_name)).setText(question);


        for(int i=1;i<5;i++){
            int id_ed = getContext().getResources().getIdentifier("edit_name"+i,"id",getContext().getPackageName());
            int id_check = getContext().getResources().getIdentifier("checkBox1"+i,"id",getContext().getPackageName());
            int id_r_ans = getContext().getResources().getIdentifier("checkBox"+i,"id",getContext().getPackageName());
            CheckBox ch1= convertView.findViewById(id_check);
            CheckBox ch2 =  convertView.findViewById(id_r_ans);
            EditText ans = convertView.findViewById(id_ed);
            ch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        ans.setVisibility(View.VISIBLE);
                        ch2.setVisibility(View.VISIBLE);

                    }
                    else{
                        ans.setVisibility(View.INVISIBLE);
                        ch2.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        for(int i=1;i<answers.length;i++){
            int id_ed = getContext().getResources().getIdentifier("edit_name"+i,"id",getContext().getPackageName());
            int id_check = getContext().getResources().getIdentifier("checkBox1"+i,"id",getContext().getPackageName());
            int id_r_ans = getContext().getResources().getIdentifier("checkBox"+i,"id",getContext().getPackageName());
            CheckBox ch1= convertView.findViewById(id_check);
            CheckBox ch2 =  convertView.findViewById(id_r_ans);
            EditText ans = convertView.findViewById(id_ed);
            ans.setText(answers[i]);
            //ans.setVisibility(View.VISIBLE);
            if(answers[i].equals("")){
                ch1.setChecked(false);
            }else{
                ch1.setChecked(true);
            }

        }


        return convertView;
    }
}
