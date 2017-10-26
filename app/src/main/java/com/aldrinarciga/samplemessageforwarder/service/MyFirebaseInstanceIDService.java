package com.aldrinarciga.samplemessageforwarder.service;

import com.aldrinarciga.samplemessageforwarder.model.TokenEvent;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by aldrinarciga on 10/26/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        TokenEvent tokenEvent = new TokenEvent(refreshedToken);

        EventBus.getDefault().post(tokenEvent);
    }
}
