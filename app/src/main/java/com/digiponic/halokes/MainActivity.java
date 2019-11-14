package com.digiponic.halokes;

import android.content.Context;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.digiponic.halokes.Fragments.AssignmentFragment;
import com.digiponic.halokes.Fragments.AttendanceFragment;
import com.digiponic.halokes.Fragments.BehaviorFragment;
import com.digiponic.halokes.Fragments.BlankFragment;
import com.digiponic.halokes.Fragments.ChatContactFragment;
import com.digiponic.halokes.Fragments.ClassFragment;
import com.digiponic.halokes.Fragments.ExtraFragment;
import com.digiponic.halokes.Fragments.GradeFragment;
import com.digiponic.halokes.Fragments.HomeFragment;
import com.digiponic.halokes.Fragments.FeedFragment;
import com.digiponic.halokes.Fragments.ScheduleFragment;
import com.digiponic.halokes.Fragments.AccountFragment;
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

        for (int i =0;i<bnvMainNav.getMaxItemCount();i++){
            bottomMenuNavigationBadge(i, true);
        }

    }

    public void bottomMenuNavigationBadge(int menuIndex,boolean hasNotif){

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bnvMainNav.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(menuIndex);

        View notificationBadge = getLayoutInflater().inflate(R.layout.template_notif_badge, menuView, false);
        TextView textView = notificationBadge.findViewById(R.id.notifications_badge);
        if (hasNotif){
            textView.setText(menuIndex*1+"");
            itemView.addView(notificationBadge);
        }else {
            textView.setVisibility(View.GONE);
            if (itemView.getChildCount()>=3){
             itemView.removeViewAt(2);
            }
        }
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
                        bottomMenuNavigationBadge(0,false);
                        return true;
                    case R.id.navigation_summary:
                        getSupportFragmentManager().beginTransaction().replace(flContent,
                                new SummaryFragment()).commit();
                        bottomMenuNavigationBadge(1,false);
                        return true;
                    case R.id.navigation_chat:
                        getSupportFragmentManager().beginTransaction().replace(flContent,
                                new ChatContactFragment()).commit();
                        bottomMenuNavigationBadge(2,false);
                        return true;
                    case R.id.navigation_feed:
                        getSupportFragmentManager().beginTransaction().replace(flContent,
                                new FeedFragment()).commit();
                        bottomMenuNavigationBadge(3,false);
                        return true;
                    case R.id.navigation_akun:
                        getSupportFragmentManager().beginTransaction().replace(flContent,
                                new AccountFragment()).commit();
                        bottomMenuNavigationBadge(4,false);
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
        boolean close = true;
//        Toast.makeText(context, view.getId()+"", Toast.LENGTH_SHORT).show();
        dialog.setCancelable(true);

        //set animation on click
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale);
        view.findViewWithTag("imgMenu").startAnimation(anim);

        switch (view.getId()) {
            case R.id.llMenuGrade:
                //Grade
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new GradeFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuBehavior:
                //Behavior
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new BlankFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuAssignment:
                //Assignment
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new AssignmentFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuSchedule:
                //Schedule
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new BlankFragment()).addToBackStack("1").commit();
//                //dialog fragment
//                scheduleF.show(getSupportFragmentManager().beginTransaction().addToBackStack("1"), "Dialog Fragment");
                break;
            case R.id.llMenuClass:
                //Class
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new BlankFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuSubject:
                //Subject
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new BlankFragment()).addToBackStack("1").commit();
                break;
            case R.id.llMenuExtra:
                //Announcement
                getSupportFragmentManager().beginTransaction().replace(flContent,
                        new BlankFragment()).addToBackStack("1").commit();
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
