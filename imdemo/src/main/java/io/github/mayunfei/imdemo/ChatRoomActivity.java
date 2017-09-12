package io.github.mayunfei.imdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.message.TIMMessageDraft;
import com.tencent.imsdk.ext.message.TIMMessageLocator;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.presenter.ChatPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ChatView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ChatRoomActivity extends AppCompatActivity implements Observer , ChatView {
    private ChatPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        GroupEvent.getInstance().addObserver(this);
        presenter = new ChatPresenter(this,"@TGS#3OY7224E2",TIMConversationType.Group);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof GroupEvent){
            if (data instanceof GroupEvent.NotifyCmd){
                GroupEvent.NotifyCmd cmd = (GroupEvent.NotifyCmd) data;
                switch (cmd.type){
                    case DEL:
//                        delGroup((String) cmd.data);
                        break;
                    case ADD:
//                        addGroup((TIMGroupCacheInfo) cmd.data);
                        break;
                    case UPDATE:
                        //群组消息
//                        updateGroup((TIMGroupCacheInfo) cmd.data);
                        break;
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GroupEvent.getInstance().deleteObserver(this);
    }

    @Override
    public void showMessage(TIMMessage message) {

    }

    @Override
    public void showMessage(List<TIMMessage> messages) {

    }

    @Override
    public void showRevokeMessage(TIMMessageLocator timMessageLocator) {

    }

    @Override
    public void clearAllMessage() {

    }

    @Override
    public void onSendMessageSuccess(TIMMessage message) {

    }

    @Override
    public void onSendMessageFail(int code, String desc, TIMMessage message) {

    }

    @Override
    public void sendImage() {

    }

    @Override
    public void sendPhoto() {

    }

    @Override
    public void sendText() {

    }

    @Override
    public void sendFile() {

    }

    @Override
    public void startSendVoice() {

    }

    @Override
    public void endSendVoice() {

    }

    @Override
    public void sendVideo(String fileName) {

    }

    @Override
    public void cancelSendVoice() {

    }

    @Override
    public void sending() {

    }

    @Override
    public void showDraft(TIMMessageDraft draft) {

    }

    @Override
    public void videoAction() {

    }

    @Override
    public void showToast(String msg) {

    }
}
