package io.github.mayunfei.livelib;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXVideoEditConstants;

/**
 * Created by mayunfei on 17-9-14.
 */

public class LivePlayer implements ITXLivePlayListener {

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
    private static final String TAG = "LivePlayer";

    private TXLivePlayer mLivePlayer;
    private int mPlayType;
    private String mPlayUrl;


    private LiveListener mLiveListener;

    public void setmLiveListener(LiveListener mLiveListener) {
        this.mLiveListener = mLiveListener;
    }

    /**
     * 记录播放状态
     */
    private int mState = CURRENT_STATE_NORMAL;

    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        Log.i(TAG, " " + bundle.toString() + " event " + event);
        switch (event) {
            case TXLiveConstants.PLAY_EVT_CONNECT_SUCC:
                Log.i(TAG, "//连接服务器成功");
                mLiveListener.onConnectSuccess();
                break;
            case TXLiveConstants.PLAY_EVT_RTMP_STREAM_BEGIN:
                Log.i(TAG, "//rtmp 开始拉流");
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_BEGIN:
                //rtmp 开始播放
                Log.i(TAG, "//rtmp 开始播放");
                mLiveListener.onVideoPlaying();
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
                Log.i(TAG, "//加载中 缓冲中" + bundle);
                mLiveListener.onVideoLoading();
                mState = CURRENT_STATE_PREPAREING;
                //加载中
                break;
            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT:
                //网络连接失败
                Log.i(TAG, "//网络连接失败");
                mLiveListener.onVideoConnectError();
                break;
            case TXLiveConstants.PLAY_WARNING_RECONNECT:
                //拉取失败
                Log.i(TAG, "//拉取失败 启动网络冲连");
                mLiveListener.onVideoConnectError();
                break;
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {
        int fps = bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS);
        //TODO 应该判断是否在播放
        mLiveListener.onVideoNetEvent();
    }

    public LivePlayer(Context context) {
        this.mLivePlayer = new TXLivePlayer(context.getApplicationContext());
    }

    /**
     * 设置播放链接
     */
    public void setPlayUrl(String playUrl){
        mPlayType = Util.checkLiveUrl(playUrl);
        mPlayUrl = playUrl;
    }

    public void play(String url){
        setPlayUrl(url);
        resume();
    }

    public void resume() {
        mLivePlayer.startPlay(mPlayUrl, mPlayType);
    }

    /**
     * 停止拉流
     *
     * @param isShowLastImg 是否保存最后一帧
     */
    public void stop(boolean isShowLastImg) {
        mLivePlayer.stopPlay(isShowLastImg);
    }

    /**
     * 绑定播放view
     */
    public void setPlayerView(TXCloudVideoView videoView) {
        mLivePlayer.setPlayerView(videoView);
        mLiveListener.onVideoPrepared();
    }

    /**
     * 清空播放器释放内存
     */
    public void destory() {
        mLivePlayer.stopPlay(false);
        mLivePlayer = null;
    }
}
