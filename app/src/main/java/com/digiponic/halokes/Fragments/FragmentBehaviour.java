package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.digiponic.halokes.Storage.Session;

public class FragmentBehaviour extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    ProgressBar pbLoading;

    public FragmentBehaviour(){

    }

    public void FragmentBehaviour(View view, int flContent, Context context, Session session, ProgressBar pbLoading) {
        this.view = view;
        this.flContent = flContent;
        this.context = context;
        this.session = session;
        this.pbLoading = pbLoading;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public int getFlContent() {
        return flContent;
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public Session getSession() {
        return session;
    }

    public ProgressBar getPbLoading() {
        return pbLoading;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setFlContent(int flContent) {
        this.flContent = flContent;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setPbLoading(ProgressBar pbLoading) {
        this.pbLoading = pbLoading;
    }
}
