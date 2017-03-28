package com.capps.question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.app.NavUtils;
import android.util.Log;

import com.capps.question.Login.User;
import com.capps.question.Question.Question;

import java.util.IllegalFormatException;

/**
 * Created by varun on 27/3/17.
 */

public class AppDataBase extends SQLiteOpenHelper {


    //TODO:: end transaction and close DB after any connection..

    private final static String DB_NAME = "QUESTION_DB";
    private static int DB_VERSION = 2;
    private static AppDataBase INSTANCE=null;

    private final String USER_T = "users";
    private final String USER_COLUMN_NAME = "name";
    private final String USER_COLUMN_EMAIL = "email";
    private final String USER_COLUMN_ADMIN = "admin";
    private final String USER_COLUMN_PASS = "pass ";//TODO::PASS >> pass ...changed..?

    private final String QUESTION_T = "questions";
    private final String QUESTION_COLUMN_QUESTION= "question";

    private final String ANSWER_T = "answers";
    private final String ANSWER_COLUMN_ANSWER= "answer";
    private final String ANSWER_COLUMN_QUESTION_ID= "question_id";
    private final String ANSWER_COLUMN_CURRECT= "currect";

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
        String createUserT = "CREATE TABLE " + USER_T +" (id int PRIMARY KEY," +
                                                        USER_COLUMN_NAME + " VARCHAR(51)," +
                                                        USER_COLUMN_EMAIL + " VARCHAR(51)," +
                                                        USER_COLUMN_PASS + "  VARCHAR(21)," +
                                                        USER_COLUMN_ADMIN + " boolean DEFAULT (0)" +
                                                        ");";
        String insertAdmin = "INSERT INTO " + USER_T + " (" + USER_COLUMN_NAME + "," + USER_COLUMN_PASS + "," + USER_COLUMN_ADMIN + ")" +
                "VALUES ('ADMIN','12345',1)";

        String createQuestionT = "CREATE TABLE " + QUESTION_T + " (id int PRIMARY KEY," +
                                                   QUESTION_COLUMN_QUESTION + " VARCHAR(51) );";

        String createAnswerT= "CREATE TABLE " + ANSWER_T + " (id int PRIMARY KEY," +
                                                ANSWER_COLUMN_ANSWER+ " VARCHAR(51)," +
                                                ANSWER_COLUMN_QUESTION_ID+ " INTEGER," +
                                                ANSWER_COLUMN_CURRECT+ " BOOLEAN DEFAULT(0)," +
                                                "FOREIGN KEY(" +  ANSWER_COLUMN_QUESTION_ID + ") REFERENCES " + QUESTION_T + "(id) );";



//        db.execSQL(createUserT);//TODO::cancle Comment
        db.execSQL(insertAdmin);
        db.execSQL(createQuestionT);
        db.execSQL(createAnswerT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    //TODO:: now  :: update DB...drop and recreated....?
//        db.execSQL("DROP TABLE IF EXISTS " + USER_T);//TODO::cancle Comment
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_T);
        db.execSQL("DROP TABLE IF EXISTS " + ANSWER_T);
        onCreate(db);

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

    public Cursor getAllTable(String tableName){
        String sql = "SELECT * FROM " + tableName;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
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
            db.endTransaction();

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


    //Question Methods

    public long saveQuestion(Question q){
        long rowID=-1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(QUESTION_COLUMN_QUESTION,q.getQuestion());
        db.beginTransaction();
        rowID = db.insertOrThrow(QUESTION_T, null,values);
        db.setTransactionSuccessful();
        db.endTransaction();

        return rowID;
    }



    //Answer Method
    public boolean saveAnswer(Answer []answers,long question_id){

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + ANSWER_T + "(" + ANSWER_COLUMN_ANSWER+ "," +
                                                        ANSWER_COLUMN_QUESTION_ID + "," +
                                                        ANSWER_COLUMN_CURRECT + ") VALUES (?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        short c ;  //TODO::  short >> byte  (for memory)

        try {

            db.beginTransaction();
            for (Answer answer :answers) {
                statement.clearBindings();
                c= answer.isCurrect()? (short) 1 : (short) 0;
                statement.bindString(1,answer.getAnswer());
                statement.bindLong(2,question_id);
                statement.bindLong(3,c);
                statement.executeInsert();
            }
            db.setTransactionSuccessful();
            return true;
        }
        catch (Exception e)
        {
            Log.d("dbError",e.getMessage());
            return false;
        }
        finally
        {
            statement.close();
            db.endTransaction();
            db.close();
        }
    }
}
