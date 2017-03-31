package com.capps.question.Login;

import android.content.Context;
import android.database.Cursor;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.capps.question.AppDataBase;
import com.capps.question.Question.Question;
import com.capps.question.Question.QuestionActivity;
import com.capps.question.Question.ShowFrag;

import java.util.ArrayList;

/**
 * Created by varun on 27/3/17.
 */

public class User {

    private String name,email;
    private int id;
    private int point;
    private int fullMark;
    private Boolean admin;


//    public User(Boolean admin, String email, String name) {
//        this.admin = admin;
//        this.email = email;
//        this.name = name;
//    }

    public User( String email, String name) {
        this.admin = false;
        this.email = email;
        this.name = name;
    }

    private User( String email, String name,int id,int point,int fullMark) {
        this.admin = false;
        this.email = email;
        this.name = name;
        this.id = id;
        this.point = point;
        this.fullMark = fullMark;

    }




    public int getFullMark() {
        return fullMark;
    }

    public void setFullMark(int fullMark) {
        this.fullMark = fullMark;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point)
    {
        this.point = point;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getName() + ": " + getEmail();
    }


    //DB Method


    @Nullable
    public static User isExists(Context context,@NonNull String email){
        String sql = "SELECT * " +
                " FROM " + AppDataBase.USER_T +
                " WHERE " + AppDataBase.USER_COLUMN_EMAIL +" = ?";
        AppDataBase db=AppDataBase.getInstance(context);
        Cursor value = db.getRow(sql,email);
        if (value.getCount() == 1){
            value.moveToFirst();
            String name = value.getString(value.getColumnIndex(AppDataBase.USER_COLUMN_NAME));
            User newUser =new User(email,name);
            return newUser;
        }
        else
            return null;

    }

    public static long createUser(Context context,User user){
        long result;
        AppDataBase db=AppDataBase.getInstance(context);
        result = db.createUser(user);
        return result;
    }

    public static ArrayList<User> getAllUsers(Context context){
        AppDataBase db=AppDataBase.getInstance(context);

        Cursor values = db.getAllTable(AppDataBase.USER_T,AppDataBase.USER_COLUMN_ADMIN,"0");
        ArrayList<User> list = new ArrayList<User>();
        String email,name;
        int id,point,fullmark;
        if(values.moveToFirst()){
            do {
                email = values.getString(values.getColumnIndex(AppDataBase.USER_COLUMN_EMAIL));
                name = values.getString(values.getColumnIndex(AppDataBase.USER_COLUMN_NAME));
                id = values.getInt(values.getColumnIndex(AppDataBase.ID_COLUMN));
                point = values.getInt(values.getColumnIndex(AppDataBase.USER_COLUMN_POINT));
                fullmark = values.getInt(values.getColumnIndex(AppDataBase.USER_COLUMN_FULL_MARK));

                list.add(new User(email,name,id,point,fullmark));
            }while (values.moveToNext());
        }
        return list;
    }

    /**
     * return
     * array[0]: point,
     * array[1]: full mark*/
    public static short [] getPoint(Context context,short user_id){
        AppDataBase db = AppDataBase.getInstance(context);
        short [] result=new  short[2];
        String sql = "SELECT " + AppDataBase.USER_COLUMN_POINT + " ," + AppDataBase.USER_COLUMN_FULL_MARK +
                " FROM " + AppDataBase.USER_T +
                " WHERE " + AppDataBase.ID_COLUMN + " = ?;";
        Cursor value = db.getRow(sql,user_id + "");;
        if (value.moveToFirst()){
            result[0] = value.getShort(value.getColumnIndex(AppDataBase.USER_COLUMN_POINT));
            result[1] = value.getShort(value.getColumnIndex(AppDataBase.USER_COLUMN_FULL_MARK));
        }
        return result;

    }
    //save points to DB.
    public static boolean saveThePoint(Context context,short user_id,short point,short fullMark){

        AppDataBase db = AppDataBase.getInstance(context);
        return db.savePoint(user_id, point,fullMark);

    }




}
