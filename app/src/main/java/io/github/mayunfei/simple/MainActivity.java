package io.github.mayunfei.simple;

import android.support.v4.app.Fragment;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ITXLivePlayListener {
    private DragGroup content;
    private TXCloudVideoView mPlayerView;
    private TXCloudVideoView video_view_small;
    private TXLivePlayer mLivePlayer;
    private FrameLayout play_fragment;
    ViewDragHelper viewDragHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mPlayerView即step1中添加的界面view
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        video_view_small = (TXCloudVideoView) findViewById(R.id.video_view_small);
        content = (DragGroup) findViewById(R.id.content);
        //创建player对象
        mLivePlayer = new TXLivePlayer(this);
        //关键player对象与界面view
        mLivePlayer.setPlayerView(mPlayerView);
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);

        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_resume).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_full).setOnClickListener(this);
        findViewById(R.id.btn_adjust).setOnClickListener(this);
        findViewById(R.id.btn_small).setOnClickListener(this);
        play_fragment = (FrameLayout) findViewById(R.id.play_fragment);
//        video_view_small.setOnTouchListener(new View.OnTouchListener() {
//
//            public int _yDelta;
//            public int _xDelta;
//            public int mDownY;
//            public int mDownX;
//
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                final int X = (int) event.getRawX();
//                final int Y = (int) event.getRawY();
//
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                        mDownX = X;
//                        mDownY = Y;
//
//                        FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view
//                                .getLayoutParams();
//                        _xDelta = X - lParams.leftMargin;
//                        _yDelta = Y - lParams.topMargin;
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        if (Math.abs(mDownY - Y) < 5 && Math.abs(mDownX - X) < 5) {
//                            return false;
//                        } else {
//                            return true;
//                        }
//                    case MotionEvent.ACTION_MOVE:
//                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view
//                                .getLayoutParams();
//
//                        layoutParams.leftMargin = X - _xDelta;
//                        layoutParams.topMargin = Y - _yDelta;
//
//                        //不能超过屏幕上下左右的位置
//                        if (layoutParams.leftMargin >= play_fragment.getWidth()) {
//                            layoutParams.leftMargin = play_fragment.getWidth();
//                        }
//
//                        if (layoutParams.topMargin >= play_fragment.getHeight()) {
//                            layoutParams.topMargin = play_fragment.getHeight();
//                        }
//
//                        if (layoutParams.leftMargin <= 0) {
//                            layoutParams.leftMargin = 0;
//                        }
//
//                        if (layoutParams.topMargin <= 0) {
//                            layoutParams.topMargin = 0;
//                        }
//
//                        view.setLayoutParams(layoutParams);
//
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                start();
                break;
            case R.id.btn_pause:
                //暂停
                pause();
                break;
            case R.id.btn_resume:
                //继续
                resume();
                break;
            case R.id.btn_adjust:
                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
                break;
            case R.id.btn_full:
                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
                break;
            case R.id.btn_small:
                mLivePlayer.setPlayerView(video_view_small);
                break;
        }
    }


    private void start() {
        String flvUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
    }

    private void resume() {
        mLivePlayer.resume();
    }

    private void pause() {
        mLivePlayer.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLivePlayer!=null){
            mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
            mLivePlayer = null;
        }
        if (mPlayerView != null){
            mPlayerView.onDestroy();
            mPlayerView = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPlayEvent(int i, Bundle bundle) {

    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }
}
