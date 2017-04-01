package com.capps.question.Question;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capps.question.Answer;
import com.capps.question.R;

/**
 * Created by varun on 1/4/17.
 */

public class ListMultiEditViewsFrag extends ListFragment {

    static final String EDITTEXT_COUNT_KEY="ETCount";
    static final String ANSWER_DATA_KEY="ANSWER_DATA";
    static final String mNotAllowUserToInbut ="NOT_ALLOW_INPUT";
    static final String mIsShowAnswers = "SHOW_ANSWERS";

    boolean notAllowUserToInbut,isShowAnswers;
    Answer[] answers;
    int count;
    MultiEditViewAdapter multiEditViewAdapter;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*Steps
        * get number of EditText answers it want
        * get answers if it was(ex: DetailsSreen,showScreen,EditScreen...)
         * get answer = null if no answer is throw to here
         * put showAnswer = false if current fragment in QuestionActivity is ShowFrag
         * put noAllowUserToInPut opposite of showAnswer..because it is not allow in this constrictor except for showFrag
         * */



        if (savedInstanceState == null) {
            count = getArguments().getInt(EDITTEXT_COUNT_KEY);
            answers = (Answer[]) getArguments().getSerializable(ANSWER_DATA_KEY); //TODO:: Should Throw Exception..Beacuse it's not..Serializable
            notAllowUserToInbut = getArguments().getBoolean(mNotAllowUserToInbut,false);
            isShowAnswers= getArguments().getBoolean(mIsShowAnswers,false);
        }
        else {
            count = savedInstanceState.getInt(EDITTEXT_COUNT_KEY);
            answers = (Answer[]) savedInstanceState.getSerializable(ANSWER_DATA_KEY); //TODO:: Should Throw Exception..Beacuse it's not..Serializable
            notAllowUserToInbut = savedInstanceState.getBoolean(mNotAllowUserToInbut, false);
            isShowAnswers = savedInstanceState.getBoolean(mIsShowAnswers, false);
        }

        multiEditViewAdapter = new MultiEditViewAdapter(count, getActivity(), answers, notAllowUserToInbut, isShowAnswers);
        setListAdapter(multiEditViewAdapter);

        View view =inflater.inflate(R.layout.list2,container,false);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EDITTEXT_COUNT_KEY,multiEditViewAdapter.getAnswers().length);
        outState.putSerializable(ANSWER_DATA_KEY,multiEditViewAdapter.getAnswers()); //TODO:: Should Throw Exception..Because it's not..Serializable
        outState.putBoolean(mNotAllowUserToInbut, notAllowUserToInbut);
        outState.putBoolean(mIsShowAnswers, isShowAnswers);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MultiEditViewAdapter.Answers = null;
    }
}
