package com.dongao.kaoqian.livesocketlib.message;


import com.dongao.kaoqian.livesocketlib.live.ILiveManager;

/**
 * 画线
 * Created by mayunfei on 17-9-18.
 */

public class DrawLineMessage extends WhiteBoardMessage {

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.getWriteBoard().drawLine(this);
    }
}
