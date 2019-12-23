package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.MainActivity;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Storage.Session;
import com.digiponic.halokes.ZendeskChatActivity;
import com.github.ybq.android.spinkit.SpinKitView;
import com.zopim.android.sdk.prechat.ZopimChatActivity;
import com.zopim.android.sdk.prechat.ZopimChatFragment;

import zendesk.support.request.RequestActivity;

public class ChatContactFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    SpinKitView skvLoading;
    LinearLayout llChatContactContainer;
    ScrollView svContent;
    Button btnSearch, btnSearchBack, btnMore,btnZendesk;
    EditText etSearch;
    LinearLayout llSearchBar;
    InputMethodManager immSoftKeyboard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_contact, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        llChatContactContainer = view.findViewById(R.id.llChatContactContainer);
        skvLoading = view.findViewById(R.id.skvLoading);

        btnSearch = view.findViewById(R.id.btnSearch);
        llSearchBar = view.findViewById(R.id.llSearchBar);
        btnSearchBack = view.findViewById(R.id.btnSearchBack);
        etSearch = view.findViewById(R.id.etSearch);
        btnMore = view.findViewById(R.id.btnMore);
        btnZendesk = view.findViewById(R.id.btnZendesk);

        configBtnZendesk();
        configSearchBar();
        configBtnMore();
        showContact();


        return view;
    }

    public void configBtnZendesk(){
        btnZendesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragment
//                ChatZopimFragment chatZF = new ChatZopimFragment();
//                chatZF.show(getFragmentManager().beginTransaction().addToBackStack("1"), "Dialog Fragment");

//                getFragmentManager().beginTransaction()
//                        .replace(R.id.chat_fragment_container,new ZopimChatFragment(),ZopimChatFragment.class.getName()).addToBackStack("1").commit();
                //activity
                startActivity(new Intent(context, ZopimChatActivity.class));

            }
        });
    }

    public void configSearchBar() {
        immSoftKeyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchBar(true);
            }
        });

        btnSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchBar(false);
            }
        });
    }

    public void configBtnMore() {
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                //.add(groupId, itemId, order, title);
                popupMenu.getMenu().add(1, 1, 1, "Add Contact");
                popupMenu.getMenu().add(1, 2, 2, "Settings");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getOrder() == 1) {
                            Toast.makeText(context, "Adding Contact", Toast.LENGTH_SHORT).show();
                        } else if (item.getOrder() == 2) {
                            Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void showSearchBar(boolean show) {
        if (show) {
            Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            anim.setDuration(600);
            llSearchBar.setVisibility(View.VISIBLE);
            llSearchBar.startAnimation(anim);
            etSearch.requestFocus();
            immSoftKeyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } else {
            llSearchBar.setVisibility(View.GONE);
            etSearch.clearFocus();
            immSoftKeyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showContact() {
        skvLoading.setVisibility(View.VISIBLE);

        View viewContact;

        llChatContactContainer.removeAllViews();

        for (int i = 0; i < 20; i++) {
            viewContact = getLayoutInflater().inflate(R.layout.template_chat_contact, null);
            LinearLayout llChatContact = viewContact.findViewById(R.id.llChatContact);
            TextView tvChatContactName = viewContact.findViewById(R.id.tvChatContactName);
            final TextView tvChatContactTime = viewContact.findViewById(R.id.tvChatContactTime);
            TextView tvChatContactMessage = viewContact.findViewById(R.id.tvChatContactMessage);
            final TextView tvChatContactCounter = viewContact.findViewById(R.id.tvChatContactCounter);

            final int finalI = i;
            llChatContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, "123", Toast.LENGTH_SHORT).show();
                    showSearchBar(false);
                    tvChatContactCounter.setVisibility(View.INVISIBLE);
                    tvChatContactTime.setTextColor(getResources().getColor(R.color.colorDark));
//                //dialogMoreMenu fragment
                    ChatFragment chatF = new ChatFragment();
                    chatF.setId(finalI + "");
                    chatF.show(getFragmentManager().beginTransaction().addToBackStack("1"), "Dialog Fragment");
                }
            });

            llChatContactContainer.addView(viewContact);
        }
        skvLoading.setVisibility(View.GONE);
    }
}