package com.example.coolweather.until;

import android.util.Log;
import android.widget.Toast;

import com.example.coolweather.activity.WeatherActivity;
import com.example.coolweather.model.Weather;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Percy on 2015/12/13.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address,
                                       final HttpCallbackListener listener){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.connect();
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    Log.d("XXXX", response.toString());
                    Weather weather;
                    Gson gson = new Gson();

                    weather = gson.fromJson(response.toString(), Weather.class);

                    Log.d("XXXX", weather.getCity());
                    Log.d("XXXX", weather.getId());
                    Log.d("XXXX", weather.getTxt());
                    Log.d("XXXX", weather.getFl());
                    Log.d("XXXX", weather.getLoc());
                    Log.d("XXXX", weather.getTmp());

                    if(listener != null){
                        //回调onFinish()方法
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if(listener != null){
                        //回调onError()方法
                        listener.onError(e);
                    }
                } finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
