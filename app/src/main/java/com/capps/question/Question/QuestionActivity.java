package com.capps.question.Question;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import com.capps.question.Login.EmployeeContentFrag;
import com.capps.question.MainActivity;
import com.capps.question.R;

/**
 * Created by varun on 27/3/17.
 */

public class QuestionActivity extends Activity implements AdminOptionsFrag.AdminOPtionInterface,Index.IndexInterface,ShowFrag.ShowInterface {

    public static Context appContext;
    private FragmentManager mManager;
    public static short CURRENT_USER_ID;



    public static final String IS_ADMIN_KEY = "isAdmin";
    private final String USER_ID = "user_id";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        QuestionActivity.appContext = this;

        if (savedInstanceState != null){
            CURRENT_USER_ID = savedInstanceState.getShort(USER_ID);// TODO: 31/3/17 check if static value are going when upside the mobile
        }


        mManager = getFragmentManager();

        //check is admin or Employee is logged
        //get data from savedInstanceState or intent
        //from savedInstanceState(in case phone over change) or from intent(in case create first/new this activity)
        if (savedInstanceState == null){
            if (getIntent().getBooleanExtra(IS_ADMIN_KEY,false))
            {
                includeFragment(new AdminOptionsFrag());
            }
            else
            {
                ShowFrag.POINTS = 0;
                includeFragment(new ShowFrag());
            }

        }

    }

    private void includeFragment(Fragment frag) {
        FragmentTransaction transaction=mManager.beginTransaction();
        transaction.replace(R.id.questionActivity,frag);
        transaction.commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(IS_ADMIN_KEY,getIntent().getBooleanExtra(IS_ADMIN_KEY,false));
        outState.putShort(USER_ID,CURRENT_USER_ID);
    }

    @Override
    public void changeFragment(Fragment newFragment) {
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.replace(R.id.questionActivity,newFragment); //TODO::todo:: add to back stack ..check it work?
        transaction.addToBackStack(null);
        transaction.commit();
    }




    @Override
    public void sendDetailsData(String question, int question_id) {

        //TODO::check if phone in landscape or portray state.

        //portray state
        DetailsFrag detailFrag = new DetailsFrag();
        Bundle bundle = new Bundle();
        bundle.putString(DetailsFrag.QUESTION_KEY,question);
        bundle.putInt(DetailsFrag.QUESTION_ID_KEY,question_id);

        detailFrag.setArguments(bundle);
        changeFragment(detailFrag);
//        FragmentTransaction transaction = mManager.beginTransaction();
//        transaction.replace(R.id.content,detailFrag);
//        transaction.addToBackStack(null);//TODO:: JUST IN porttray state
//        transaction.commit();

    }

    @Override
    public void moveToNextQuestionFrag(Question question) {
        /*Steps
        * show Question if the question not null
        * show Result Screen if question is null*/

        Bundle bundle=new Bundle();
        if (question != null){
            ShowFrag frag = new ShowFrag();
            bundle.putString(ShowFrag.QUESTION_KEY,question.getmQuestion());
            bundle.putInt(ShowFrag.QUESTION_ID_KEY,question.getmId());
            frag.setArguments(bundle);
            includeFragment(frag);
        }
        else{
            ResultFrag frag=new ResultFrag();
            bundle.putInt(ResultFrag.RESULT_KEY,ShowFrag.POINTS);
            bundle.putInt(ResultFrag.FULL_MARK_KEY,Question.countALl(this));
            frag.setArguments(bundle);
            includeFragment(frag);
        }
    }
}


//TODO::Clear password after clickButton

