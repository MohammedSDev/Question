package com.capps.question.Question;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capps.question.Answer;
import com.capps.question.R;

/**
 * Created by varun on 28/3/17.
 */

public class ListAnswerFrag extends ListFragment {

    static final String EDITTEXT_COUNT_KEY="ETCount";
    QuestionAdapter mQuestionAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        int count = getArguments().getInt(EDITTEXT_COUNT_KEY);
        mQuestionAdapter=new QuestionAdapter(count,getActivity());
        setListAdapter(mQuestionAdapter);
        View view =inflater.inflate(R.layout.list2,container,false);

        return view;
    }

}
