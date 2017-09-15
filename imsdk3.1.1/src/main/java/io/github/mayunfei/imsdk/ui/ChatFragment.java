package io.github.mayunfei.imsdk.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;

import java.util.ArrayList;
import java.util.List;

import io.github.mayunfei.imsdk.R;
import io.github.mayunfei.imsdk.presenter.ChatPresenter;
import io.github.mayunfei.imsdk.ui.model.Message;
import io.github.mayunfei.imsdk.ui.model.MessageFactory;
import io.github.mayunfei.imsdk.ui.model.TextMessage;
import io.github.mayunfei.imsdk.viewfeatures.ChatView;

public class ChatFragment extends Fragment implements ChatView {
    private static final String TAG = "ChatFragment";

    private static final String IDENTIFY = "IDENTIFY";
    private OnFragmentInteractionListener mListener;
    private String identify;
    private ChatPresenter mPresenter;

    private ChatAdapter adapter;
    private ListView listView;
    private List<Message> messageList = new ArrayList<>();

    private ChatInput input;

    public ChatFragment() {
        // Required empty public constructor
    }

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
            identify = getArguments().getString(IDENTIFY);
            mPresenter = new ChatPresenter(this, identify, TIMConversationType.Group);
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
        input = view.findViewById(R.id.input_panel);
        input.setChatView(this);
        adapter = new ChatAdapter(getContext(),R.layout.item_message,messageList);
        listView.setAdapter(adapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        input.setInputMode(ChatInput.InputMode.NONE);
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
//                    mPresenter.getMessage(messageList.size() > 0 ? messageList.get(0).getMessage() : null);

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                firstItem = firstVisibleItem;
            }
        });
        mPresenter.start();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.stop();
        mListener = null;
    }

    @Override
    public void showMessage(TIMMessage message) {
        if (message == null) {
            adapter.notifyDataSetChanged();
        } else {
            Message mMessage = MessageFactory.getMessage(message);
            if (mMessage != null) {
                if (!(mMessage instanceof TextMessage)){
//                    CustomMessage.Type messageType = ((CustomMessage) mMessage).getType();
//                    switch (messageType){
//                        case TYPING:
//                            TemplateTitle title = (TemplateTitle) findViewById(R.id.chat_title);
//                            title.setTitleText(getString(R.string.chat_typing));
//                            handler.removeCallbacks(resetTitle);
//                            handler.postDelayed(resetTitle,3000);
//                            break;
//                        default:
//                            break;
//                    }
                }else{
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
        Message message = new TextMessage(input.getText());
        mPresenter.sendMessage(message.getMessage());
        input.setText("");
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
    public void videoAction() {

    }

    @Override
    public void showToast(String msg) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
