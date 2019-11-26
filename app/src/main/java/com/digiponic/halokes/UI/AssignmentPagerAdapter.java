package com.digiponic.halokes.UI;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.digiponic.halokes.Fragments.AssignmentSubFragment;
import com.digiponic.halokes.Models.ListAssignment;

import java.util.List;

public class AssignmentPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private List<ListAssignment> lData;


    public AssignmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public AssignmentSubFragment getItem(int i) {
        AssignmentSubFragment fragment = new AssignmentSubFragment();
        fragment.setlData(lData.get(i));
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(AssignmentSubFragment.ARG_OBJECT, i + 1);
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

    public void setlData(List<ListAssignment> lData) {
        this.lData = lData;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lData.get(position).getNama_mapel();
    }

}
