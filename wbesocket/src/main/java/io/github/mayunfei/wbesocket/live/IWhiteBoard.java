package io.github.mayunfei.wbesocket.live;

import io.github.mayunfei.wbesocket.message.ChangePPTMessage;
import io.github.mayunfei.wbesocket.message.DrawClearMessage;
import io.github.mayunfei.wbesocket.message.DrawEllipseMessage;
import io.github.mayunfei.wbesocket.message.DrawEraseMessage;
import io.github.mayunfei.wbesocket.message.DrawLineMessage;
import io.github.mayunfei.wbesocket.message.DrawRectMessage;
import io.github.mayunfei.wbesocket.message.DrawTextMessage;

/**
 * Created by mayunfei on 17-9-18.
 */

public interface IWhiteBoard {
    /**
     * 画方
     */
    void drawLine(DrawLineMessage message);

    /**
     * 画圆
     */
    void drawEllipse(DrawEllipseMessage message);

    /**
     * 清空全部
     */
    void drawClearAll(DrawClearMessage message);

    /**
     * 画矩形
     */
    void drawRect(DrawRectMessage message);

    /**
     * 橡皮擦
     */
    void drawErase(DrawEraseMessage message);

    /**
     * 写字
     */
    void drawText(DrawTextMessage message);

    /**
     * 切换ppt
     * @param message
     */
    void changePPPT(ChangePPTMessage message);
}
