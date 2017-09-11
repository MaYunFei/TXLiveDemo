package io.github.mayunfei.simple;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tencent.rtmp.ui.TXCloudVideoView;

import io.github.mayunfei.simple.player.LivePlayerManager;

/**
 * Created by mayunfei on 17-9-7.
 */

public class PlayFrameLayout extends FrameLayout {
    protected ImageView mPPT;
    protected TXCloudVideoView mTxCloudVideoView;

    public PlayFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public PlayFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mTxCloudVideoView = new TXCloudVideoView(context);
        mTxCloudVideoView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mPPT = new ImageView(context);
        mPPT.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mTxCloudVideoView);
        addView(mPPT);
    }

    public void showPPt(){
        mPPT.setVisibility(VISIBLE);
        mPPT.setImageResource(R.mipmap.ic_launcher);
        mTxCloudVideoView.setVisibility(GONE);
    }

    public void showVideo(){
        mPPT.setVisibility(GONE);
        mTxCloudVideoView.setVisibility(VISIBLE);
    }

    public void onDestroy(){
        mTxCloudVideoView.onDestroy();
        LivePlayerManager.getInstance().stopPlay();
    }

    public void onPause(){
        mTxCloudVideoView.onPause();
        LivePlayerManager.getInstance().pause();

    }

    public void onResume(){
        mTxCloudVideoView.onResume();
    }

    public void play(String rtmpUrl){
        LivePlayerManager.getInstance().setPlayerView(mTxCloudVideoView);
        LivePlayerManager.getInstance().startPlay(rtmpUrl);
    }

    public void pause(){
        LivePlayerManager.getInstance().pause();
    }



    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
