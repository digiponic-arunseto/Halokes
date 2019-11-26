package com.digiponic.halokes.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.LoginActivity;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Storage.Session;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {

    View view;
    Context context;
    Session session;
    TextView tvGreeting;
    Button btnLogout,btnEditProfile;
    int flContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        context = getActivity();
        session = Session.getInstance(context);
        flContent = R.id.framelayout_content;

        tvGreeting = view.findViewById(R.id.tvAccountName);
        tvGreeting.setText(session.getUser().getNama_siswa());


        btnLogout = view.findViewById(R.id.btnLogout);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View bnvMainNav = getActivity().findViewById(R.id.bnvMainNav);
                bnvMainNav.setVisibility(View.INVISIBLE);

                getFragmentManager().beginTransaction().replace(flContent,
                        new AccountEditFragment()).addToBackStack("1").commit();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View layoutLogout = getLayoutInflater().inflate(R.layout.layout_dialog_confirmation, null);
                // yes == log out
                // no == cancel
                Button btnYes = layoutLogout.findViewById(R.id.btnNo);
                Button btnNo = layoutLogout.findViewById(R.id.btnYes);
                TextView tvTitle = layoutLogout.findViewById(R.id.tvTitle);
                builder.setView(layoutLogout);

                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();// to resize alert dialogMoreMenu, this command line should be below the alert.show()
                dialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);// here i have fragment height 30% of window's height you can set it as per your requirement


                tvTitle.setText("Apakah Anda yakin ingin keluar akun ?");
                btnYes.setText("KELUAR");
                btnNo.setText("BATAL");
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        session.clear();
                        dialog.dismiss();
                        startActivity(new Intent(context, LoginActivity.class));
                        getActivity().finish();
                        Toast.makeText(context, "Berhasil keluar", Toast.LENGTH_SHORT).show();
                    }
                });

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });


        return view;
    }
}
