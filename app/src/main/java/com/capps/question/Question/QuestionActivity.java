package com.capps.question.Question;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.capps.question.Login.EmployeeContentFrag;
import com.capps.question.R;

/**
 * Created by varun on 27/3/17.
 */

public class QuestionActivity extends Activity implements AdminOptionsFrag.AdminOPtionInterface {

    private FragmentManager mManager;


    public static final String IS_ADMIN_KEY = "isAdmin";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mManager = getFragmentManager();

        //check if admin or Employee is logged
        //get data from save
        //admin login savedInstanceState(in case phone over change) or from intent(in case create first/new this activity)
        if( (savedInstanceState!= null && savedInstanceState.getBoolean(IS_ADMIN_KEY)) || getIntent().getBooleanExtra(IS_ADMIN_KEY,false)){
            FragmentTransaction transaction=mManager.beginTransaction();
            transaction.add(R.id.questionActivity,new AdminOptionsFrag());
            transaction.commit();
        }//another wise ,Employee login
        else
        {
            FragmentTransaction transaction=mManager.beginTransaction();
            transaction.add(R.id.questionActivity,new EmployeeContentFrag());
            transaction.commit();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(IS_ADMIN_KEY,getIntent().getBooleanExtra(IS_ADMIN_KEY,false));
    }

    @Override
    public void changeFragment(Fragment newFragment) {
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.replace(R.id.questionActivity,newFragment); //TODO::add to back stack
        transaction.commit();
    }
}