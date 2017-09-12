package io.github.mayunfei.imdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.android.pushagent.PushManager;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMFriendshipSettings;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupSettings;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMLogListener;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushToken;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendGroup;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.tlslibrary.activity.ImgCodeActivity;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import modle.UserInfo;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

public class MainActivity extends AppCompatActivity implements TLSPwdLoginListener, TIMCallBack {

    private static final String TAG = "MainActivity";
    private String tag = "888 ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
    }

    private void login() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), loglvl);
        //初始化TLS
        TlsBusiness.init(getApplicationContext());


        String id = TLSService.getInstance().getLastUserIdentifier();
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
        if (isUserLogin()) {
            //已经登录
            navToHome();
        } else {
            TLSService.getInstance().TLSPwdLogin("yunfei9959", "yunfei123", this);
        }
    }

    public boolean isUserLogin() {
        return UserInfo.getInstance().getId() != null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()));
    }

    @Override
    public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
        Log.i(TAG, "登录成功 " );
        TLSService.getInstance().setLastErrno(0);
        navToHome();
    }

    @Override
    public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {


        ImgCodeActivity.fillImageview(bytes);
    }

    @Override
    public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
        Log.i(TAG,"OnPwdLoginNeedImgcode " + tlsErrInfo.Msg);
    }

    @Override
    public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
        Log.i(TAG, "登录失败 " + tlsErrInfo.Msg);
    }

    @Override
    public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
        Log.i(TAG, "登录失败 超时 " + tlsErrInfo.ExtraMsg + " "+tlsErrInfo.describeContents()+ " " + tlsErrInfo.Msg + " " +tlsErrInfo.Title );
    }

    public void navToHome() {
        //登录之前要初始化群和好友关系链缓存
        TIMUserConfig userConfig = new TIMUserConfig();
        userConfig.setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                Log.d(TAG, "receive force offline message");
//                Intent intent = new Intent(SplashActivity.this, DialogActivity.class);
//                startActivity(intent);
            }

            @Override
            public void onUserSigExpired() {
                //票据过期，需要重新登录
//                new NotifyDialog().show(getString(R.string.tls_expire), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                            logout();
//                    }
//                });
            }
        })
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i(TAG, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(TAG, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(TAG, "onWifiNeedAuth");
                    }
                });

        //设置刷新监听
        RefreshEvent.getInstance().init(userConfig);
        userConfig = FriendshipEvent.getInstance().init(userConfig);
        userConfig = GroupEvent.getInstance().init(userConfig);
        userConfig = MessageEvent.getInstance().init(userConfig);
        TIMManager.getInstance().setUserConfig(userConfig);
        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
    }

    /**
     * TODO
     * imsdk登录失败后回调
     */
    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onSuccess() {
//初始化程序后台后消息推送
//        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
//        String deviceMan = android.os.Build.MANUFACTURER;
        //注册小米和华为推送
//        if (deviceMan.equals("Xiaomi") && shouldMiInit()){
//            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
//        }else if (deviceMan.equals("HUAWEI")){
//            PushManager.requestToken(this);
//        }


//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "refreshed token: " + refreshedToken);
//
//        if(!TextUtils.isEmpty(refreshedToken)) {
//            TIMOfflinePushToken param = new TIMOfflinePushToken(169, refreshedToken);
//            TIMManager.getInstance().setOfflinePushToken(param, null);
//        }
//        MiPushClient.clearNotification(getApplicationContext());
        Log.d(TAG, "imsdk env " + TIMManager.getInstance().getEnv());
        Intent intent = new Intent(this, ChatRoomActivity.class);
        startActivity(intent);
        finish();

        //TODO 获取公共群ID
    }
}
