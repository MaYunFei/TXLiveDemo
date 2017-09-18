package io.github.mayunfei.wbesocket.message;

import io.github.mayunfei.wbesocket.live.ILiveManager;

/**
 * Created by mayunfei on 17-9-18.
 */

public class DrawClearMessage extends WhiteBoardMessage {

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.getWriteBoard().drawClearAll(this);
    }
}
