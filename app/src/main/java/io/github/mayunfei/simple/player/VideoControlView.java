package io.github.mayunfei.simple.player;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * Created by mayunfei on 17-9-8.
 */

public abstract class VideoControlView extends FrameLayout implements LiveListener {

    TXCloudVideoView mVideoView;

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
    }

    public void play(@NonNull String url) {
//        LiveListener listener = LivePlayerManager.getInstance().listener();
//        if (listener != null) {
//            listener.onChange();
//        }
        LivePlayerManager.getInstance().setPlayerView(mVideoView);
        LivePlayerManager.getInstance().setListener(this);
        LivePlayerManager.getInstance().startPlay(url);
        onPrepared();
    }

    public void pause() {
        if (!LivePlayerManager.getInstance().isPause()) {
            mVideoView.onPause();
            LivePlayerManager.getInstance().pause();
        }
        onVideoPause();
    }

    public void onDestroy() {
        mVideoView.onDestroy();
    }


}
