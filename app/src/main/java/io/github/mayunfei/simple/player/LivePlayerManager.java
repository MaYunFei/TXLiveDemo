package io.github.mayunfei.simple.player;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.lang.ref.WeakReference;

/**
 * 全局的播放器控制类
 * Created by mayunfei on 17-9-7.
 */

public class LivePlayerManager implements ITXLivePlayListener {

    // 播放器状态
    //正常
    public static final int CURRENT_STATE_NORMAL = 0;
    //准备中
    public static final int CURRENT_STATE_PREPAREING = 1;
    //播放中
    public static final int CURRENT_STATE_PLAYING = 2;
    //开始缓冲
    public static final int CURRENT_STATE_PLAYING_BUFFERING_START = 3;
    //暂停
    public static final int CURRENT_STATE_PAUSE = 5;
    //自动播放结束
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    //错误状态
    public static final int CURRENT_STATE_ERROR = 7;
    // 课堂状态

    // 休息
    public static final int CURRENT_STATE_RESET = 8;
    // 下课
    public static final int CURRENT_STATE_CLASS_OVER = 9;

    private int mState = CURRENT_STATE_NORMAL;

    private WeakReference<LiveListener> listener;
    private static LivePlayerManager instance;
    private TXLivePlayer mLivePlayer;
    private static String TAG = "PlayerManager";
    private String mPlayUrl;
    private int mPlayType;
    private boolean mVideoPause;

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

    public void setPlayUrl(String playUrl){
        mPlayType = Util.checkLiveUrl(playUrl);
        if (mPlayType == -1){
            throw new IllegalArgumentException("直播链接错误");
        }
        mPlayUrl = playUrl;
    }

    //设置Play View
    public void setPlayerView(TXCloudVideoView videoView) {
        checkInit();
        mLivePlayer.setPlayerView(videoView);
    }

    public void setPlayerView(VideoControlView videoControlView){
        checkInit();
        mLivePlayer.setPlayerView(videoControlView.mVideoView);
        if (listener()!=null){
            listener().onChange();
        }
        setListener(videoControlView);
        mLivePlayer.startPlay(mPlayUrl,mPlayType);
        switch (mState){
            //根据状态修改现在播放器状态
        }
    }

    public void startPlay(String rtmpUrl) {
        checkInit();
        setPlayUrl(rtmpUrl);
        mLivePlayer.startPlay(mPlayUrl,mPlayType);
        mState = CURRENT_STATE_PREPAREING;//准备状态
    }


    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        Log.i(TAG, " " + bundle.toString() + " event " + event);
        switch (event) {
            case TXLiveConstants.PLAY_EVT_CONNECT_SUCC:
                Log.i(TAG, "//连接服务器成功");
                break;
            case TXLiveConstants.PLAY_EVT_RTMP_STREAM_BEGIN:
                Log.i(TAG, "//rtmp 开始拉流");
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_BEGIN:
                //rtmp 开始播放
                Log.i(TAG, "//rtmp 开始播放");
                listener().onPlaying();
                mState = CURRENT_STATE_PLAYING;
                break;
            case TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER:
                Log.i(TAG, "//正在解码");
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_END:
                Log.i(TAG, "//播放结束");
                mState = CURRENT_STATE_AUTO_COMPLETE;
                //播放结束
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_LOADING:
                Log.i(TAG, "//加载中 缓冲中");
                listener().onLoading();
                mState = CURRENT_STATE_PREPAREING;
                //加载中
                break;
            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT:
                //网络连接失败
                Log.i(TAG, "//网络连接失败");
                listener().onConnectError();
                break;
            case TXLiveConstants.PLAY_WARNING_RECONNECT:
                //拉取失败
                Log.i(TAG, "//拉取失败 启动网络冲连");
                listener().onWarningReconnect();
                break;
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {
        Log.i(TAG, "onNetStatus " + bundle.toString());
    }

    public void onResume() {
        if (!TextUtils.isEmpty(mPlayUrl)){
            checkInit();
           this.startPlay(mPlayUrl);
        }
    }

    public void stopPlay() {
        checkInit();
        mLivePlayer.stopPlay(true); //移除最后一帧
    }

    public void pause() {
        checkInit();
        mLivePlayer.stopPlay(false);
        listener().onVideoPause();
        mState = CURRENT_STATE_PAUSE;
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


    public boolean isPause() {
        return mState == CURRENT_STATE_PAUSE;
    }

    public void destory() {
        checkInit();
        mPlayUrl = "";



//        if (mLivePlayer.isPlaying()){
//
//        }
//        mLivePlayer.resume();
    }
}
