package com.digiponic.halokes;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.digiponic.halokes.Fragments.AnnouncementFragment;
import com.digiponic.halokes.Fragments.AssignmentFragment;
import com.digiponic.halokes.Fragments.AttendanceFragment;
import com.digiponic.halokes.Fragments.BehaviorFragment;
import com.digiponic.halokes.Fragments.ClassFragment;
import com.digiponic.halokes.Fragments.GradeFragment;
import com.digiponic.halokes.Fragments.HomeFragment;
import com.digiponic.halokes.Fragments.FeedFragment;
import com.digiponic.halokes.Fragments.ScheduleFragment;
import com.digiponic.halokes.Fragments.SettingsFragment;
import com.digiponic.halokes.Fragments.SubjectFragment;
import com.digiponic.halokes.Fragments.SummaryFragment;

public class MainActivity extends AppCompatActivity {
    BottomSheetDialog dialog;
    View dialogView;
    private Context context;
    private int flContent;
    private BottomNavigationView bnvMainNav;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;


    private BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        bnvMainNav = findViewById(R.id.bnvMainNav);
        flContent = R.id.framelayout_content;

        getSupportFragmentManager().beginTransaction().replace(flContent,
                new HomeFragment()).commit();

        bottomMenuNavigation();

        dialog = new BottomSheetDialog(context);
        dialogView = getLayoutInflater().inflate(R.layout.fragment_more_menu, null);
        dialog.setContentView(dialogView);


        bnvMainNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        bnvMainNav.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);
    }

    public void bottomMenuNavigation() {
        //selected
        onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(flContent,
                                new HomeFragment()).commit();
                        return true;
                    case R.id.navigation_summary:
                        getSupportFragmentManager().beginTransaction().replace(flContent,
                                new SummaryFragment()).commit();
                        return true;
                    case R.id.navigation_feed:
                        getSupportFragmentManager().beginTransaction().replace(flContent,
                                new FeedFragment()).commit();
                        return true;
                    case R.id.navigation_setttings:
                        getSupportFragmentManager().beginTransaction().replace(flContent,
                                new SettingsFragment()).commit();
                        return true;
                }
                return false;
            }
        };

        //reselected
        onNavigationItemReselectedListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

            }
        };
    }

    public void menuNavigation(View view) {
        bnvMainNav.setVisibility(View.INVISIBLE);
        //BottomSheetDialog, for "more menu"
        ScheduleFragment scheduleF = new ScheduleFragment();
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale);
        view.findViewWithTag("imgMenu").startAnimation(anim);
        boolean close = true;
//        Toast.makeText(context, view.getId()+"", Toast.LENGTH_SHORT).show();
        dialog.setCancelable(true);
        switch (view.getId()) {
            case R.id.llMenuGrade:
                //Grade
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new GradeFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuBehavior:
                //Behavior
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new BehaviorFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuAssignment:
                //Assignment
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new AssignmentFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuSchedule:
                //Schedule
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new ScheduleFragment()).addToBackStack("1").commit();
//                //ialog fragment
//                scheduleF.show(getSupportFragmentManager().beginTransaction().addToBackStack("1"), "Dialog Fragment");
                break;
            case R.id.llMenuClass:
                //Class
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new ClassFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuSubject:
                //Subject
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new SubjectFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuAnnouncement:
                //Announcement
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new AnnouncementFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuAttendance:
                //Attendance
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new AttendanceFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuMore:
                //Attendance
                close = false;
                dialog.show();
                bnvMainNav.setVisibility(View.VISIBLE);
                break;
        }

        if (close) dialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        bnvMainNav.setVisibility(View.VISIBLE);
        if (bnvMainNav.getSelectedItemId() == R.id.navigation_home) {
            super.onBackPressed();
        } else {
            bnvMainNav.setSelectedItemId(R.id.navigation_home);
        }
    }

    public void navBack(View view) { // for level 2 fragment
        bnvMainNav.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }
}
