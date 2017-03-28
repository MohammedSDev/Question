package com.capps.question.Question;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.capps.question.R;

/**
 * Created by varun on 27/3/17.
 */

public class AdminOptionsFrag extends Fragment implements View.OnClickListener {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (! (context instanceof  AdminOPtionInterface))
            throw new ClassCastException("Host ACtivity Must Implemment ADminOPtionInterface..");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (! (activity instanceof  AdminOPtionInterface))
            throw new ClassCastException("Host ACtivity Must Implemment ADminOPtionInterface..");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_admin_options,container,false);


        Button newQuestion,listUsers,listAllQuestions;
        newQuestion = (Button) view.findViewById(R.id.buttonSaveNewQuestion);
        listUsers= (Button) view.findViewById(R.id.buttonUsers);
        listAllQuestions = (Button) view.findViewById(R.id.buttonListAllQues);

        newQuestion.setOnClickListener(this);
        listUsers.setOnClickListener(this);
        listAllQuestions.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        AdminOPtionInterface host= (AdminOPtionInterface) getActivity();

        switch (v.getId()){
            case R.id.buttonSaveNewQuestion: host.changeFragment(new CreateFrag());break;
            case R.id.buttonListAllQues:host.changeFragment(new Index());break;
            //TODO:: add User Fragment.
        }
    }



    public interface AdminOPtionInterface{
        void changeFragment(Fragment newFragment);
    }
}
