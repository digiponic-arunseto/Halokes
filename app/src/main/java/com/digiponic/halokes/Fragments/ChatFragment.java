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

public class ChatFragment extends DialogFragment {
    View view;
    int flContent;
    Context context;
    LinearLayout llChatContainer;
    EditText etChatMessage;
    String id;
    ScrollView svContainer;
    FloatingActionButton fabChatSend;
    Button btnScrollBottom;

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_conv, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();

        svContainer = view.findViewById(R.id.svContainer);
        llChatContainer = view.findViewById(R.id.llChatContainer);
        etChatMessage = view.findViewById(R.id.etChatMessage);
        fabChatSend = view.findViewById(R.id.fabChatSend);
        btnScrollBottom = view.findViewById(R.id.btnScrollBottom);

        btnScrollBottom.setVisibility(View.INVISIBLE);

        fabChatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        svContainer.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
//                Toast.makeText(context, svContainer.getScrollX()+"", Toast.LENGTH_SHORT).show();
                if (svContainer.getScrollY() < llChatContainer.getHeight() / 2) {
                    btnScrollBottom.setVisibility(View.VISIBLE);
                } else {
                    btnScrollBottom.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnScrollBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svContainer.fullScroll(View.FOCUS_DOWN);
            }
        });

        etChatMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (btnScrollBottom.getVisibility() != View.VISIBLE) {
                        btnScrollBottom.setVisibility(View.VISIBLE);
                    }
                } else {

                }
            }
        });
        showMessage();

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

    public void sendMessage() {
//        Toast.makeText(context, svContainer.getScrollY()+ " < " + llChatContainer.getHeight() , Toast.LENGTH_SHORT).show();
        String msg = etChatMessage.getText().toString().trim();
        if (msg.equals("") || msg.isEmpty()) {
            return;
        }

        View viewMessage = getLayoutInflater().inflate(R.layout.template_chat_message_out, null);
        TextView tvMessage = viewMessage.findViewById(R.id.tvChatMessage);
        TextView tvChatMessageTime = viewMessage.findViewById(R.id.tvChatMessageTime);


        tvMessage.setText(Html.fromHtml(etChatMessage.getText() + ""));
        llChatContainer.addView(viewMessage);
        tvChatMessageTime.requestFocus();

        etChatMessage.setText("");
        etChatMessage.requestFocus();
    }

    public void showMessage() {
        llChatContainer.removeAllViews();

        for (int i = 1; i <= 19; i++) {
            View viewMessage;
            if (i % 2 == 0) {
                viewMessage = getLayoutInflater().inflate(R.layout.template_chat_message_in, null);
            } else {
                viewMessage = getLayoutInflater().inflate(R.layout.template_chat_message_out, null);
            }
            TextView tvMessage = viewMessage.findViewById(R.id.tvChatMessage);
            TextView tvChatMessageTime = viewMessage.findViewById(R.id.tvChatMessageTime);
            llChatContainer.addView(viewMessage);
            tvChatMessageTime.requestFocus();

        }
    }
}
