package com.capps.question.Login;

import android.content.Context;
import android.database.Cursor;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.capps.question.AppDataBase;

/**
 * Created by varun on 27/3/17.
 */

public class User {

    private String name,email;
    private int id;
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


}
