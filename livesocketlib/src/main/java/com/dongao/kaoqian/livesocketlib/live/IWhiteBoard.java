package com.dongao.kaoqian.livesocketlib.live;


import com.dongao.kaoqian.livesocketlib.message.ChangePPTMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawClearMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawEllipseMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawEraseMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawLineMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawRectMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawTextMessage;

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
     */
    void changePPPT(ChangePPTMessage message);
}
