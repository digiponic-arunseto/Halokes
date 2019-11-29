package com.digiponic.halokes.Fragments;

import android.content.Context;
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

        for (int i = 1; i <= 3; i++) {
            //add tab
            tabDots.addTab(tabDots.newTab());

            View viewAnnouncement = getLayoutInflater().inflate(R.layout.template_announcement, null);
            final int finalI = i;
            RelativeLayout rlAnnouncement = viewAnnouncement.findViewById(R.id.rlAnnouncement);
            TextView tvAnnouncementTitle = viewAnnouncement.findViewById(R.id.tvAnnouncementTitle);
            TextView tvAnnouncementContent = viewAnnouncement.findViewById(R.id.tvAnnouncementContent);
            tvAnnouncementTitle.setText("Upacara Bendera Hari Santri Nasional 2019");
            String htmlTxt = Html.fromHtml("<div >\n" +
                    "\t<p>Nomor: 001/D.2.043/Staida/X/2018</p>\n" +
                    "<p>Assalami’alaikum Warahmatullahi Wabarakaatuh</p>\n" +
                    "<p>Dalam rangka memperingati hari santri nasional tahun 2018, maka diwajibkan bagi semua mahasiswa prodi PAI, PGMI, Piaud dan Es Semester I-VII Staida Gresik untuk hadir pada:</p>\n" +
                    "<blockquote><p>Hari&nbsp;&nbsp;&nbsp;&nbsp; : Senin, 22 Oktober 2018</p>\n" +
                    "<p><b>Pukul &nbsp; : 15.30 Wib – Selesai</b></p>\n" +
                    "<p>Tempat : Halaman Kampus STAIDA Gresik</p>\n" +
                    "<p>Acara&nbsp;&nbsp; : Upacara Bendera Hari Santri Nasional 2018</p>\n" +
                    "<p>Busana: Pria: Baju Taqwa Putih, Sarung dan Songkok</p>\n" +
                    "<p>Wanita: Baju Putih, Rok Panjang/maxi, Jilbab Putih</p></blockquote>\n" +
                    "<p>Demikian pemberitahuan ini disampaikan. Harap maklum.</p>\n" +
                    "<p>Wassalamu’alaikum Warahmatullahi Wabarakaatuh</p>\n" +
                    "<p>Gresik, 17 Oktober 2018</p>\n" +
                    "<p>Ketua STAIDA<br>\n" +
                    "Wakil Ketua,</p>\n" +
                    "<p>Kemahasiswaan, Alumni Dan Kerjasama</p>\n" +
                    "<p>Nur Khamim, S.Ag, M.Pd</p>\n" +
                    "</div>").toString().trim();
            tvAnnouncementContent.setText(htmlTxt);
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
}
