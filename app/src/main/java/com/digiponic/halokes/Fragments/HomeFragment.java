package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListAnnouncement;
import com.digiponic.halokes.Models.ModelAnnouncement;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    View view;
    int flContent;
    Context context;
    LinearLayout llAnnouncementContainer;
    HorizontalScrollView hsvAnnouncementContainer;
    TabLayout tabDots;

    // BottomSheetDialog
    BottomSheetDialog dialogMoreMenu;
    View viewMoreMenu;
    BottomNavigationView bnvMainNav;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();

        llAnnouncementContainer = view.findViewById(R.id.llAnnouncementContainer);
        hsvAnnouncementContainer = view.findViewById(R.id.hsvAnnouncementContainer);
        tabDots = view.findViewById(R.id.tabDots);


        bnvMainNav = getActivity().findViewById(R.id.bnvMainNav);

        dialogMoreMenu = new BottomSheetDialog(context);
        viewMoreMenu = getLayoutInflater().inflate(R.layout.fragment_more_menu, null);
        dialogMoreMenu.setContentView(viewMoreMenu);
        dialogMoreMenu.setCancelable(true);

        try {
            showAnnouncement();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
        }

        //indicator changes as the announcement goes
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

        Call<ModelAnnouncement> call = RetrofitClient.getInstance().getApi().showAnnouncement();
        call.enqueue(new Callback<ModelAnnouncement>() {
            @Override
            public void onResponse(Call<ModelAnnouncement> call, Response<ModelAnnouncement> response) {
                ModelAnnouncement ma = response.body();
                if (isAdded() && response.isSuccessful()) {
                    for (ListAnnouncement la : ma.getData()) {
                        tabDots.addTab(tabDots.newTab());

                        View viewAnnouncement = getLayoutInflater().inflate(R.layout.template_announcement, null);
                        RelativeLayout rlAnnouncement = viewAnnouncement.findViewById(R.id.rlAnnouncement);
                        TextView tvAnnouncementTitle = viewAnnouncement.findViewById(R.id.tvAnnouncementTitle);
                        TextView tvAnnouncementContent = viewAnnouncement.findViewById(R.id.tvAnnouncementContent);
                        ImageView ivAnnouncementThumbnail = viewAnnouncement.findViewById(R.id.ivAnnouncementThumbnail);

                        configAnnouncementDummy(
                                tvAnnouncementTitle,
                                tvAnnouncementContent,
                                ivAnnouncementThumbnail,
                                rlAnnouncement,
                                la);

                        llAnnouncementContainer.addView(viewAnnouncement);
                    }
                    //tab item margin
                    for (int i = 0; i < ma.getData().size(); i++) {
                        View tab = ((ViewGroup) tabDots.getChildAt(0)).getChildAt(i);
                        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                        p.setMargins(6, 0, 6, 0);
                        tab.requestLayout();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelAnnouncement> call, Throwable t) {
                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void menuNavigation(View view, BottomNavigationView bnvMainNav, FragmentTransaction fragmentTransaction) {
        //Bottom Navigation Menu
        bnvMainNav.setVisibility(View.INVISIBLE);
        boolean close = true;
//        Toast.makeText(context, view.getId()+"", Toast.LENGTH_SHORT).show();

        //set animation on click
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale);
//        view = view.findViewWithTag("imgMenu");
        view.findViewWithTag("imgMenu").startAnimation(anim);
        switch (view.getId()) {
            case R.id.llMenuGrade:
                //Grade
                fragmentTransaction.replace(flContent,
                        new GradeFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuCounseling:
                //Counseling
                fragmentTransaction.replace(flContent,
                        new CounselingFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuAssignment:
                //Assignment
                fragmentTransaction.replace(flContent,
                        new AssignmentFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuSchedule:
                //Schedule
                fragmentTransaction.replace(flContent,
                        new ScheduleFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuClass:
                //Class
                fragmentTransaction.replace(flContent,
                        new ClassFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuExtra:
                //Extra
                fragmentTransaction.replace(flContent,
                        new ExtraFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuAttendance:
                //Attendance
                fragmentTransaction.replace(flContent,
                        new AttendanceFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuMore:
                //More
                close = false;
                dialogMoreMenu.show();
                bnvMainNav.setVisibility(View.VISIBLE);
                break;
        }

        if (close) dialogMoreMenu.dismiss();
    }

    public void configAnnouncementDummy(
            TextView tvAnnouncementTitle,
            TextView tvAnnouncementContent,
            ImageView ivAnnouncementThumbnail,
            RelativeLayout rlAnnouncement,
            final ListAnnouncement laData) {

        tvAnnouncementTitle.setText(laData.getJudul_berita());
        tvAnnouncementContent.setText(laData.getIsi_berita());
        if (!laData.getGambar_berita().equals("")) {
            Picasso.with(context).load(laData.getGambar_berita()).into(ivAnnouncementThumbnail);
        }
        rlAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                AnnouncementFragment announcementF = new AnnouncementFragment();
                announcementF.setLaData(laData);

                announcementF.show(getFragmentManager().beginTransaction().addToBackStack("1"), "Dialog Fragment");

            }
        });
    }
}
