package com.capps.question;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.capps.question.Login.ContentFrag;
import com.capps.question.Login.FooterFrag;
import com.capps.question.Login.HeaderFrag;

public class MainActivity extends AppCompatActivity implements HeaderFrag.LoginInterface {

    private FragmentManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager=getFragmentManager();

        FragmentTransaction transaction=mManager.beginTransaction();
        transaction.add(R.id.header,new HeaderFrag());
        transaction.commit();

        transaction=mManager.beginTransaction();
        transaction.add(R.id.content,new ContentFrag());
        transaction.commit();

        transaction=mManager.beginTransaction();
        transaction.add(R.id.footer,new FooterFrag());
        transaction.commit();

    }


    @Override
    public void changeContent(boolean isAdmin) {

        ViewGroup group = (ViewGroup) findViewById(R.id.content);

        ContentFrag contentFrag= (ContentFrag) mManager.findFragmentById(R.id.content);
        contentFrag.changeLayout(isAdmin,group);
    }
}
