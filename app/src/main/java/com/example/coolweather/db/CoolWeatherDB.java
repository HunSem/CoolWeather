package com.example.coolweather.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Percy on 2015/12/13.
 */
public class CoolWeatherDB {
    public static final String DB_NAME = "city_code.db";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
      */

    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,
                DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB实例
     */
    public synchronized static CoolWeatherDB getInstance(Context context){
     if(coolWeatherDB == null){
         coolWeatherDB = new CoolWeatherDB(context);
     }
        return coolWeatherDB;
    }



    /**
     * 从数据库中读取全国所有省份的信息
     */
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        String[] columns =  {"province"};
        Cursor cursor = db.query(true, "Code", columns, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setProvinceName(cursor.getString(cursor
                        .getColumnIndex("province")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return list;
    }



    /**
     * 从数据库中读取全国所有城市的信息
     */
    public List<City> loadCities(String provinceName){
        List<City> list = new ArrayList<City>();
        String[] columns =  {"city", "province"};
        Cursor cursor = db.query(true, "Code", columns, "province = ?",
                new String[] {String.valueOf(provinceName)} , null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setCityName(cursor.getString(cursor
                        .getColumnIndex("city")));
                city.setProvinceName(provinceName);
                list.add(city);
            }while (cursor.moveToNext());
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return list;
    }


    /**
     * 从数据库中读取全国所有县的信息
     */
    public List<County> loadCounties(String provinceName, String cityName){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("Code", null, "city = ? and province = ?",
                new String[] {String.valueOf(cityName), String.valueOf(provinceName)}
                , null, null, null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setCountyName(cursor.getString(cursor
                        .getColumnIndex("county")));
                county.setCityName(cityName);
                list.add(county);
            }while (cursor.moveToNext());
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return list;
    }

    public String loadCityCode(Province province, City city, County county){
        StringBuilder cityCode = new StringBuilder();
        cityCode.append("CN");
        int tempCode;
        String tempProvince = province.getProvinceName();
        String tempCity = city.getCityName();
        String tempCounty = county.getCountyName();

        Cursor cursor = db.query("Code", null, "county=? and city=? and province=?",
                new String[] {tempCounty, tempCity, tempProvince}
                , null, null, null);
        Log.d("TAG", "reslut = "+cursor.getCount());

        if(cursor.moveToFirst()){

            tempCode = cursor.getInt(cursor.getColumnIndex("code"));
            Log.d("TAG", "tempcode:"+tempCode);
            cityCode.append(tempCode);
        }else
        {
            Log.d("TAG", "no data");
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return cityCode.toString();
    }


}
