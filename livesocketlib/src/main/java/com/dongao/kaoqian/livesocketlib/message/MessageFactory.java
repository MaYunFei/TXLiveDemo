package com.dongao.kaoqian.livesocketlib.message;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * 消息种类
 * Created by mayunfei on 17-9-18.
 */

public class MessageFactory {
    private static final String TAG = "MessageFactory";

    public static LiveMessage messagePause(String compressMsg) {
        String msgText = uncompress(compressMsg);
        Log.i(TAG, msgText);
        try {
            JSONObject jsonObject = new JSONObject(msgText);
            String type = jsonObject.optString("type");
            JSONObject data = jsonObject.optJSONObject("data");
            if (data == null) {
                return null;
            }
            switch (type) {
                case LiveMessage.TYPE_DRAW:

                    String method = data.getString("method");
                    switch (method) {
                        case LiveMessage.METHOD_LINE:
                            return JSON.parseObject(msgText, DrawLineMessage.class);
                        case LiveMessage.METHOD_ELLIPSE:
                            return JSON.parseObject(msgText, DrawEllipseMessage.class);
                        case LiveMessage.METHOD_RECT:
                            return JSON.parseObject(msgText, DrawRectMessage.class);
                        case LiveMessage.METHOD_ERASE:
                            return JSON.parseObject(msgText, DrawEraseMessage.class);
                        case LiveMessage.METHOD_CLEARALL:
                            return JSON.parseObject(msgText, DrawClearMessage.class);
                        case LiveMessage.METHOD_TEXT:
                            return JSON.parseObject(msgText, DrawTextMessage.class);

                    }
                    break;
                case LiveMessage.TYPE_PPT_CHANGE:
                    return JSON.parseObject(msgText, ChangePPTMessage.class);
                case LiveMessage.TYPE_CLASS_BEGIN:
                    return JSON.parseObject(msgText, ClassBeginMessage.class);
                case LiveMessage.TYPE_CLASS_REST:
                    return JSON.parseObject(msgText, ClassRestMessage.class);
            }

            return null;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解压数据
     */
    private static String uncompress(String str) {

        Inflater decompressor = new Inflater();
        ByteArrayOutputStream bos = null;
        byte[] value = null;
        try {
            value = str.getBytes("ISO-8859-1");
            bos = new ByteArrayOutputStream(value.length);
            decompressor.setInput(value);
            final byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                int count = 0;
                count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        } finally {
            decompressor.end();
        }
        if (bos != null) {

            return bos.toString();
        }
        return null;
    }
}
