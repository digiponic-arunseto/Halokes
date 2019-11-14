package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.R;

public class HomeFragment extends Fragment {
    View view;
    int flContent;
    Context context;
    LinearLayout llAnnouncementContainer;
    HorizontalScrollView hsvAnnouncementContainer;
    TabLayout tabDots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();

        llAnnouncementContainer = view.findViewById(R.id.llAnnouncementContainer);
        hsvAnnouncementContainer = view.findViewById(R.id.hsvAnnouncementContainer);
        tabDots = view.findViewById(R.id.tabDots);


        showAnnouncement();

        //tab item margin
        for (int i = 0; i < tabDots.getTabCount(); i++) {
            View tab = ((ViewGroup) tabDots.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(6, 0, 6, 0);
            tab.requestLayout();
        }

        hsvAnnouncementContainer.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (isAdded()) {
                    int interval = (hsvAnnouncementContainer.getScrollX() / 640);

                    if (hsvAnnouncementContainer.getChildAt(0).getRight()
                            == (hsvAnnouncementContainer.getWidth() + hsvAnnouncementContainer.getScrollX())) {
                        //scroll view is at bottom
                        tabDots.getTabAt(tabDots.getTabCount() - 1).select();
                    } else {
                        //scroll view is not at bottom
                        tabDots.getTabAt(interval).select();
                    }
                }

            }
        });


        hsvAnnouncementContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    public void showAnnouncement() {
        llAnnouncementContainer.removeAllViews();

        for (int i = 0; i <= 5; i++) {
            //add tab
            tabDots.addTab(tabDots.newTab());

            View viewAnnouncement = getLayoutInflater().inflate(R.layout.template_announcement, null);
            final int finalI = i;
            RelativeLayout rlAnnouncement = viewAnnouncement.findViewById(R.id.rlAnnouncement);
            TextView tvAnnouncementTitle = viewAnnouncement.findViewById(R.id.tvAnnouncementTitle);
            tvAnnouncementTitle.setText(i + "");
            rlAnnouncement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//
                    AnnouncementFragment announcementF = new AnnouncementFragment();
                    announcementF.setId(finalI + "");
                    announcementF.show(getFragmentManager().beginTransaction().addToBackStack("1"), "Dialog Fragment");

                }
            });

            llAnnouncementContainer.addView(viewAnnouncement);
        }
    }
}
