package com.capps.question;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.capps.question.Login.AdminContentFrag;
import com.capps.question.Login.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener  {

    private FragmentManager mManager;
    private EditText mPass,mEmail,mName;
    private String tagAdmin = "admin", tagEmployee = "employee";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rbgADminEmbloyee);
        radioGroup.setOnCheckedChangeListener(this);

        //set Default Content.. Admin Content
        setAdminContentFragment();

    }

    private void setAdminContentFragment() {

        mManager=getFragmentManager();

        if(mManager.findFragmentByTag(tagAdmin) ==null) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.content, new AdminContentFrag(), tagAdmin);
            transaction.commit();
        }
    }

    private void setEmployeeContentFragment() {

        mManager=getFragmentManager();

        if (mManager.findFragmentByTag(tagEmployee) == null) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.content, new AdminContentFrag(), tagEmployee);
            transaction.commit();
        }
    }




    @Override
    public void onClick(View v) {
        //Login Button
        mManager=getFragmentManager();
        AppDataBase db = AppDataBase.getInstance(this);
        if(mManager.findFragmentByTag(tagAdmin)!= null){


            if (mPass == null) {
                findViewById(R.id.editTextPassword);
            }


            if (db.checkAdminPass(mPass.getText().toString())){

                //TODO::open question activity and put choices
            }

        }
        else{
            if (mEmail == null){
                mEmail = (EditText) findViewById(R.id.editTextEmail);
                mName = (EditText) findViewById(R.id.editTextName);
            }

            User user = new User(mEmail.getText().toString(),mName.getText().toString());
            if(db.createUser(user)){
//                //TODO::open question activity and start questions
            }


        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.rbAdmin:
                setAdminContentFragment();break;
            case R.id.rbEmbloyee:
                setEmployeeContentFragment();break;
        }
    }
}
