package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.R;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Locale;

public class FeedSubFragment extends DialogFragment {
    View view;
    int flContent;
    Context context;
    String feedId, feedTitle, feedContent, feedInfo;
    ScrollView svContainer;
    TextView tvfeedContent, tvfeedTitle, tvfeedInfo;
    ImageView ivfeedImg;
    Button btnTTS;
    TextToSpeech tts;
    SpinKitView skvLoading1;

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = Html.fromHtml(feedContent).toString().trim();
    }

    public void setFeedInfo(String feedInfo) {
        this.feedInfo = feedInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_details, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();

        svContainer = view.findViewById(R.id.svContainer);
        tvfeedContent = view.findViewById(R.id.tvfeedContent);
        tvfeedTitle = view.findViewById(R.id.tvfeedTitle);
        tvfeedInfo = view.findViewById(R.id.tvfeedInfo);
        ivfeedImg = view.findViewById(R.id.ivfeedImg);
        skvLoading1 = view.findViewById(R.id.skvLoading1);
        skvLoading1.setVisibility(View.GONE);
        btnTTS = view.findViewById(R.id.btnTTS);

        configTTS();
        showfeed();

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
        String feed = feedContent;
        Toast.makeText(context, "Memutar...", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(feed, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(feed, TextToSpeech.QUEUE_FLUSH, null);
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

    public void showfeed() {
        Drawable AnnImg;
        tvfeedTitle.setText(feedTitle);
        tvfeedInfo.setText(feedInfo);
        tvfeedContent.setText(feedContent);
    }
}
