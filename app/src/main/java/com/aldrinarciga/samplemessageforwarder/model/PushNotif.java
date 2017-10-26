package com.aldrinarciga.samplemessageforwarder.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldrinarciga on 10/26/2017.
 */

public class PushNotif {
    private List<String> registration_ids;
    private Data data;

    public PushNotif(String message, String... ids) {
        data = new Data(message);
        registration_ids = new ArrayList<>();
        for(String id: ids) {
            addRegistrationId(id);
        }
    }

    public void addRegistrationId(String token) {
        if(registration_ids != null) {
            registration_ids.add(token);
        }
    }

    public static class Data {
        String message;

        public Data(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
