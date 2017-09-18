package io.github.mayunfei.wbesocket.live;

import android.graphics.Canvas;

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
    void classRest();


    /**
     * 上课
     */
    void classBegin();
}
