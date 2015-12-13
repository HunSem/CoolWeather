package com.example.coolweather.until;

/**
 * Created by Percy on 2015/12/13.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
