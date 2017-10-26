package com.aldrinarciga.samplemessageforwarder.api;

import com.aldrinarciga.samplemessageforwarder.model.PushNotif;
import com.aldrinarciga.samplemessageforwarder.model.PushNotifResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by aldrinarciga on 10/26/2017.
 */

public interface PushNotifApi {
    @POST("send")
    @Headers({
            "Authorization: key=AIzaSyC55MurRdhZeFZjVU8dNd8Dg2Ma67rHYh4",
            "project_id: 245345776490"
    })
    Call<PushNotifResponse> pushMessage(@Body PushNotif pushNotif);
}
