package com.digiponic.halokes.UI;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.digiponic.halokes.Fragments.GradeSubFragment;
import com.digiponic.halokes.Models.ListGrade;

import java.util.List;

public class GradePagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private List<ListGrade> lData;

    public GradePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public GradeSubFragment getItem(int i) {
        GradeSubFragment fragment = new GradeSubFragment();
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

    public void setlData(List<ListGrade> lData) {
        this.lData = lData;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String def = lData.get(position).getMapel();
        String tabTitle = def.substring(0, 1).toUpperCase() + def.substring(1);

        return tabTitle;
    }

}
