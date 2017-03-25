package com.capps.question.Question;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class CreateFrag extends ListFragment implements AdapterView.OnItemClickListener{

    final ListView listView=getListView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.create_question,container,false);

        Spinner numberOptionsAnswer = (Spinner) view.findViewById(R.id.spinnerNumberOption);
        numberOptionsAnswer.setAdapter(ArrayAdapter.createFromResource(getActivity(),R.array.numberOptionAnswer,android.R.layout.simple_spinner_item));
        numberOptionsAnswer.setOnItemClickListener(this);
        setListAdapter(new QuestionAdapter(0,getActivity()));

        return  view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listView.setAdapter(new QuestionAdapter(position,getActivity()));
    }
}
