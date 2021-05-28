package com.example.test_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class pass_edits extends AppCompatActivity {
    ListView listview;
    DatabaseHelper db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_edits);
        listview=findViewById(R.id.edit_list);
        List<String > list;
        db= new DatabaseHelper(getApplicationContext());
        list=db.getTest();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
              Intent i = new Intent(pass_edits.this,edit_activity.class);
              i.putExtra("pos",position);
              startActivity(i);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                @Override
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                    AlertDialog.Builder adb = new AlertDialog.Builder(pass_edits.this);
                                                    List<Integer> table_list=db.countTestNums();
                                                    adb.setTitle("Delete?");
                                                    adb.setMessage("Are you sure you want to delete " + db.get_test_name(table_list.get(position)));
                                                    final int positionToRemove = position;
                                                    adb.setNegativeButton("Cancel", null);
                                                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            list.remove(positionToRemove);

                                                            db.deleteChosenData(table_list.get(positionToRemove));
                                                            adapter.notifyDataSetChanged();
                                                        }
                                                    });
                                                    adb.show();
                                                    return true;
                                                }
                                            }
        );



    }
}