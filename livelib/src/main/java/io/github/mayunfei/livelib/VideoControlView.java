package io.github.mayunfei.livelib;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * Created by mayunfei on 17-9-14.
 */

public abstract class VideoControlView extends FrameLayout implements LiveListener {
    private  TXCloudVideoView mVideoView;
    private LivePlayer mPlayer;

    public VideoControlView(@NonNull Context context) {
        this(context, null);
    }

    public VideoControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoControlView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        if (isInEditMode()){
            return;
        }
        mVideoView = new TXCloudVideoView(context);
        addView(mVideoView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mVideoView.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()){
            mPlayer = new LivePlayer(getContext());
            //设置监听事件
            mPlayer.setLiveListener(this);
            mPlayer.setPlayerView(mVideoView);
        }
    }

    public void setUrl(String url){
        if (!Util.checkUrl(url)){
            onPlayUrlError();
            return;
        }
        mPlayer.setPlayUrl(url);

    }

    public void play(String url){
        if (!Util.checkUrl(url)){
            onPlayUrlError();
        return;
        }
        mPlayer.play(url);
    }

    /**
     * 暂停
     */
    public void onPause() {
        mVideoView.onPause();
        mPlayer.stop(true);
    }

    /**
     * 重启
     */
    public void onResume() {
        mVideoView.onResume();
        mPlayer.resume();
    }


    /**
     * 销毁
     */
    public void onDestory() {
        mVideoView.onDestroy();
        mPlayer.destory();
    }
}
