package com.example.test_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void MakeTest(View view) {
        Intent i = new Intent(MainActivity.this,Create_single_test.class);
        startActivity(i);
    }

    public void pass_the_test(View view) {
        Intent i = new Intent(MainActivity.this,passing_tests.class);
        startActivity(i);
    }

    public void test_editor(View view) {
        Intent i = new Intent(this,pass_edits.class);
        startActivity(i);
    }
}