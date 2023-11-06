package com.example.databaseprac;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "employeeManager";
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_CITY = "city";

    SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_AGE + " TEXT,"
                + KEY_CITY + " TEXT " + ")";
        db.execSQL(query);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new employee
    public long addEmployee(String name, String age, String city) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Employee Name
        values.put(KEY_AGE, age); // Employee Age
        values.put(KEY_CITY, city); // Employee City

        return db.insert(TABLE_EMPLOYEE, null, values);
    }

    // code to get the single employee
    public String getEmployee() {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[] {KEY_ID,KEY_NAME,KEY_AGE,KEY_CITY},
                null, null, null, null, null);

        int eId = cursor.getColumnIndex(KEY_ID);
        int eName = cursor.getColumnIndex(KEY_NAME);
        int eAge = cursor.getColumnIndex(KEY_AGE);
        int eCity = cursor.getColumnIndex(KEY_CITY);

        String res = "";

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            res = res +
                    "Id: "+cursor.getString(eId)+"\n"+
                    "Name: "+cursor.getString(eName)+"\n"+
                    "Age: "+cursor.getString(eAge)+"\n"+
                    "City: "+cursor.getString(eCity)+"\n\n";
        }

        db.close();
        return res;
    }

    // code to update the single employee
    public void updateEmployee(long l, String name, String age, String city) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Employee Name
        values.put(KEY_AGE, age); // Employee Age
        values.put(KEY_CITY, city); // Employee City

        db.update(TABLE_EMPLOYEE, values, KEY_ID+"="+l,null);
        db.close();

    }

    // Deleting single employee
    public void deleteEmployee(long l) {
        db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " ="+l,null);
    }

    public String getName(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[] {KEY_ID,KEY_NAME,KEY_AGE,KEY_CITY},
                KEY_ID+"="+l1, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String name = cursor.getString(1);
            return  name;
        }
        return null;
    }

    public String getAge(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[] {KEY_ID,KEY_NAME,KEY_AGE,KEY_CITY},
                KEY_ID+"="+l1, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String age = cursor.getString(2);
            return  age;
        }
        return null;
    }

    public String getCity(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[] {KEY_ID,KEY_NAME,KEY_AGE,KEY_CITY},
                KEY_ID+"="+l1, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String city = cursor.getString(3);
            return  city;
        }
        return null;
    }

}

