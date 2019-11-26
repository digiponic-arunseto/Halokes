package com.digiponic.halokes.UI;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.digiponic.halokes.Fragments.GradeSubFragment;
import com.digiponic.halokes.Fragments.ScheduleSubFragment;
import com.digiponic.halokes.Models.ListGrade;
import com.digiponic.halokes.Models.ListSchedule;

import java.util.List;

public class SchedulePagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private List<ListSchedule> lData;

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public ScheduleSubFragment getItem(int i) {
        ScheduleSubFragment fragment = new ScheduleSubFragment();
        fragment.setlData(lData.get(i));
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(GradeSubFragment.ARG_OBJECT, i + 1);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void setTabCount(int tabCount) {
        this.tabCount = tabCount;
    }

    public void setlData(List<ListSchedule> lData) {
        this.lData = lData;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lData.get(position).getHari();
    }

}
