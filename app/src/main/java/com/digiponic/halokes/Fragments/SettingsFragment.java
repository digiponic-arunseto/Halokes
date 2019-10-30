package com.digiponic.halokes.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.LoginActivity;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Storage.Session;

public class SettingsFragment extends Fragment {

    View view;
    Context context;
    Session session;
    TextView tvGreeting;
    Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        context = getActivity();
        session = Session.getInstance(context);

        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvGreeting.setText("Hello, "+session.getUser().getUsername());

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah Anda ingin keluar aplikasi ?");
                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Berhasil keluar", Toast.LENGTH_SHORT).show();
                        session.clear();
                        dialog.dismiss();
                        startActivity(new Intent(context, LoginActivity.class));
                        getActivity().finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorDark));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));


            }
        });


        return view;
    }
}
