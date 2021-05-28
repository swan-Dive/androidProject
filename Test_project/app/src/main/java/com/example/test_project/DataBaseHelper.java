package com.example.test_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mybd2.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_ID = "ID";
    public static final String TEST_NUMBER = "T_number";
    public static final String TEST_NAME = "T_name";
    public static final String COL_QUESTION = "question";
    public static final String COL_VARIANTS = "variants";
    public static final String COL_ANSWERS = "answers";
    public static final String COL_TIMER = "timer";


    public DatabaseHelper(Context context){super(context, DATABASE_NAME, null, 2);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE `"+TABLE_NAME+"` (`" +
                COL_ID+"` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "`"+TEST_NUMBER+"` INTEGER NOT NULL," +
                "`"+TEST_NAME+"` TEXT NOT NULL," +
                "`"+ COL_QUESTION +"`TEXT NOT NULL,"+
                "`"+COL_VARIANTS+"` TEXT NOT NULL," +
                "`"+COL_ANSWERS+"` TEXT NOT NULL," +
                "`"+ COL_TIMER+"`"+" INTEGER NOT NULL);";

        db.execSQL(createTable);//
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String q = "DROP TABLE IF EXISTS ";
        db.execSQL(q + TABLE_NAME);
    }

    public boolean addData(int test_number,String name,String question,String variants,String answers,int time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TEST_NUMBER,test_number);
        cv.put(TEST_NAME,name);
        cv.put(COL_QUESTION,question);
        cv.put(COL_VARIANTS,variants);

        cv.put(COL_ANSWERS,answers);
        cv.put(COL_TIMER,time);
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            return false;
        }
        return true;
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("DELETE  FROM "+ TABLE_NAME );


    }
    public void deleteChosenData(int pos){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE  FROM "+ TABLE_NAME+" WHERE "+ TEST_NUMBER+"="+pos );


    }
    //получение названий тестов
    public List<String> getTest(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> list = new ArrayList<String>();
        List<String> list_to_return = new ArrayList<String>();
        String selectQuery = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                if(!list.contains(cursor.getString(1))){
                    list.add(cursor.getString(1));
                    list_to_return.add(cursor.getString(2));
                }

            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return list_to_return;
    }
    //получить вопрос и варианты ответов
    public List<SingleTest> getQuestion_ans(int pos){
        SQLiteDatabase db = this.getReadableDatabase();
        List<SingleTest> list_to_return = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_NAME + " WHERE " +TEST_NUMBER+ "="+ pos +  ";";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                list_to_return.add(new SingleTest(cursor.getString(3),cursor.getString(4)));
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return list_to_return;
    }
    //получить имя конкретного теста
    public String get_test_name(int pos){
        SQLiteDatabase db = this.getReadableDatabase();
        String string_to_return="";
        String selectQuery = "SELECT * FROM "+TABLE_NAME + " WHERE " +TEST_NUMBER+ "="+ pos +  ";";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                string_to_return=cursor.getString(2);
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return string_to_return;
    }
    //посчитать количество вопросов
    public int count(int pos){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+TABLE_NAME + " WHERE " +TEST_NUMBER+ "="+ pos +  ";";
        Cursor cursor = db.rawQuery(selectQuery,null);
        int count=0;
        if (cursor.moveToFirst()){
            do{
                count++;
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return count;
    }
    //получить правильные ответы
    public List<String> get_answers(int pos){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> list_to_return = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_NAME + " WHERE " +TEST_NUMBER+ "="+ pos +  ";";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                list_to_return.add(cursor.getString(5));
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return list_to_return;
    }
    //максимальный номер теста
    public int get_max(){
        SQLiteDatabase db = this.getReadableDatabase();
        int max=0;
        String selectQuery = "SELECT "+TEST_NUMBER +" FROM "+TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                if(max<cursor.getInt(0)){
                    max=cursor.getInt(0);
                }
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return max;
    }
    public int count_tests(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  "+TEST_NUMBER + " FROM " +TABLE_NAME+ ";";
        Cursor cursor = db.rawQuery(selectQuery,null);
        int count=0;
        if (cursor.moveToFirst()){
            do{
                count++;
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return count;
    }
    public List<Integer> countTestNums(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  "+TEST_NUMBER + " FROM " +TABLE_NAME+ ";";
        Cursor cursor = db.rawQuery(selectQuery,null);
        List<Integer> listToReturn=new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                if(!listToReturn.contains(cursor.getInt(0))){
                    listToReturn.add(cursor.getInt(0));
                }
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return listToReturn;
    }

    public List<String> selectAll(){
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getString(1)+' '+cursor.getString(2) + " "+ cursor.getString(3)+cursor.getString(4)+cursor.getString(5));
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return list;
    }
    public int getTimer(int pos){
        int time = 0;
        String selectQuery = "SELECT * FROM "+TABLE_NAME + " WHERE " +TEST_NUMBER+ "="+ pos +  ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                time=cursor.getInt(6);
            }while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();}
        return time;
    }

}