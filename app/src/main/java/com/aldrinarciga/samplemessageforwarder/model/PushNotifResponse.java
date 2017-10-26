package com.aldrinarciga.samplemessageforwarder.model;

/**
 * Created by aldrinarciga on 10/26/2017.
 */

public class PushNotifResponse {
    private long multicast_id;
    private int success;
    private int failure;

    public long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }
}
