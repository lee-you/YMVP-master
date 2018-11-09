package io.liyou.sample;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.tencent.mmkv.MMKV;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        OkGo.getInstance().init(this).setOkHttpClient(builder.build());
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
    }

    public static Context getInstance() {
        return mContext;
    }
}
