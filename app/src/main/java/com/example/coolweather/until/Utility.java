package com.example.coolweather.until;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.coolweather.model.Weather;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Percy on 2015/12/13.
 */
public class Utility {

    /**
     * 解析服务器返回的数据，并储存到本地
     */
    public static void handleWeatherResponse(Context context, String response){

            Weather weather;
            Gson gson = new Gson();

            weather = gson.fromJson(response, Weather.class);

            Log.d("weather", weather.getCity());
            Log.d("weather", weather.getId());
            Log.d("weather", weather.getTxt());
            Log.d("weather", weather.getFl());
            Log.d("weather", weather.getLoc());
            Log.d("weather", weather.getTmp());

            saveWeatherInfo(context, weather.getCity(), weather.getId(),
                    weather.getTmp(), weather.getFl(), weather.getTxt(), weather.getLoc());


    }
    /**
     * 将服务器返回的数据存储到SharedPreference文件
     */
    public static void saveWeatherInfo(Context context, String cityName,
                                       String weatherCode, String temp1, String temp2,
                                       String weatherDesp, String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.
                getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();
    }
}
