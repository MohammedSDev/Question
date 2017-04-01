package com.capps.question.Question;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.capps.question.Answer;
import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class CreateFrag extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{


//    private final String ANSWER_USER_TYPE_KEY="dataAnswer";
    EditText editTextQuestion;
    FragmentManager mManager;
    private ListAnswerFrag mListAnswerFrag;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_question,container,false);

        //TODO::To check:  when no data past to onsaveInstanceState..is it came null or not
        mManager = getChildFragmentManager();
        mListAnswerFrag = new ListAnswerFrag();
        changeFragment(mListAnswerFrag,0);

        Spinner numberOptionsAnswer = (Spinner) view.findViewById(R.id.spinnerNumberOption);
        Button buttonSave = (Button) view.findViewById(R.id.buttonSaveNewQuestion);
        editTextQuestion = (EditText) view.findViewById(R.id.edutTextQuestion);


        numberOptionsAnswer.setAdapter(ArrayAdapter.createFromResource(getActivity(),R.array.numberOptionAnswer,android.R.layout.simple_spinner_item));
        numberOptionsAnswer.setOnItemSelectedListener(this);
        //setListAdapter(QuestionAdapter.getInstance(0));



        buttonSave.setOnClickListener(this);

        return  view;
    }

    private void changeFragment(ListAnswerFrag frag,int editTextCount) {
        Bundle bundle=new Bundle();
        bundle.putInt(ListAnswerFrag.EDITTEXT_COUNT_KEY,editTextCount);
        frag.setArguments(bundle);
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.replace(R.id.content,frag);
        transaction.commit();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        * clear data if save success*/ //TODO::Not Code it yet.

        //check if editTExt Question not Empty and more than 11
        if (editTextQuestion.getText().toString().isEmpty() || editTextQuestion.getText().toString().length()<12){
            Toast.makeText(getActivity(),R.string.emptyShortQuestion,Toast.LENGTH_SHORT).show();
            return;
        }

        //get answers from listAdapter and check it is not Empty and more then 1
     //   QuestionAdapter adapter = (QuestionAdapter) getListAdapter(); //TODO::ERROR ::this don't get current adapter.why?


//        Answer []answers = mManager.findFragmentByTag(R.id.content).getdata//TODO:: create getData Method
        Answer []answers= mListAnswerFrag.mQuestionAdapter.getData();
        if (answers.length<2){
            Toast.makeText(getActivity(),R.string.emptyAnswer,Toast.LENGTH_SHORT).show();
            return;
        }
        boolean hasAtLessOneCorrectAnswer=false;
        for (Answer a:answers) {
            if (a == null || (a.getAnswer().isEmpty() || a.getAnswer().equals(" ")) ){ //TODO:: equalsIgnorCase > equals
                Toast.makeText(getActivity(),R.string.emptyAnswer,Toast.LENGTH_SHORT).show();
                return;
            }
            if (a.isCurrect())
                hasAtLessOneCorrectAnswer =true;
        }
        if (!hasAtLessOneCorrectAnswer){
            Toast.makeText(getActivity(),R.string.emptyCorrectAnswer,Toast.LENGTH_SHORT).show();
            return;
        }



        //Save Question & Answers in to the DB..and return rowID
        Question question = new Question(getActivity());
        question.setmQuestion(editTextQuestion.getText().toString());
        boolean resulte = question.save(answers);

        //Show Message to User Save Success OR Save Failed
        if (resulte){
            Toast.makeText(getActivity(),R.string.saveQuestionAndAnswersSuccessful,Toast.LENGTH_SHORT).show(); //TODO::LENGTH_LONG
            //clear data in textBoxs when save Success
            editTextQuestion.setText("");
            changeFragment(new ListAnswerFrag(),0);

        }
        else
        {
            Toast.makeText(getActivity(),R.string.saveQuestionAndAnswersFailed,Toast.LENGTH_SHORT).show();
        }











    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int count = position + 1;
        mListAnswerFrag = new ListAnswerFrag();
        changeFragment(mListAnswerFrag,count);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
