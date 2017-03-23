package com.orange_team.narinjapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.orange_team.narinjapp.R;

public class MessageFragment extends Fragment{

    EditText mPreviewMessageText,mOnTheWayMessageText,mFinishedMessageText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }

    private void init(View view){
        mPreviewMessageText = (EditText) view.findViewById(R.id.previewMessageText);
        mOnTheWayMessageText = (EditText) view.findViewById(R.id.onTheWayMessageText);
        mFinishedMessageText = (EditText) view.findViewById(R.id.finishedMessageText);
    }
}
