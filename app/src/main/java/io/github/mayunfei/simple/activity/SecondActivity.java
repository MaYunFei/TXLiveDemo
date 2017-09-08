package io.github.mayunfei.simple.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.mayunfei.simple.LivePlayerManager;
import io.github.mayunfei.simple.R;
import io.github.mayunfei.simple.player.StandardViewPlayer;

public class SecondActivity extends AppCompatActivity {

    private StandardViewPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        LivePlayerManager.getInstance().init(this);
        mPlayer = (StandardViewPlayer) findViewById(R.id.player);
        mPlayer.playRtmp("rtmp://pull.live.dongaocloud.com/live/8250_100159_cif");
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
    }
}
