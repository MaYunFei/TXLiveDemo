package io.github.mayunfei.imdemo;

import android.app.Application;
import android.content.SharedPreferences;

import com.tencent.imsdk.TIMLogLevel;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;

/**
 * Created by mayunfei on 17-9-13.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), loglvl);
        //初始化TLS
//        TlsBusiness.init(getApplicationContext());
    }
}
