package com.digiponic.halokes.UI;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.digiponic.halokes.Fragments.AssignmentSubFragment;
import com.digiponic.halokes.Models.ListAssignmentSubject;

import java.util.List;

public class AssignmentPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private List<ListAssignmentSubject> lasData;

    public AssignmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public AssignmentSubFragment getItem(int i) {
        AssignmentSubFragment fragment = new AssignmentSubFragment();
        fragment.setLasData(lasData.get(i));
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

    public void setLasData(List<ListAssignmentSubject> lasData) {
        this.lasData = lasData;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lasData.get(position).getNama_mapel();
    }

}
