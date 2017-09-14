package io.github.mayunfei.simple.activity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.tencent.imsdk.TIMCallBack;

import io.github.mayunfei.imsdk.Constant;
import io.github.mayunfei.imsdk.business.InitBusiness;
import io.github.mayunfei.imsdk.business.LoginBusiness;
import io.github.mayunfei.simple.DragGroup;
import io.github.mayunfei.simple.player.DensityUtils;
import io.github.mayunfei.simple.player.LivePlayerManager;
import io.github.mayunfei.simple.R;
import io.github.mayunfei.simple.player.SmallScreen;
import io.github.mayunfei.simple.player.StandardViewPlayer;

public class SecondActivity extends AppCompatActivity implements StandardViewPlayer.VideoPlayerFullScreenListener, TIMCallBack ,ChatFragment.OnFragmentInteractionListener {

    private StandardViewPlayer mPlayer;
    Button btn_change;
    private SmallScreen smallScreen;
    private DragGroup dragGroup;
    private float mScreenWidth;
    private float mScreenHeight;
    boolean isSmall = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        LivePlayerManager.getInstance().init(this);
        mPlayer = (StandardViewPlayer) findViewById(R.id.player);
        dragGroup = (DragGroup) findViewById(R.id.dragGroup);
        mPlayer.play("rtmp://pull.live.dongaocloud.com/live/8250_100159_cif");
        btn_change = (Button) findViewById(R.id.btn_change);
        smallScreen = (SmallScreen) findViewById(R.id.id_small_screen);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSmall){
                    LivePlayerManager.getInstance().setPlayerView(mPlayer);
                }else {
                    LivePlayerManager.getInstance().setPlayerView(smallScreen);
                }
                isSmall = !isSmall;
            }
        });
        mPlayer.setVideoPlayerFullScreenListener(this);
        mScreenWidth = DensityUtils.getWidthInPx(this);
        mScreenHeight = DensityUtils.getHeightInPx(this);
        InitBusiness.start(this);
        LoginBusiness.loginIm("yunfei", Constant.Sig,this);

//        FrameLayout.LayoutParams smallScreenLayoutParams = (FrameLayout.LayoutParams) smallScreen.getLayoutParams();
//        smallScreenLayoutParams.leftMargin = (int) (mScreenWidth - DensityUtils.dip2px(this,200));
//        smallScreenLayoutParams.topMargin = (int) (DensityUtils.dip2px(this,200));
//        smallScreen.setLayoutParams(smallScreenLayoutParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.onDestroy();
        LivePlayerManager.getInstance().destory();
    }

    @Override
    public void onVideoPlayerFullScreen(boolean isScreen) {
        if (isScreen){
            if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){ //全屏

                dragGroup.setType(DragGroup.TYPE_LANDSCAPE);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                ViewGroup.LayoutParams layoutParams = mPlayer.getLayoutParams();
                layoutParams.height  = (int) mScreenWidth;
                layoutParams.width = (int) mScreenHeight;
                mPlayer.setLayoutParams(layoutParams);
                //需要重新计算
//                FrameLayout.LayoutParams smallScreenLayoutParams = (FrameLayout.LayoutParams) smallScreen.getLayoutParams();
//                smallScreenLayoutParams.leftMargin = (int) (mScreenHeight - DensityUtils.dip2px(this,200));
//                smallScreenLayoutParams.topMargin = (int) (mScreenWidth - DensityUtils.dip2px(this,112.5f));
//                smallScreen.setLayoutParams(smallScreenLayoutParams);


            }
        }else {
            if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){ //竖屏
//                dragGroup.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                dragGroup.setType(DragGroup.TYPE_PORTRAIT);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                ViewGroup.LayoutParams layoutParams = mPlayer.getLayoutParams();
                layoutParams.height  = (int) mScreenHeight;
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = DensityUtils.dip2px(this,200);
                mPlayer.setLayoutParams(layoutParams);
//                FrameLayout.LayoutParams smallScreenLayoutParams = (FrameLayout.LayoutParams) smallScreen.getLayoutParams();
//                smallScreenLayoutParams.leftMargin = (int) (mScreenWidth - DensityUtils.dip2px(this,200));
//                smallScreenLayoutParams.topMargin = (int) (DensityUtils.dip2px(this,200));
//                smallScreen.setLayoutParams(smallScreenLayoutParams);

            }
        }

    }

    @Override
    public void onError(int i, String s) {
        Log.e("IMSDK ","error " +s);
    }

    @Override
    public void onSuccess() {
        //登录成功
        Log.e("IMSDK","success");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(R.id.frame_chat,ChatFragment.newInstance(Constant.GROUP_ID));
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
