package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.digiponic.halokes.R;
import com.digiponic.halokes.Storage.Session;

public class ChatContactFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    ProgressBar pbLoading, pbPercentage;
    LinearLayout llChatContactContainer;
    ScrollView svContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_contact, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        llChatContactContainer = view.findViewById(R.id.llChatContactContainer);
        pbLoading = view.findViewById(R.id.pbLoading);

        showContact();

        return view;
    }

    public void showContact() {
        View viewContact;

        llChatContactContainer.removeAllViews();

        for (int i = 0; i < 20; i++) {
            viewContact = getLayoutInflater().inflate(R.layout.template_chat_contact, null);
            LinearLayout llChatContact = viewContact.findViewById(R.id.llChatContact);
            TextView tvChatContactName = viewContact.findViewById(R.id.tvChatContactName);
            TextView tvChatContactTime = viewContact.findViewById(R.id.tvChatContactTime);
            TextView tvChatContactMessage = viewContact.findViewById(R.id.tvChatContactMessage);
            final TextView tvChatContactCounter = viewContact.findViewById(R.id.tvChatContactCounter);

            final int finalI = i;
            llChatContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, "123", Toast.LENGTH_SHORT).show();
                    tvChatContactCounter.setVisibility(View.INVISIBLE);
//                //dialog fragment
                    ChatFragment chatF = new ChatFragment();
                    chatF.setId(finalI+"");
                    chatF.show(getFragmentManager().beginTransaction().addToBackStack("1"), "Dialog Fragment");
                }
            });

            llChatContactContainer.addView(viewContact);
        }
        pbLoading.setVisibility(View.GONE);
    }
}