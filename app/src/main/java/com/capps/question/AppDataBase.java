package com.capps.question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.capps.question.Login.User;

import java.util.IllegalFormatException;

/**
 * Created by varun on 27/3/17.
 */

public class AppDataBase extends SQLiteOpenHelper {

    private final static String DB_NAME = "QUESTION_DB";
    private static int DB_VERSION = 1;
    private static AppDataBase INSTANCE=null;

    private final String USER_T = "users";
    private final String USER_COLUMN_NAME = "name";
    private final String USER_COLUMN_EMAIL = "email";
    private final String USER_COLUMN_ADMIN = "admin";
    private final String USER_COLUMN_PASS = "PASS";//TODO::PASS >> pass

    private AppDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static AppDataBase getInstance(Context context){
        if (INSTANCE ==null){
            INSTANCE = new AppDataBase(context,DB_NAME,null,DB_VERSION);
        }
        return INSTANCE;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = "CREATE TABLE " + USER_T +" (id int PRIMARY KEY," +
                                                        USER_COLUMN_NAME + " VARCHAR(51)," +
                                                        USER_COLUMN_EMAIL + " VARCHAR(51)," +
                                                        USER_COLUMN_PASS + "  VARCHAR(21)," +
                                                        USER_COLUMN_ADMIN + " boolean DEFAULT (0)" +
                                                        ");";
        String insertAdmin = "INSERT INTO " + USER_T + " (" + USER_COLUMN_NAME + "," + USER_COLUMN_PASS + "," + USER_COLUMN_ADMIN + ")" +
                "VALUES ('ADMIN','12345',1)";

        db.execSQL(command);
        db.execSQL(insertAdmin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }

    private Cursor getRow(String sql){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    private Cursor getRow(String sql,String selection){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,new String[] {selection});
        return cursor;
    }

    //Users Method

    //create
    public boolean createUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NAME,user.getName());
        values.put(USER_COLUMN_EMAIL,user.getEmail());
        values.put(USER_COLUMN_ADMIN,user.getAdmin());

        try{
            db.beginTransaction();
            int result = (int) db.insertOrThrow(USER_T,null,values);
            db.setTransactionSuccessful();

            if (result >= 0)
                return true;
            else
                return false;
        }catch (IllegalFormatException err){
            Log.d("ERROR","error in create user in db");
            return false;
        }

    }

    //is new user
    public boolean isNewUser(User user){
        String command = "SELECT id FROM " + USER_T + "WHERE name = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(command,new String[] {user.getName()});
        if (cursor != null && cursor.moveToFirst())
            return false; //mean it is exitis in db.
        else
            return true; // mean not exisit in db

    }

    //check password login
    public boolean checkAdminPass(String pass){
        String command = "SELECT id FROM " + USER_T +
                          " WHERE " +USER_COLUMN_PASS +" = ?";

        if (getRow(command,pass).moveToFirst())
            return true;
        else
            return false;
    }
}
