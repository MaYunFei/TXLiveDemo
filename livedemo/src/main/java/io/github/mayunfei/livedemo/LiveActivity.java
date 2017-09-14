package io.github.mayunfei.livedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import io.github.mayunfei.livedemo.ui.ScaleLiveView;
import io.github.mayunfei.whiteboard.Whiteboard;

public class LiveActivity extends AppCompatActivity implements View.OnClickListener{

    private ScaleLiveView mScaleLiveView;
    private Whiteboard mWhiteboard;
    private ViewGroup mBigScreen;
    private ViewGroup mSmallScreen;
    /**
     * 视频在大屏幕中
     */
    boolean isVideoInBigScreen = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        mScaleLiveView = (ScaleLiveView) findViewById(R.id.live_view);
        mWhiteboard = (Whiteboard) findViewById(R.id.white_board);
        mSmallScreen = (ViewGroup) findViewById(R.id.id_small_screen);
        mBigScreen = (ViewGroup) findViewById(R.id.id_big_screen);
        findViewById(R.id.full_screen).setOnClickListener(this);
        findViewById(R.id.switch_screen).setOnClickListener(this);
        mScaleLiveView.play("rtmp://pull.live.dongaocloud.com/live/8250_100159_cif");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.full_screen:
                break;
            case R.id.switch_screen:
                if (isVideoInBigScreen){
                    mBigScreen.removeAllViews();
                    mSmallScreen.removeAllViews();
                    mBigScreen.addView(mWhiteboard);
                    mSmallScreen.addView(mScaleLiveView);
                    isVideoInBigScreen = false;
                }else {
                    mBigScreen.removeAllViews();
                    mSmallScreen.removeAllViews();
                    mBigScreen.addView(mScaleLiveView);
                    mSmallScreen.addView(mWhiteboard);
                    isVideoInBigScreen = true;
                }
                break;
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
//        mScaleLiveView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScaleLiveView.onDestory();
    }
}
