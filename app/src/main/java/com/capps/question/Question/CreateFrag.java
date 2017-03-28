package com.capps.question.Question;


import android.app.ListFragment;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.capps.question.Answer;
import com.capps.question.AppDataBase;
import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class CreateFrag extends ListFragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{

     private ListView listView;
     private final String ANSWER_USER_TYPE_KEY="dataAnswer";
     EditText editTextQuestion;

    QuestionAdapter mQuestionAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_question,container,false);

        Spinner numberOptionsAnswer = (Spinner) view.findViewById(R.id.spinnerNumberOption);
        Button buttonSave = (Button) view.findViewById(R.id.buttonSaveNewQuestion);
        editTextQuestion = (EditText) view.findViewById(R.id.edutTextQuestion);


        numberOptionsAnswer.setAdapter(ArrayAdapter.createFromResource(getActivity(),R.array.numberOptionAnswer,android.R.layout.simple_spinner_item));
        numberOptionsAnswer.setOnItemSelectedListener(this);
        //setListAdapter(QuestionAdapter.getInstance(0));
        mQuestionAdapter=new QuestionAdapter(0,getActivity());
        setListAdapter(mQuestionAdapter);


        buttonSave.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getListView();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //QuestionAdapter adapter = (QuestionAdapter) getListAdapter();
        //outState.putSerializable(ANSWER_USER_TYPE_KEY,adapter.getData());//TODO::Should Throw Error,Because Answer not implement Serializable
//        outState.putSerializable(ANSWER_USER_TYPE_KEY,QuestionAdapter.mAnswers);
    }

    @Override
    public void onClick(View v) {
        //Save Button clicked

        /*Steps
        * check Question
        * get&check Answers
        * save Question & Answers
        * show message to user (Success|failed)
        * clear data if save success*/ //TODO::NOt Code it yet.

        //check if editTExt Question not Empty and more than 11
        if (editTextQuestion.getText().toString().isEmpty() || editTextQuestion.getText().toString().length()<12){
            Toast.makeText(getActivity(),R.string.emptyShortQuestion,Toast.LENGTH_SHORT).show();
            return;
        }

        //get answers from listAdapter and check it is not Empty and more then 1
     //   QuestionAdapter adapter = (QuestionAdapter) getListAdapter(); //TODO::ERROR ::this don't get current adapter.why?

        Answer []answers= mQuestionAdapter.getData();
        if (answers.length<2){
            Toast.makeText(getActivity(),R.string.emptyAnswer,Toast.LENGTH_SHORT).show();
            return;
        }
        for (Answer a:answers) {
            if (a.getAnswer().isEmpty() || a.getAnswer().equalsIgnoreCase(" ")){ //TODO:: equals > equals
                Toast.makeText(getActivity(),R.string.emptyAnswer,Toast.LENGTH_SHORT).show();
                return;
            }
        }



        //Save Question & Answers in to the DB..and return rowID
        Question question = new Question(getActivity());
        question.setQuestion(editTextQuestion.getText().toString());
        boolean resulte = question.save(answers);

        //Show Message to User Save Success OR Save Failed
        if (resulte){
            Toast.makeText(getActivity(),R.string.saveQuestionAndAnswersSuccessful,Toast.LENGTH_SHORT).show(); //TODO::LENGTH_LONG
        }
        else
        {
            Toast.makeText(getActivity(),R.string.saveQuestionAndAnswersFailed,Toast.LENGTH_SHORT).show();
        }







    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int count = position + 1;
        mQuestionAdapter=new QuestionAdapter(count,getActivity());
        listView.setAdapter(mQuestionAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
