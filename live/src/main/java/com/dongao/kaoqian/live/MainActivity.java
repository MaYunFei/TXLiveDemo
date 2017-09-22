package com.dongao.kaoqian.live;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.donagao.kaoqian.imsdk.Constant;
import com.donagao.kaoqian.imsdk.business.InitBusiness;
import com.donagao.kaoqian.imsdk.business.LoginBusiness;
import com.donagao.kaoqian.imsdk.ui.ChatFragment;
import com.dongao.kaoqian.live.ui.ControlView;
import com.dongao.kaoqian.live.ui.DragGroupView;
import com.dongao.kaoqian.live.ui.ScaleLiveView;
import com.dongao.kaoqian.live.ui.Whiteboard;
import com.dongao.kaoqian.livesocketlib.WsManager;
import com.dongao.kaoqian.livesocketlib.listener.WsStatusListener;
import com.dongao.kaoqian.livesocketlib.live.ILiveManager;
import com.dongao.kaoqian.livesocketlib.live.IWhiteBoard;
import com.dongao.kaoqian.livesocketlib.message.ClassBeginMessage;
import com.dongao.kaoqian.livesocketlib.message.ClassRestMessage;
import com.dongao.kaoqian.livesocketlib.message.LiveMessage;
import com.dongao.kaoqian.livesocketlib.message.MessageFactory;
import com.tencent.imsdk.TIMCallBack;

import io.github.mayunfei.utilslib.DensityUtils;
import okhttp3.Response;
import okio.ByteString;

public class MainActivity extends AppCompatActivity implements ControlView.MediaControlListener, ILiveManager, View.OnClickListener, TIMCallBack,ChatFragment.OnChatFragmentListener {

    private static final String TAG = "MainActivity";
    /**
     * 播放器view
     */
    private ScaleLiveView mScaleLiveView;
    /**
     * 白板 view
     */
    private Whiteboard mWhiteboard;
    /**
     * 添加到大屏的
     */
    private ViewGroup mBigScreen;
    /**
     * 添加到小屏
     */
    private ViewGroup mSmallScreen;

    private ViewGroup mBigScreenParent;
    private ViewGroup mSmallScreenParent;

    private DragGroupView mDragGroupView;
    /**
     * 视频在大屏幕中
     */
    boolean isVideoInBigScreen = true;

    private ControlView layout_control;
    private float mScreenWidth;
    private float mScreenHeight;

    /**
     * socket
     */
    private WsManager wsManager;
    private ChatFragment mChatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
//        initLive();
        initSocket();
//        initIm();
    }

    private void initLive() {
        mScaleLiveView.setUrl("rtmp://pull.live.dongaocloud.com/live/8250_100159_cif");
    }

    private void initView() {
        mScreenWidth = DensityUtils.getWidthInPx(this);
        mScreenHeight = DensityUtils.getHeightInPx(this);
        mScaleLiveView = (ScaleLiveView) findViewById(R.id.live_view);
        mWhiteboard = (Whiteboard) findViewById(R.id.white_board);
        mSmallScreen = (ViewGroup) findViewById(R.id.layout_small_screen);
        mBigScreen = (ViewGroup) findViewById(R.id.layout_big_screen);
        mDragGroupView = (DragGroupView) findViewById(R.id.drag_group_view);
        mBigScreenParent = (ViewGroup) findViewById(R.id.big_screen_parent);
        mSmallScreenParent = (ViewGroup) findViewById(R.id.id_small_screen);
        layout_control = (ControlView) findViewById(R.id.layout_control);
        findViewById(R.id.iv_small_close).setOnClickListener(this);
    }

    private void initListener() {
        layout_control.setMediaControlListener(this);
    }

    private void initSocket() {
        WsManager.Builder builder = new WsManager.Builder(this).needReconnect(true).wsUrl("ws://dev.ws.lvb.dongaocloud.tv/sub?id=100159");
        wsManager = builder.build();
        wsManager.startConnect();
        wsManager.setWsStatusListener(new WsStatusListener() {
            @Override
            public void onMessage(String text) {
                super.onMessage(text);
                Log.i(TAG,"onMessage");
                LiveMessage liveMessage = MessageFactory.messagePause(text);
                if (liveMessage!=null){
                    liveMessage.handleMessage(MainActivity.this);
                }
            }

            @Override
            public void onOpen(Response response) {
                super.onOpen(response);
                Log.i(TAG,"onOpen");
            }

            @Override
            public void onMessage(ByteString bytes) {
                super.onMessage(bytes);
            }

            @Override
            public void onReconnect() {
                super.onReconnect();
                Log.i(TAG,"onReconnect");
            }

            @Override
            public void onClosing(int code, String reason) {
                super.onClosing(code, reason);
                Log.i(TAG,"onClosing");
            }

            @Override
            public void onClosed(int code, String reason) {
                super.onClosed(code, reason);
                Log.i(TAG,"onClosed");
            }

            @Override
            public void onFailure(Throwable t, Response response) {
                super.onFailure(t, response);
                Log.i(TAG,"onFailure");
            }
        });
    }


    private void initIm() {
        InitBusiness.start(this);
        LoginBusiness.loginIm("yunfei", Constant.Sig,this);
    }

    /**
     * 全屏
     */
    @Override
    public void onFullScreenClick() {
        if (!isFullScreen()) {
            fullScreen();
            if (mChatFragment!=null){
                mChatFragment.hideSoftKeyBoard();
            }
        }
        layout_control.hideFullScreen();
    }

    /**
     * 切换
     */
    @Override
    public void onChangeClick() {
        if (isVideoInBigScreen) {
            mBigScreen.removeAllViews();
            mSmallScreen.removeAllViews();
            mBigScreen.addView(mWhiteboard);
            mSmallScreen.addView(mScaleLiveView);
            isVideoInBigScreen = false;
        } else {
            mBigScreen.removeAllViews();
            mSmallScreen.removeAllViews();
            mBigScreen.addView(mScaleLiveView);
            mSmallScreen.addView(mWhiteboard);
            isVideoInBigScreen = true;
        }
    }

    @Override
    public void onPPTClick() {
        mSmallScreenParent.setVisibility(View.VISIBLE);
        layout_control.showChange();
    }

    @Override
    public void onVideoClick() {
        mSmallScreenParent.setVisibility(View.VISIBLE);
        layout_control.showChange();
    }

    @Override
    public void onBackClick() {
        if (isFullScreen()) {
            smallScreen();
            layout_control.showFullScreen();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (isFullScreen()) {
            smallScreen();
            layout_control.showFullScreen();
        } else {
            finish();
        }
    }

    @Override
    public void onAudioClick() {

    }

    private void fullScreen() {
        //在转屏之前
        mDragGroupView.setType(DragGroupView.TYPE_LANDSCAPE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewGroup.LayoutParams layoutParams = mBigScreenParent.getLayoutParams();
        layoutParams.height = (int) mScreenWidth;
        layoutParams.width = (int) mScreenHeight;
        mBigScreenParent.setLayoutParams(layoutParams);

    }

    private void smallScreen() {
        mDragGroupView.setType(DragGroupView.TYPE_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGroup.LayoutParams layoutParams = mBigScreenParent.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mBigScreenParent.setLayoutParams(layoutParams);
    }

    private boolean isFullScreen() {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScaleLiveView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScaleLiveView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScaleLiveView.onDestory();
        wsManager.getWebSocket().cancel();
    }

    @Override
    public IWhiteBoard getWriteBoard() {
        return mWhiteboard;
    }

    @Override
    public void classRest(ClassRestMessage message) {

    }

    @Override
    public void classBegin(ClassBeginMessage classBeginMessage) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_small_close:

                if (isVideoInBigScreen){
                    mSmallScreenParent.setVisibility(View.GONE);
                    layout_control.showPPT();
                }else {
                    mSmallScreenParent.setVisibility(View.GONE);
                    layout_control.showVideo();
                }
                break;
        }
    }


    @Override
    public void onError(int i, String s) {
        Log.e("聊天模块",s);
    }

    @Override
    public void onSuccess() {
        Log.e("IMSDK","success");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
       mChatFragment = ChatFragment.newInstance(Constant.GROUP_ID);
        transaction.add(R.id.frame_chat, mChatFragment);
        transaction.commit();
    }

    @Override
    public void onKeyboardShow(boolean isShow) {
//        if (isShow){
//            mBigScreenParent.setVisibility(View.GONE);
//        }else {
//            mBigScreenParent.setVisibility(View.VISIBLE);
//        }
    }
}
