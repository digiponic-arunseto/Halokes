package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.R;

public class AnnouncementFragment extends DialogFragment {
    View view;
    int flContent;
    Context context;
    String id;
    ScrollView svContainer;
    TextView tvAnnouncementContent, tvAnnouncementTitle;

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_announcement_details, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();

        svContainer = view.findViewById(R.id.svContainer);
        tvAnnouncementContent = view.findViewById(R.id.tvAnnouncementContent);
        tvAnnouncementTitle = view.findViewById(R.id.tvAnnouncementTitle);

        String htmlTxt = Html.fromHtml("<div >\n" +
                "\t<p>Nomor: 001/D.2.043/Staida/X/2018</p>\n" +
                "<p>Assalami’alaikum Warahmatullahi Wabarakaatuh</p>\n" +
                "<p>Dalam rangka memperingati hari santri nasional tahun 2018, maka diwajibkan bagi semua mahasiswa prodi PAI, PGMI, Piaud dan Es Semester I-VII Staida Gresik untuk hadir pada:</p>\n" +
                "<blockquote><p>Hari&nbsp;&nbsp;&nbsp;&nbsp; : Senin, 22 Oktober 2018</p>\n" +
                "<p><b>Pukul &nbsp; : 15.30 Wib – Selesai</b></p>\n" +
                "<p>Tempat : Halaman Kampus STAIDA Gresik</p>\n" +
                "<p>Acara&nbsp;&nbsp; : Upacara Bendera Hari Santri Nasional 2018</p>\n" +
                "<p>Busana: Pria: Baju Taqwa Putih, Sarung dan Songkok</p>\n" +
                "<p>Wanita: Baju Putih, Rok Panjang/maxi, Jilbab Putih</p></blockquote>\n" +
                "<p>Demikian pemberitahuan ini disampaikan. Harap maklum.</p>\n" +
                "<p>Wassalamu’alaikum Warahmatullahi Wabarakaatuh</p>\n" +
                "<p>Gresik, 17 Oktober 2018</p>\n" +
                "<p>Ketua STAIDA<br>\n" +
                "Wakil Ketua,</p>\n" +
                "<p>Kemahasiswaan, Alumni Dan Kerjasama</p>\n" +
                "<p>Nur Khamim, S.Ag, M.Pd</p>\n" +
                "</div>") + "";

        tvAnnouncementContent.setText(htmlTxt);
        tvAnnouncementTitle.setText("Upacara Bendera Hari Santri Nasional 2019");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// FULLSCREEN DIALOG(IF USING DIALOGFRAGMENT)
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // here i have fragment height 30% of window's height you can set it as per your requirement
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

    }

}
