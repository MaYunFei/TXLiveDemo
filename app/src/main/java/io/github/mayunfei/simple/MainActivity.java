package io.github.mayunfei.simple;

import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ITXLivePlayListener {
    private DragGroup content;
    private PlaySupportSmallFrameLayout mPlayerView;
//    private TXLivePlayer mLivePlayer;
    private FrameLayout play_fragment;
    ViewDragHelper viewDragHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        File path = getFilesDir();
//        Log.e("yunfei",path.getPath());
//        File m3u8 = new File(path.getPath()+File.separator+"online.m3u8");
//        File key = new File(path.getPath()+File.separator+"keyText.txt");
//        if (!m3u8.exists()){
//            saveFile(m3u8,"online.m3u8");
//            saveFile(key,"keyText.txt");
//        }
        PlayerManager.getInstance().init(this);
        setContentView(R.layout.activity_main);
        //mPlayerView即step1中添加的界面view
        mPlayerView = (PlaySupportSmallFrameLayout) findViewById(R.id.video_view);
        content = (DragGroup) findViewById(R.id.content);
        //创建player对象
        //关键player对象与界面view

        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_resume).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_full).setOnClickListener(this);
        findViewById(R.id.btn_adjust).setOnClickListener(this);
        findViewById(R.id.btn_small).setOnClickListener(this);
        play_fragment = (FrameLayout) findViewById(R.id.play_fragment);
        //testOkhttp();
    }

    private void testOkhttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //构造request对象
        Request request = new Request.Builder()
                .url("")
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
            }
        });
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
//                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
                break;
            case R.id.btn_full:
//                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
                break;
            case R.id.btn_small:
                mPlayerView.showSmallScreen();
//                mPlayerView.onPause();

//                mLivePlayer.setPlayerView(video_view_small);
//                start();
                break;
        }
    }


    private void start() {
        String flvUrl = "rtmp://pull.live.dongaocloud.com/live/8250_100159_cif";
        mPlayerView.play(flvUrl);
//        mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
    }

    private void resume() {
        mPlayerView.onResume();
    }

    private void pause() {
        mPlayerView.onPause();
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
//        if (mLivePlayer!=null){
//            mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
//            mLivePlayer = null;
//        }

        if (mPlayerView != null){
            mPlayerView.onDestroy();
            mPlayerView = null;
        }
//        if (video_view_small != null){
//            video_view_small.onDestroy();
//            video_view_small = null;
//        }
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

    public void saveFile(File saveFile,String name){
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = assetManager.open(name);
            outputStream = new BufferedOutputStream(new FileOutputStream(saveFile));
            byte data[] = new byte[1024];
            int len;
            while ((len = inputStream.read(data, 0, 1024)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream !=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
