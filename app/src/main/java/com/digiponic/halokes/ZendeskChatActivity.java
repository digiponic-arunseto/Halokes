package com.digiponic.halokes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zendesk.core.AnonymousIdentity;
import zendesk.core.Identity;
import zendesk.core.Zendesk;
import zendesk.support.Support;
import zendesk.support.request.RequestActivity;

public class ZendeskChatActivity extends AppCompatActivity {
Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Zendesk.INSTANCE.init(this, "https://halokestes.zendesk.com",
                "a50f3cf3bdf85d06e0cf2d8ecc07214c875d3b5871c9debb",
                "mobile_sdk_client_c6942e98a7f86315bf91");



        Identity identity = new AnonymousIdentity();
        Zendesk.INSTANCE.setIdentity(identity);

        Support.INSTANCE.init(Zendesk.INSTANCE);

        RequestActivity.builder().show(this);

    }
}
