package io.github.mayunfei.simple.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import io.github.mayunfei.simple.R;

/**
 * Created by mayunfei on 17-9-8.
 */

public class StandardViewPlayer extends VideoControlView {
    private static final String TAG = "StandardViewPlayer";

    public StandardViewPlayer(@NonNull Context context) {
        this(context, null);
    }

    public StandardViewPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.standard_player, this);

    }

    @Override
    public void onPrepared() {
        Log.i(TAG, "准备播放");
    }

    @Override
    public void onStart() {
        Log.i(TAG, "开始播放 切换UI");
    }

    @Override
    public void onLoading() {
        Log.i(TAG, "Loading");
    }

    @Override
    public void onConnectError() {

    }

    @Override
    public void onWarningReconnect() {

    }

    @Override
    public void onChange() {

    }

    @Override
    public void onVideoPause() {

    }

}
