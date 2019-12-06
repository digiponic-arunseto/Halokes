package com.digiponic.halokes.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.digiponic.halokes.Models.ListUser;

/**
 * Created by Arunstop on 25-May-19.
 */

public class Session {

    private static final String SHARED_PREF_NAME = "my_session";
    private static Session mInstance;
    private Context mCtx;

    public Session(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized Session getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new Session(mCtx);
        }
        return mInstance;
    }

    public void saveUser(ListUser user) {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();

        editor.putString("id_user", user.getId_user());
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putString("nis", user.getNis());
        editor.putString("nama_siswa", user.getNama_siswa());
        editor.putString("kelas", user.getKelas());
        editor.putString("role", user.getRole());
        editor.putString("foto", user.getFoto());

        editor.apply();
    }

    //logging check
    public boolean isLoggedIn() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return session.getString("id_user", null) != null;
    }

    public ListUser getUser() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ListUser(
                session.getString("id_user", null),
                session.getString("username", null),
                session.getString("password", null),
                session.getString("nis", null),
                session.getString("nama_siswa", null),
                session.getString("kelas", null),
                session.getString("role", null),
                session.getString("foto", null)
        );
    }

    public void clear() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();
        editor.clear();
        editor.apply();
    }
}
