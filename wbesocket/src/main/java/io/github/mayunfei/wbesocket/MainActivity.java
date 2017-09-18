package io.github.mayunfei.wbesocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.github.mayunfei.wbesocket.listener.WsStatusListener;
import io.github.mayunfei.wbesocket.live.IWhiteBoard;
import io.github.mayunfei.wbesocket.live.ILiveManager;
import io.github.mayunfei.wbesocket.message.LiveMessage;
import io.github.mayunfei.wbesocket.message.MessageFactory;
import io.github.mayunfei.wbesocket.ui.Whiteboard;
import okhttp3.Response;
import okio.ByteString;

public class MainActivity extends AppCompatActivity implements ILiveManager {
    private Whiteboard white_board;
    private WsManager wsManager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        white_board = (Whiteboard) findViewById(R.id.white_board);
        WsManager.Builder builder = new WsManager.Builder(this).needReconnect(true).wsUrl("ws://dev.ws.lvb.dongaocloud.tv/sub?id=100159");
        wsManager = builder.build();
        wsManager.startConnect();
        wsManager.setWsStatusListener(new WsStatusListener() {
            @Override
            public void onMessage(String text) {
                super.onMessage(text);
                Log.i(TAG,"onMessage");
                LiveMessage liveMessage = MessageFactory.messagePause(text);
                if (liveMessage!=null){
                    liveMessage.handleMessage(MainActivity.this);
                }
            }

            @Override
            public void onOpen(Response response) {
                super.onOpen(response);
                Log.i(TAG,"onOpen");
            }

            @Override
            public void onMessage(ByteString bytes) {
                super.onMessage(bytes);
            }

            @Override
            public void onReconnect() {
                super.onReconnect();
                Log.i(TAG,"onReconnect");
            }

            @Override
            public void onClosing(int code, String reason) {
                super.onClosing(code, reason);
                Log.i(TAG,"onClosing");
            }

            @Override
            public void onClosed(int code, String reason) {
                super.onClosed(code, reason);
                Log.i(TAG,"onClosed");
            }

            @Override
            public void onFailure(Throwable t, Response response) {
                super.onFailure(t, response);
                Log.i(TAG,"onFailure");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wsManager != null) {
            wsManager.stopConnect();
            wsManager = null;
        }
    }

    @Override
    public IWhiteBoard getWriteBoard() {
        return white_board;
    }

    @Override
    public void classRest() {

    }

    @Override
    public void classBegin() {

    }
}
