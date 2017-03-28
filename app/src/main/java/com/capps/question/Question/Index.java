package com.capps.question.Question;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class Index extends ListFragment {

    private Question [] questions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO::get data from db

        Question []questions = Question.allQuestion(getActivity());
        IndexAdapter adapter=new IndexAdapter(questions,getActivity());

        setListAdapter(adapter);
    }
}
