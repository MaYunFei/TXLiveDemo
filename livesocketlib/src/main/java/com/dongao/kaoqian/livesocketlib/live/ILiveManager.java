package com.dongao.kaoqian.livesocketlib.live;

import com.dongao.kaoqian.livesocketlib.message.ClassBeginMessage;
import com.dongao.kaoqian.livesocketlib.message.ClassRestMessage;

/**
 * Created by mayunfei on 17-9-18.
 */

public interface ILiveManager {
    /**
     * 获得白板的画布
     */
    IWhiteBoard getWriteBoard();

    /**
     * 课间休息
     */
    void classRest(ClassRestMessage message);


    /**
     * 上课
     * @param classBeginMessage
     */
    void classBegin(ClassBeginMessage classBeginMessage);
}
