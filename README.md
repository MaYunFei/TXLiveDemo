# TXLiveDemo
腾讯直播demo
## SDK
[直播sdk https://www.qcloud.com/document/product/454/7886](https://www.qcloud.com/document/product/454/7886)
[播放文档 https://www.qcloud.com/document/product/454/788 ](https://www.qcloud.com/document/product/454/7886)

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
mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);程度

绑定生命周期


## ijkplayer
业务上需要播放加密的 m3u8文件 在ijkplayer中找到选项
[https://github.com/Bilibili/ijkplayer/issues/2348](https://github.com/Bilibili/ijkplayer/issues/2348)
业务上 链接是加密的 所以要开启 openssl

export COMMON_FF_CFG_FLAGS="$COMMON_FF_CFG_FLAGS --enable-openssl"
remove --disable-protocol=crypto in config/module-lite.sh and recompile it.

```
./init-android-openssl.sh
./init-android.sh
./compile-openssl.sh clean//清除
./compile-ffmpeg.sh clean//清除
./compile-openssl.sh all//编译
./compile-ffmpeg.sh all//编译
./compile-ijk.sh all
```

## 问题
没有暂停和恢复
不能实时去替换播放的view ，每次设置之后需要重新开始播放


