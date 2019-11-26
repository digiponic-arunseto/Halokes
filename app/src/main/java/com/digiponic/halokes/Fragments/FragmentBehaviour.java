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
    ProgressBar skvLoading;

    public FragmentBehaviour(){

    }

    public void FragmentBehaviour(View view, int flContent, Context context, Session session, ProgressBar skvLoading) {
        this.view = view;
        this.flContent = flContent;
        this.context = context;
        this.session = session;
        this.skvLoading = skvLoading;
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

    public ProgressBar getskvLoading() {
        return skvLoading;
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

    public void setskvLoading(ProgressBar skvLoading) {
        this.skvLoading = skvLoading;
    }
}
