package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "healthcare_database";

    private static final int DATABASE_VERSION = 3;


    public Database(Context context, String healthcare, Object o, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "CREATE TABLE users (username TEXT, email TEXT, password TEXT)";
        sqLiteDatabase.execSQL(qry1);

        String qry2 = "CREATE TABLE cart (username TEXT, product TEXT, price float, otype TEXT)";
        sqLiteDatabase.execSQL(qry2);

        String qry3 = "CREATE TABLE orderplace ( username TEXT,fullname TEXT ,address TEXT , contactno TEXT ,pincode INT, date TEXT ,time TEXT,ammout FLOAT, otype TEXT)";
        sqLiteDatabase.execSQL(qry3);


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Handle database schema upgrades here if needed
    }

    public void register(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int login(String username, String password) {
        int result = 0;
        String[] str = new String[]{username, password};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        if (c.moveToFirst()) {
            result = 1;
        }
        c.close();
        db.close();
        return result;
    }

    public void addCart(String username, String product, float price, String otype) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("username", username);
            cv.put("product", product);
            cv.put("price", price);
            cv.put("otype", otype);
            db.insertOrThrow("cart", null, cv);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int checkCart(String username, String product) {
        int result = 0;
        String[] str = new String[]{username, product};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE username=? AND product=?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        c.close(); // Close the cursor after using it
        db.close();
        return result;
    }


    public void removeCart(String username, String otype) {
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "username=? and otype=?", str);
        db.close();
    }

    public ArrayList getCardData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String[] str = new String[2];
            str[0]=username;
            str[1]=otype;
            Cursor c = db.rawQuery("SELECT * FROM cart WHERE username=? AND otype=?", str);
            if (c.moveToFirst()) {
                do {
                    String product = c.getString(1);
                    String price=c.getString(2);
                    arr.add(product + "$" + price);
                } while (c.moveToNext());
            }
            db.close();
        return arr;
    }


    public void addOrder(String username, String fullname, String address, String contact, int pincode, String date, String time, float price, String otype) {

        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("fullname", fullname);
        cv.put("address", address);
        cv.put("contactno", contact);
        cv.put("pincode", pincode);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("ammout", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("orderplace", null, cv);
        db.close();
    }

    public ArrayList getorderdata(String username) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] str = new String[]{username};
        Cursor c = db.rawQuery("select * from orderplace where username=?", str);
        if (c.moveToFirst()) {
            do {
                arr.add(c.getString(1) + "$" + c.getString(2) + "$" + c.getString(3) + "$" + c.getString(4) + "$" + c.getString(5) + "$" + c.getString(6) + "$" + c.getString(7) + "$" + c.getString(8));
            } while (c.moveToNext());
        }
        c.close(); // Close the cursor after using it
        db.close();
        return arr;
    }

    public int checkAppointmentExists(String username,String fullname,String address,String contact,String date,String time){
        int result=0;
        String str[] = new String[6];
        str[0] = username;
        str[1] = fullname;
        str[2] = address;
        str[3] = contact;
        str[4] = date;
        str[5] = time;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from orderplace where username = ? and fullname = ? and address = ? and contactno = ? and date = ? and time = ?",str);
        if(c.moveToFirst()){
            result=1;
        }
        db.close();
        return result;
    }

}
