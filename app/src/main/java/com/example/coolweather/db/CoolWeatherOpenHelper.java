
package com.example.coolweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.coolweather.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Percy on 2015/12/13.
 */

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "city_code.db";

    public static final String PACKAGE_NAME = "com.example.coolweather";
    // 数据库路径
    public static final String DB_PATH = "/data"
            +  Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/databases";
    private Context mContext;


    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
        copyDatabase(DB_NAME);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean copyDatabase(String DB_NAME){
        File db_path = new File(DB_PATH);
        if(!db_path.exists()){
            db_path.mkdirs();
        }
        File dbfile = new File(DB_PATH, DB_NAME);
        //Log.d("TAG", "dbfile.exists:"+dbfile.exists());
        // 判断数据库文件是否存在，
        // 若不存在,将/assets下的数据库文件导入到/data/data/package/databases下
        // 若存在，就直接打开数据库
        try {
            if (!dbfile.exists()) {
                // 获取读取/raw下的数据库文件的输入流
                InputStream is = mContext.getResources().openRawResource(
                        R.raw.city_code); //欲导入的数据库
                // 创建往数据库中写数据的输出流
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[1024];
                int count = -1;
                while((count = is.read(buffer)) != -1){
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();

                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*public boolean copyCodeDatabase(String DB_NAME){
        File db_path = new File(DB_PATH);
        if(!db_path.exists()){
            db_path.mkdirs();
        }
        File dbfile = new File(DB_PATH, DB_NAME);
        //Log.d("TAG", "dbfile.exists:"+dbfile.exists());
        // 判断数据库文件是否存在，
        // 若不存在,将/assets下的数据库文件导入到/data/data/package/databases下
        // 若存在，就直接打开数据库
        try {
            if (!dbfile.exists()) {
                // 获取读取/raw下的数据库文件的输入流
                InputStream is = mContext.getResources().openRawResource(
                        R.raw.city_code); //欲导入的数据库
                // 创建往数据库中写数据的输出流
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[1024];
                int count = -1;
                while((count = is.read(buffer)) != -1){
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();

                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }*/

}

