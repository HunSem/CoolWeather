package com.example.coolweather.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Percy on 2015/12/13.
 */
public class CoolWeatherDB {
    public static final String CITY_DB = "china_city.db";
    public static final String CODE_DB = "city_code.db";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase city_db;

    private SQLiteDatabase code_db;

    /**
     * 将构造方法私有化
      */

    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper city_dbHelper = new CoolWeatherOpenHelper(context,
                CITY_DB, null, VERSION);
        city_db = city_dbHelper.getWritableDatabase();

        CoolWeatherOpenHelper code_dbHelper = new CoolWeatherOpenHelper(context,
                CODE_DB, null, VERSION);
        code_db = city_dbHelper.getWritableDatabase();

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
        Cursor cursor = city_db.query("T_Province", null, null, null , null, null, null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("ProSort")));
                province.setProvinceName(cursor.getString(cursor
                        .getColumnIndex("ProName")));
                province.setProvinceCode(cursor.getString(cursor
                        .getColumnIndex("ProSort")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }



    /**
     * 从数据库中读取全国所有城市的信息
     */
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = city_db.query("T_City", null, "ProID = ?",
                new String[] {String.valueOf(provinceId)} , null, null, null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("CitySort")));
                city.setCityName(cursor.getString(cursor
                        .getColumnIndex("CityName")));
                city.setCityCode(cursor.getString(cursor
                        .getColumnIndex("CitySort")));
                city.setProvinceId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }


    /**
     * 从数据库中读取全国所有县的信息
     */
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = city_db.query("T_Zone", null, "CityID = ?",
                new String[] {String.valueOf(cityId)} , null, null, null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("ZoneID")));
                county.setCountyName(cursor.getString(cursor
                        .getColumnIndex("ZoneName")));
                county.setCountyCode(cursor.getString(cursor
                        .getColumnIndex("ZoneID")));
                county.setCityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }


}
