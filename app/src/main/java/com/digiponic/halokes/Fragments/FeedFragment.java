package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.R;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

public class FeedFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    LinearLayout llFeedContainer;
    SpinKitView skvLoading;
    Button btnSearch, btnSearchBack, btnMore;
    EditText etSearch;
    LinearLayout llSearchBar;
    InputMethodManager immSoftKeyboard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);
        skvLoading = view.findViewById(R.id.skvLoading);

        btnSearch = view.findViewById(R.id.btnSearch);
        llSearchBar = view.findViewById(R.id.llSearchBar);
        btnSearchBack = view.findViewById(R.id.btnSearchBack);
        etSearch = view.findViewById(R.id.etSearch);
        btnMore = view.findViewById(R.id.btnMore);

        configSearchBar();
        configBtnMore();
        showFeed();

        return view;
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

    public void configBtnMore() {
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                //.add(groupId, itemId, order, title);
                popupMenu.getMenu().add(1, 1, 1, "Read All");
                popupMenu.getMenu().add(1, 2, 2, "Delete All");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getOrder() == 1) {
                            Toast.makeText(context, "Adding Contact", Toast.LENGTH_SHORT).show();
                        } else if (item.getOrder() == 2) {
                            Toast.makeText(context, "All Read", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void showFeed() {
        llFeedContainer = view.findViewById(R.id.llFeedContainer);
        skvLoading.setVisibility(View.VISIBLE);

        llFeedContainer.removeAllViews();
        for (int i = 1; i <= 14; i++) {
            View viewFeed = getLayoutInflater().inflate(R.layout.template_feed, null);
//            final LinearLayout llFeed = child.findViewById(R.id.llFeed);


            final View finalViewFeed = viewFeed;
            viewFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView ivFeedIcon = finalViewFeed.findViewById(R.id.ivFeedIcon);
                    TextView tvFeedTitle = finalViewFeed.findViewById(R.id.tvFeedTitle);
                    TextView tvFeedTag = finalViewFeed.findViewWithTag("tvFeedTag");
                    TextView tvFeedTime = finalViewFeed.findViewById(R.id.tvFeedTime);

                    ivFeedIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_mail_outline_30dp));
                    tvFeedTitle.setTypeface(tvFeedTitle.getTypeface(), Typeface.ITALIC);
                    tvFeedTag.setBackground(getResources().getDrawable(R.drawable.bg_round_side_border_white));
                    tvFeedTag.setTextColor(getResources().getColor(R.color.colorDark));
                    tvFeedTime.setTextColor(getResources().getColor(R.color.colorDark));
                    finalViewFeed.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                }
            });

            if (i % 3 == 0) {
                viewFeed.setBackgroundColor(getResources().getColor(R.color.colorBackground));
            }

            llFeedContainer.addView(viewFeed);
        }
        skvLoading.setVisibility(View.GONE);
    }
}
