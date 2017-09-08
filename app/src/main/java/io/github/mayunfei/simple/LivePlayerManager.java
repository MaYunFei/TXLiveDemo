package io.github.mayunfei.simple;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.lang.ref.WeakReference;

import io.github.mayunfei.simple.player.LiveListener;

/**
 * 全局的播放器控制类
 * Created by mayunfei on 17-9-7.
 */

public class LivePlayerManager implements ITXLivePlayListener {
    private WeakReference<LiveListener> listener;

    private static LivePlayerManager instance;
    private TXLivePlayer mLivePlayer;
    private static String TAG = "PlayerManager";
    private boolean isOpenSmall = false;

    private LivePlayerManager() {
    }

    public static LivePlayerManager getInstance() {
        if (instance == null) {
            synchronized (LivePlayerManager.class) {
                if (instance == null) {
                    instance = new LivePlayerManager();
                }
            }
        }
        return instance;
    }

    /**
     * 配置
     */
    public void init(Context context) {
        mLivePlayer = new TXLivePlayer(context);
        TXLivePlayConfig mConfig = new TXLivePlayConfig();
        mLivePlayer.setConfig(mConfig);
        mLivePlayer.setPlayListener(this);
    }

    //判断是否初始化
    private void checkInit() {
        if (mLivePlayer == null) {
            throw new IllegalArgumentException("请先初始化播放器");
        }
    }

    //设置Play View
    public void setPlayerView(TXCloudVideoView videoView) {
        checkInit();
        mLivePlayer.setPlayerView(videoView);
    }

    public void startPlayRtmp(String rtmpUrl) {
        checkInit();
        mLivePlayer.startPlay(rtmpUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
    }


    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        Log.i(TAG, " " + bundle.toString());
        switch (event) {
            case TXLiveConstants.PLAY_EVT_RTMP_STREAM_BEGIN:
                //rtmp 开始播放
                Log.i(TAG, "//rtmp 开始播放");
                listener().onStart();
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_BEGIN:
                //播放
                Log.i(TAG, "//播放");
                listener().onStart();
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_END:
                Log.i(TAG, "//播放结束");
                //播放结束
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_LOADING:
                Log.i(TAG, "//加载中");
                listener().onLoading();
                //加载中
                break;
            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT:
                //网络连接失败
                Log.i(TAG, "//网络连接失败");
                listener().onConnectError();
                break;
            case TXLiveConstants.PLAY_WARNING_RECONNECT:
                //拉取失败
                Log.i(TAG, "//拉取失败");
                listener().onWarningReconnect();
                break;
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {
        Log.i(TAG, "onNetStatus " + bundle.toString());
    }

    public void onResume() {
        checkInit();
        mLivePlayer.resume();
    }

    public void stopPlay() {
        checkInit();
        mLivePlayer.stopPlay(false);
    }

    public void pause() {
        checkInit();
        mLivePlayer.pause();
    }

    public void setListener(LiveListener listener) {
        if (listener == null)
            this.listener = null;
        else
            this.listener = new WeakReference<>(listener);
    }

    public LiveListener listener() {
        if (listener == null)
            return null;
        return listener.get();
    }


}
