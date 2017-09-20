package com.donagao.kaoqian.imsdk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.donagao.kaoqian.imsdk.R;
import com.donagao.kaoqian.imsdk.model.Message;
import com.donagao.kaoqian.imsdk.model.MessageFactory;
import com.donagao.kaoqian.imsdk.model.TextMessage;
import com.donagao.kaoqian.imsdk.presenter.ChatPresenter;
import com.donagao.kaoqian.imsdk.viewfeatures.ChatView;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageStatus;
import com.tencent.imsdk.ext.message.TIMMessageDraft;
import com.tencent.imsdk.ext.message.TIMMessageLocator;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.input;


public class ChatFragment extends Fragment implements ChatView{

    private static final String TAG = "ChatFragment";

    private static final String IDENTIFY = "identify";

    private ChatPresenter presenter;
    private ListView listView;
    private ChatAdapter adapter;

    private List<Message> messageList = new ArrayList<>();

    public static ChatFragment newInstance(String identify) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(IDENTIFY, identify);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String identify = getArguments().getString(IDENTIFY);
            presenter = new ChatPresenter(this, identify, TIMConversationType.Group);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new ChatAdapter(getContext(),R.layout.item_message,messageList);

        listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        input.setInputMode(ChatInput.InputMode.NONE);
                        break;
                }
                return false;
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int firstItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && firstItem == 0) {
                    //如果拉到顶端读取更多消息
                    presenter.getMessage(messageList.size() > 0 ? messageList.get(0).getMessage() : null);

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                firstItem = firstVisibleItem;
            }
        });
        registerForContextMenu(listView);
        presenter.start();
    }


    @Override
    public void showMessage(TIMMessage message) {
        if (message == null) {
            adapter.notifyDataSetChanged();
        } else {
            Message mMessage = MessageFactory.getMessage(message);
            if (mMessage != null) {
                if (mMessage instanceof TextMessage){
                    if (messageList.size()==0){
                        mMessage.setHasTime(null);
                    }else{
                        mMessage.setHasTime(messageList.get(messageList.size()-1).getMessage());
                    }
                    messageList.add(mMessage);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(adapter.getCount()-1);
                }

            }
        }
    }

    @Override
    public void showMessage(List<TIMMessage> messages) {
        int newMsgNum = 0;
        for (int i = 0; i < messages.size(); ++i){
            Message mMessage = MessageFactory.getMessage(messages.get(i));
            if (mMessage == null || messages.get(i).status() == TIMMessageStatus.HasDeleted) continue;
//            if (mMessage instanceof CustomMessage && (((CustomMessage) mMessage).getType() == CustomMessage.Type.TYPING ||
//                    ((CustomMessage) mMessage).getType() == CustomMessage.Type.INVALID)) continue;
            ++newMsgNum;
            if (i != messages.size() - 1){
                mMessage.setHasTime(messages.get(i+1));
                messageList.add(0, mMessage);
            }else{
                mMessage.setHasTime(null);
                messageList.add(0, mMessage);
            }
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(newMsgNum);
    }

    @Override
    public void showRevokeMessage(TIMMessageLocator timMessageLocator) {

    }

    @Override
    public void clearAllMessage() {
        messageList.clear();
    }

    @Override
    public void onSendMessageSuccess(TIMMessage message) {
        showMessage(message);
    }

    @Override
    public void onSendMessageFail(int code, String desc, TIMMessage message) {
        long id = message.getMsgUniqueId();
        for (Message msg : messageList){
            if (msg.getMessage().getMsgUniqueId() == id){
                switch (code){
                    case 80001:
                        //发送内容包含敏感词
                        msg.setDesc("内容含有敏感词");
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        }

        adapter.notifyDataSetChanged();
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
