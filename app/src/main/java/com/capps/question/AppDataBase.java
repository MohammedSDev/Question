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
                                                        USER_COLUMN_ADMIN + " boolean)";


        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
}
