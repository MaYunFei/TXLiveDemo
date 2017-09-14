package io.github.mayunfei.livelib;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * Created by mayunfei on 17-9-14.
 */

public abstract class VideoControlView extends FrameLayout implements LiveListener {
    private final TXCloudVideoView mVideoView;
    private LivePlayer mPlayer;

    public VideoControlView(@NonNull Context context) {
        this(context, null);
    }

    public VideoControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoControlView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mVideoView = new TXCloudVideoView(context);
        addView(mVideoView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mPlayer = new LivePlayer(context);
        mPlayer.setPlayerView(mVideoView);
    }

    public void play(String url){
        if (!Util.checkUrl(url)){
            onPlayUrlError();
        return;
        }
        mPlayer.play(url);
    }

    public void onPause() {
        mVideoView.onPause();
        mPlayer.stop(true);
    }

    public void onResume() {
        mVideoView.onResume();
        mPlayer.resume();
    }


    public void onDestory() {
        mVideoView.onDestroy();
        mPlayer.destory();
    }
}
