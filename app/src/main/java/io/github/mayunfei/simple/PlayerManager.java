package io.github.mayunfei.simple;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;

/**
 * 全局的播放器控制类
 * Created by mayunfei on 17-9-7.
 */

public class PlayerManager implements ITXLivePlayListener {

    private static PlayerManager instance;
    private TXLivePlayer mLivePlayer;
    private static String TAG = "PlayerManager";
    private boolean isOpenSmall = false;

    private PlayerManager() {
    }

    public static PlayerManager getInstance() {
        if (instance == null) {
            synchronized (PlayerManager.class) {
                if (instance == null) {
                    instance = new PlayerManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mLivePlayer = new TXLivePlayer(context);
        TXLivePlayConfig mConfig = new TXLivePlayConfig();
        mLivePlayer.setConfig(mConfig);
        mLivePlayer.setPlayListener(this);
    }

    private void checkInit() {
        if (mLivePlayer == null) {
            throw new IllegalArgumentException("请先初始化播放器");
        }
    }

    public void setPlayerView(TXCloudVideoView videoView) {
        checkInit();
        mLivePlayer.setPlayerView(videoView);
    }

    public void startPlayRtmp(String rtmpUrl){
        checkInit();
        mLivePlayer.startPlay(rtmpUrl,TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
    }


    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        Log.i(TAG," " +bundle.toString());
        switch (event){
            case TXLiveConstants.PLAY_EVT_RTMP_STREAM_BEGIN:
                //rtmp 开始播放
                Log.i(TAG,"//rtmp 开始播放");
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_BEGIN:
                //播放
                Log.i(TAG,"//播放");
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_END:
                Log.i(TAG,"//播放结束");
                //播放结束
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_LOADING:
                Log.i(TAG,"//加载中");
                //加载中
                break;
            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT:
                //网络连接失败
                Log.i(TAG,"//网络连接失败");
                break;
            case TXLiveConstants.PLAY_WARNING_RECONNECT:
                //拉取失败
                Log.i(TAG,"//拉取失败");
                break;
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {
        Log.i(TAG,"onNetStatus " +bundle.toString());
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
}
