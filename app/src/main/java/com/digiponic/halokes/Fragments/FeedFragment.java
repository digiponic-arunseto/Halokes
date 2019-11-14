package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digiponic.halokes.R;
import com.digiponic.halokes.Storage.Session;

public class FeedFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    LinearLayout llFeedContainer;
    ProgressBar pbLoading;
    FragmentBehaviour fb = new FragmentBehaviour();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);
        pbLoading = view.findViewById(R.id.pbLoading);

        showFeed();
        return view;
    }

    public void showFeed() {
        llFeedContainer = view.findViewById(R.id.llFeedContainer);
        View viewFeed;

        llFeedContainer.removeAllViews();
        for (int i = 1; i <= 14; i++) {
            viewFeed = getLayoutInflater().inflate(R.layout.template_feed, null);
//            final LinearLayout llFeed = child.findViewById(R.id.llFeed);


            final View finalViewFeed = viewFeed;
            viewFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tvFeedTitle = finalViewFeed.findViewById(R.id.tvFeedTitle);
                    TextView tvFeedTag = finalViewFeed.findViewWithTag("tvFeedTag");
                    tvFeedTitle.setTypeface(tvFeedTitle.getTypeface(), Typeface.ITALIC);
                    tvFeedTag.setBackground(getResources().getDrawable(R.drawable.bg_round_side_border_white));
                    tvFeedTag.setTextColor(getResources().getColor(R.color.colorDark));
                    finalViewFeed.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                }
            });

            if(i%3==0){
                viewFeed.setBackgroundColor(getResources().getColor(R.color.colorBackground));
            }

            llFeedContainer.addView(viewFeed);
        }
        pbLoading.setVisibility(View.GONE);
    }
}
