package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListAnnouncement;
import com.digiponic.halokes.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class AnnouncementFragment extends DialogFragment {
    View view;
    int flContent;
    Context context;
    ScrollView svContainer;
    TextView tvAnnouncementContent, tvAnnouncementTitle, tvAnnouncementInfo;
    ImageView ivAnnouncementImg;
    Button btnTTS;
    ListAnnouncement laData;
    TextToSpeech tts;
    SpinKitView skvLoading1;

    public void setLaData(ListAnnouncement laData) {
        this.laData = laData;
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
        tvAnnouncementInfo = view.findViewById(R.id.tvAnnouncementInfo);
        ivAnnouncementImg = view.findViewById(R.id.ivAnnouncementImg);
        skvLoading1 = view.findViewById(R.id.skvLoading1);
        skvLoading1.setVisibility(View.GONE);
        btnTTS = view.findViewById(R.id.btnTTS);

        configTTS();
        showAnnouncement();

        btnTTS.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        configBtnTTS();
                    }
                }
        );

        return view;
    }

    public void configTTS() {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(new Locale("id", "ID"));
                }
            }
        });
    }

    public void configBtnTTS() {
        String announcement = Html.fromHtml(laData.getIsi_berita()).toString();
        Toast.makeText(context, "Memutar Pengumuman...", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(announcement, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(announcement, TextToSpeech.QUEUE_FLUSH, null);
        }

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

    @Override
    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void showAnnouncement() {
        if (!laData.getGambar_berita().equals("")){
            Picasso.with(context).load(laData.getGambar_berita()).into(ivAnnouncementImg);
        }
        tvAnnouncementTitle.setText(laData.getJudul_berita());
        tvAnnouncementInfo.setText("Ditulis Oleh : " + laData.getPenulis() + ", pada : " +laData.getTanggal());
        tvAnnouncementContent.setText(laData.getIsi_berita());
    }
}
