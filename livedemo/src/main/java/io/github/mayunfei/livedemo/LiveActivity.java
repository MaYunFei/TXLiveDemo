package io.github.mayunfei.livedemo;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dongao.kaoqian.livesocketlib.WsManager;
import com.dongao.kaoqian.livesocketlib.listener.WsStatusListener;
import com.dongao.kaoqian.livesocketlib.live.ILiveManager;
import com.dongao.kaoqian.livesocketlib.live.IWhiteBoard;
import com.dongao.kaoqian.livesocketlib.message.ClassBeginMessage;
import com.dongao.kaoqian.livesocketlib.message.ClassRestMessage;
import com.dongao.kaoqian.livesocketlib.message.LiveMessage;
import com.dongao.kaoqian.livesocketlib.message.MessageFactory;
import com.tencent.imsdk.TIMCallBack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;

import io.github.mayunfei.imsdk.Constant;
import io.github.mayunfei.imsdk.business.InitBusiness;
import io.github.mayunfei.imsdk.business.LoginBusiness;
import io.github.mayunfei.imsdk.ui.ChatFragment;
import io.github.mayunfei.livedemo.ui.DragGroupView;
import io.github.mayunfei.livedemo.ui.ScaleLiveView;
import io.github.mayunfei.livedemo.ui.Whiteboard;
import io.github.mayunfei.livedemo.websocket.WebSocketUtil;
import io.github.mayunfei.utilslib.DensityUtils;
import io.github.mayunfei.utilslib.L;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.BufferedSink;
import okio.ByteString;
import okio.GzipSink;
import okio.Okio;
import okio.Sink;

public class LiveActivity extends AppCompatActivity implements View.OnClickListener, TIMCallBack ,ChatFragment.OnFragmentInteractionListener,ILiveManager {

    private static final String TAG = "LiveActivity";
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

    private DragGroupView mDragGroupView;
    /**
     * 视频在大屏幕中
     */
    boolean isVideoInBigScreen = true;


    private float mScreenWidth;
    private float mScreenHeight;
    private WsManager wsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_live);
        mScaleLiveView = (ScaleLiveView) findViewById(R.id.live_view);
        mWhiteboard = (Whiteboard) findViewById(R.id.white_board);
        mSmallScreen = (ViewGroup) findViewById(R.id.id_small_screen);
        mBigScreen = (ViewGroup) findViewById(R.id.id_big_screen);
        mDragGroupView = (DragGroupView) findViewById(R.id.drag_group_view);
        mBigScreenParent = (ViewGroup) findViewById(R.id.big_screen_parent);

        findViewById(R.id.full_screen).setOnClickListener(this);
        findViewById(R.id.switch_screen).setOnClickListener(this);
        mScaleLiveView.setUrl("rtmp://pull.live.dongaocloud.com/live/8250_100159_cif");
        mScreenWidth = DensityUtils.getWidthInPx(this);
        mScreenHeight = DensityUtils.getHeightInPx(this);
        initIm();
        initSocket();

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
                    liveMessage.handleMessage(LiveActivity.this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.full_screen:
                fullScreen();
                break;
            case R.id.switch_screen:
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
                break;
        }
    }

    private void fullScreen() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) { //全屏
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

        } else {
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) { //竖屏
                //在转屏之前
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
        }


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
    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onSuccess() {
        Log.e("IMSDK","success");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(R.id.frame_chat, ChatFragment.newInstance(Constant.GROUP_ID));
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
}
