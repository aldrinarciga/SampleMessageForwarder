package com.aldrinarciga.samplemessageforwarder.view.presenter;

import com.aldrinarciga.samplemessageforwarder.api.PushNotifApi;
import com.aldrinarciga.samplemessageforwarder.model.PushNotif;
import com.aldrinarciga.samplemessageforwarder.model.PushNotifResponse;
import com.aldrinarciga.samplemessageforwarder.view.contract.MainContract;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aldrinarciga on 10/26/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mainView;
    private PushNotifApi pushNotifApi;

    public MainPresenter(MainContract.View mainView, PushNotifApi pushNotifApi) {
        this.mainView = mainView;
        this.pushNotifApi = pushNotifApi;
    }

    @Override
    public void init() {
        String token = FirebaseInstanceId.getInstance().getToken();
        if(token != null) {
            mainView.showToken(token);
        }
    }

    @Override
    public void sendPushNotif(String message, String id) {
        mainView.showLoadingIndicator();
        pushNotifApi.pushMessage(new PushNotif(message, id)).enqueue(new Callback<PushNotifResponse>() {
            @Override
            public void onResponse(Call<PushNotifResponse> call, Response<PushNotifResponse> response) {
                if(mainView != null) {
                    mainView.hideLoadingIndicator();
                    if(response.body() != null) {
                        mainView.showMessage(response.body().getSuccess() > 0 ? "Message sent!" : "Message sending failed");
                    } else {
                        mainView.showMessage("Response is null!!");
                    }
                }

            }

            @Override
            public void onFailure(Call<PushNotifResponse> call, Throwable t) {
                if(mainView != null) {
                    mainView.hideLoadingIndicator();
                    mainView.showMessage("Failed request");
                }
            }
        });
    }
}
