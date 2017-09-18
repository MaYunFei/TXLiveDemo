package io.github.mayunfei.wbesocket.message;

import io.github.mayunfei.wbesocket.live.ILiveManager;

/**
 * 橡皮擦
 */

public class DrawEraseMessage extends WhiteBoardMessage {

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.getWriteBoard().drawErase(this);
    }
}
