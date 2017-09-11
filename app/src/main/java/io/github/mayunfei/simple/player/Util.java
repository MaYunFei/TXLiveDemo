package io.github.mayunfei.simple.player;

import android.widget.Toast;

import com.tencent.rtmp.TXLivePlayer;

/**
 * Created by mayunfei on 17-9-11.
 */

public class Util {
    private Util() {
    }

    public static int checkLiveUrl(String playUrl) {
        if (playUrl.startsWith("rtmp://")) {
            return TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
        } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
            return TXLivePlayer.PLAY_TYPE_LIVE_FLV;
//        } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".m3u8")) {
//            return TXLivePlayer.PLAY_TYPE_VOD_HLS;//m3u8
        }
        else {
            return -1;
        }
    }

}
