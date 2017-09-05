# TXLiveDemo
腾讯直播demo
## SDK
直播sdk https://www.qcloud.com/document/product/454/7873

## 步骤
xml
```
<com.tencent.rtmp.ui.TXCloudVideoView
    android:id="@+id/video_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:visibility="visible"/>
```
TXLivePlayer
 mLivePlayer = new TXLivePlayer(this);
//关键player对象与界面view
mLivePlayer.setPlayerView(mPlayerView);

start
String flvUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);

绑定生命周期
