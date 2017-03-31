package com.capps.question.Login;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.capps.question.R;

import java.util.ArrayList;

/**
 * Created by varun on 31/3/17.
 */

public class UsersFrag extends ListFragment implements AdapterView.OnItemClickListener {

    private ArrayList<User> mListUsers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListUsers = User.getAllUsers(getActivity());
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(getActivity(),android.R.layout.simple_list_item_1,mListUsers);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("adapter",parent.toString());

        if (mListUsers.get(position).getFullMark() > 0)
            Toast.makeText(getActivity(),mListUsers.get(position).getPoint() + "/" + mListUsers.get(position).getFullMark(),Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(),R.string.noMark,Toast.LENGTH_SHORT).show();
    }
}
