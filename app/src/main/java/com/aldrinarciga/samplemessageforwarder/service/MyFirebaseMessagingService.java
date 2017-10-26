package com.aldrinarciga.samplemessageforwarder.service;

import android.util.Log;

import com.aldrinarciga.samplemessageforwarder.model.PushNotif;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by aldrinarciga on 10/26/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            PushNotif.Data data = new PushNotif.Data(remoteMessage.getData().get("message"));
            EventBus.getDefault().post(data);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
