package io.github.mayunfei.simple.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMMessage;

import java.util.ArrayList;
import java.util.List;

import io.github.mayunfei.imsdk.presenter.ChatPresenter;
import io.github.mayunfei.imsdk.ui.ChatAdapter;
import io.github.mayunfei.imsdk.ui.model.Message;
import io.github.mayunfei.imsdk.ui.model.MessageFactory;
import io.github.mayunfei.imsdk.ui.model.TextMessage;
import io.github.mayunfei.imsdk.viewfeatures.ChatView;
import io.github.mayunfei.simple.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements ChatView {
    private static final String TAG = "ChatFragment";

    private static final String IDENTIFY = "IDENTIFY";
    private OnFragmentInteractionListener mListener;
    private String identify;
    private ChatPresenter mPresenter;



    private ChatAdapter adapter;
    private ListView listView;
    private List<Message> messageList = new ArrayList<>();

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
        adapter = new ChatAdapter(getContext(),R.layout.item_message,messageList);
        listView.setAdapter(adapter);
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
