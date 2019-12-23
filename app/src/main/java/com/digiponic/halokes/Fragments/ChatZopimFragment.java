package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.digiponic.halokes.R;
import com.zopim.android.sdk.prechat.ZopimChatFragment;

public class ChatZopimFragment extends DialogFragment {
    View view;
    int flContent, flChat;
    Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_zopim, container, false);

        flContent = R.id.framelayout_content;
        flChat = R.id.chat_fragment_container;
        context = getActivity();

        getFragmentManager().beginTransaction()
                .replace(R.id.chat_fragment_container, new ZopimChatFragment(), ZopimChatFragment.class.getName()).addToBackStack("1").commit();


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// FULLSCREEN DIALOG(IF USING DIALOGFRAGMENT)
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // here i have fragment height 30% of window's height you can set it as per your requirement
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));
    }

}
