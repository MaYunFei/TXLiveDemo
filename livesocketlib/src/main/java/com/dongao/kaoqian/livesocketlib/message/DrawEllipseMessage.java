package com.dongao.kaoqian.livesocketlib.message;


import com.dongao.kaoqian.livesocketlib.live.ILiveManager;

/**
 * Created by mayunfei on 17-9-18.
 */

public class DrawEllipseMessage extends WhiteBoardMessage {

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.getWriteBoard().drawEllipse(this);
    }
}
