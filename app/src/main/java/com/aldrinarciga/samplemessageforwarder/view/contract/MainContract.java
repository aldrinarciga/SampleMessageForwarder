package com.aldrinarciga.samplemessageforwarder.view.contract;

/**
 * Created by aldrinarciga on 10/26/2017.
 */

public interface MainContract {
    interface View {
        void init();
        void showToken(String token);
        void showLoadingIndicator();
        void hideLoadingIndicator();
        void showMessage(String message);
    }

    interface Presenter {
        void init();
        void sendPushNotif(String message, String id);
    }
}
