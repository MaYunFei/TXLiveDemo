package io.github.mayunfei.livedemo.websocket;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

import io.github.mayunfei.utilslib.L;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.BufferedSource;
import okio.ByteString;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

/**
 * Created by mayunfei on 17-9-15.
 */

public class WebSocketUtil {
    private WebSocketUtil() {
    }

    public interface SocketListener {
        void onSocketConnected();

        void onClosed();

        void onFailure();
    }

    public static void init(OkHttpClient client, String url) {
        //构造request对象
        Request request = new Request.Builder()
                .url("ws://dev.ws.lvb.dongaocloud.tv/sub?id=100159")
                .build();

        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                L.i("开启socket");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                L.i("收到消息 MEssage ");
                String msg = uncompress(text);
                if (!TextUtils.isEmpty(msg)) {
                    L.i(msg);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                L.i("收到消息 ByteString ");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                L.i("收到消息 onClosing ");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                L.i("收到消息 onClosed ");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                L.i("收到消息 onFailure ");
            }
        });
        webSocket.cancel();
    }

    /**
     * 解压数据
     */
    static String uncompress(String str) {

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
            L.i(e.toString());
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
