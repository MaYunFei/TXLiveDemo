package io.github.mayunfei.wbesocket.message;

/**
 * Created by mayunfei on 17-9-18.
 */

public abstract class LiveMessage  implements ILiveMessage{
    public static final String TYPE_DRAW = "draw";
    public static final String TYPE_PPTCHANGE = "pptChange";

    public static final String METHOD_LINE = "line";
    /**
     * 清屏
     */
    public static final String METHOD_CLEARALL = "clearAll";
    /**
     * 画圆
     */
    public static final String METHOD_ELLIPSE = "ellipse";
    /**
     * 写字
     */
    public static final String METHOD_TEXT = "text";
    /**
     * 矩形
     */
    public static final String METHOD_RECT = "rect";
    /**
     * 橡皮擦
     */
    public static final String METHOD_ERASE = "erase";

    /**
     * 切换ppt
     */

    public static final String PPT_BLACK = "black";

    private String type;

    @Override
    public String toString() {
        return "LiveMessage{" +
                "type='" + type + '\'' +
                '}';
    }
}
