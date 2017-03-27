package com.capps.question.Login;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class ContentFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.login_content_admin_frag,container,false);
    }


    public void changeLayout(boolean isAdmin, ViewGroup conteainer){
        conteainer.removeAllViews();
        if (isAdmin)
             View.inflate(getActivity(),R.layout.login_content_admin_frag,conteainer);
        else
            View.inflate(getActivity(),R.layout.login_content_employee_frag,conteainer);
    }


    
}
