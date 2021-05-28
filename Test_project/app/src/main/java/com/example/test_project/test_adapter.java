package com.example.test_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class test_adapter extends ArrayAdapter<SingleTest> {

    public test_adapter( Context context, SingleTest[] arr) {
        super(context,R.layout.adapter, arr);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final SingleTest month = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter, null);
        }

        String[] ans=month.variants.split("&");
            ((TextView) convertView.findViewById(R.id.textv)).setText(month.name);

            if(ans.length==3){
                ((CheckBox) convertView.findViewById(R.id.rb1)).setText(ans[1]);
                ((CheckBox) convertView.findViewById(R.id.rb2)).setText(ans[2]);
                ( convertView.findViewById(R.id.rb3)).setVisibility(View.INVISIBLE);
                ( convertView.findViewById(R.id.rb4)).setVisibility(View.INVISIBLE);
            }
            else if(ans.length==4){
                ((CheckBox) convertView.findViewById(R.id.rb1)).setText(ans[1]);
                ((CheckBox) convertView.findViewById(R.id.rb2)).setText(ans[2]);
                ((CheckBox) convertView.findViewById(R.id.rb3)).setText(ans[3]);
                ( convertView.findViewById(R.id.rb4)).setVisibility(View.INVISIBLE);
            }else if(ans.length!=0) {
                ((CheckBox) convertView.findViewById(R.id.rb1)).setText(ans[1]);
                ((CheckBox) convertView.findViewById(R.id.rb2)).setText(ans[2]);
                ((CheckBox) convertView.findViewById(R.id.rb3)).setText(ans[3]);
                ((CheckBox) convertView.findViewById(R.id.rb4)).setText(ans[4]);

            }





        return convertView;
    }
}

