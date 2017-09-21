package com.donagao.kaoqian.imsdk.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donagao.kaoqian.imsdk.EmoticonUtil;
import com.donagao.kaoqian.imsdk.R;
import com.donagao.kaoqian.imsdk.viewfeatures.ChatView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import io.github.mayunfei.utilslib.DensityUtils;

import static android.R.attr.spacing;

/**
 * 输入框
 */

public class ChatInput extends FrameLayout implements TextWatcher, View.OnClickListener {

    ViewPager mViewPager;
    public static final int PRE_PAGE_COUNT = 8;
    private EditText editText;

    ChatView chatView;
    private boolean isSendVisible;
    private Button btnSend;
    private LinearLayout emoticonPanel;

    private InputMode inputMode = InputMode.NONE;
    OnKeyboardShow onKeyboardShow;


    public void setOnKeyboardShow(OnKeyboardShow onKeyboardShow) {
        this.onKeyboardShow = onKeyboardShow;
    }

    public void setChatView(ChatView chatView) {
        this.chatView = chatView;
    }

    private EmojiIndicatorView ll_point_group;//表情面板对应的点列表


    private AbstractList<GridView> emotionViews = new ArrayList<>();

    public ChatInput(@NonNull Context context) {
        this(context, null);
    }

    public ChatInput(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatInput(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.chat_input_layout, this);
        ll_point_group= (EmojiIndicatorView) findViewById(R.id.ll_point_group);
        mViewPager = findViewById(R.id.vp_complate_emotion_layout);
        initView();
        initEmj();
        EmjViewAdapter adapter = new EmjViewAdapter(emotionViews);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos=0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ll_point_group.playByStartPointToNext(oldPagerPos,position);
                oldPagerPos=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initView() {
        editText = (EditText) findViewById(R.id.input);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editText.requestFocus()){
                        updateView(InputMode.TEXT);
                    }
                }
            }
        });
        isSendVisible = editText.getText().length() != 0;
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        findViewById(R.id.btnEmoticon).setOnClickListener(this);
        emoticonPanel = (LinearLayout) findViewById(R.id.emoticonPanel);
    }

    private void updateView(InputMode mode){
        if (mode == inputMode) return;
        leavingCurrentState();
        switch (inputMode = mode){
            case TEXT:
                if (editText.requestFocus()){
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    if (onKeyboardShow!=null){
                        onKeyboardShow.onKeyboardShow(true);
                    }
                }
                break;
            case EMOTICON:
                emoticonPanel.setVisibility(VISIBLE);
                if (onKeyboardShow!=null){
                    onKeyboardShow.onKeyboardShow(true);
                }
                break;
            default:
                onKeyboardShow.onKeyboardShow(false);
                break;
        }
    }



    private void leavingCurrentState(){
        switch (inputMode){
            case TEXT:
                View view = ((Activity) getContext()).getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                editText.clearFocus();
                break;
            case EMOTICON:
                emoticonPanel.setVisibility(GONE);
        }
    }


    private void initEmj() {
        List<Integer> emj_indexs = new ArrayList<>(PRE_PAGE_COUNT);
        List<Integer> indexs = new ArrayList<>(EmoticonUtil.emj.length);
        for (int i = 0; i < EmoticonUtil.emj.length; i++) {
            indexs.add(i);
        }

        for (int emj_index : indexs) {
            emj_indexs.add(emj_index);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emj_indexs.size() == PRE_PAGE_COUNT) {
                GridView gv = createEmotionGridView(emotionViews.size(),emj_indexs);
                emotionViews.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emj_indexs = new ArrayList<>(PRE_PAGE_COUNT);
            }
        }

        // 判断最后是否有不足20个表情的剩余情况
        if (emj_indexs.size() > 0) {
            GridView gv = createEmotionGridView(emotionViews.size(),emj_indexs);
            emotionViews.add(gv);
        }
        //初始化指示器
        ll_point_group.initIndicator(emotionViews.size());
    }

    private GridView createEmotionGridView(final int page, List<Integer> emj_indexs) {
        // 创建GridView
        int padding = DensityUtils.dip2px(getContext(),5);
        GridView gv = new GridView(getContext());
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        gv.setNumColumns(4);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding * 2);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(getContext(),220f));
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGridViewAdapter adapter = new EmotionGridViewAdapter(emj_indexs);
        gv.setAdapter(adapter);
        //设置全局点击事件
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onEmjOnclick(i+ page*PRE_PAGE_COUNT);
            }
        });
//        gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener(emotion_map_type));
        return gv;
    }

    /**
     * 获取输入框文字
     */
    public Editable getText(){
        return editText.getText();
    }

    /**
     * 设置输入框文字
     */
    public void setText(String text){
        editText.setText(text);
    }

    private void onEmjOnclick(int position) {
        chatView.sendFace(position);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isSendVisible = s!=null&&s.length()>0;
        setSendBtn();
    }

    private void setSendBtn(){
        if (isSendVisible){
            btnSend.setClickable(true);
        }else{
            btnSend.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        Activity activity = (Activity) getContext();
        int id = view.getId();
        if (id == R.id.btn_send){
            chatView.sendText();
        }
//        if (id == R.id.btn_keyboard){
//            updateView(InputMode.TEXT);
//        }
        if (id == R.id.btnEmoticon){
            updateView(inputMode == InputMode.EMOTICON?InputMode.TEXT:InputMode.EMOTICON);
        }
    }

    /**
     * 设置输入模式
     */
    public void setInputMode(InputMode mode){
        updateView(mode);
    }

    public void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    static class EmotionGridViewAdapter extends BaseAdapter{

        private final List<Integer> emj_indexs;

        public EmotionGridViewAdapter(List<Integer> emj_indexs) {
            this.emj_indexs = emj_indexs;
        }

        @Override
        public int getCount() {
            return emj_indexs.size();
        }

        @Override
        public Object getItem(int i) {
            return emj_indexs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return emj_indexs.get(i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Context context = viewGroup.getContext();
            View view_holder = inflate(context, R.layout.emj_item, null);
            ImageView iv_emj = view_holder.findViewById(R.id.iv_emj);
            TextView tv_emj = view_holder.findViewById(R.id.tv_emj);
            iv_emj.setImageResource(EmoticonUtil.emj[emj_indexs.get(i)]);
            tv_emj.setText(EmoticonUtil.emoticonData[emj_indexs.get(i)]);

            return view_holder;
        }
    }

    class EmjViewAdapter extends PagerAdapter {

        private List<GridView> gvs;

        public EmjViewAdapter(List<GridView> gvs) {
            this.gvs = gvs;
        }

        @Override
        public int getCount() {
            return gvs.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(gvs.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(gvs.get(position));
            return gvs.get(position);
        }
    }

    public enum InputMode{
        TEXT,
        VOICE,
        EMOTICON,
        MORE,
        VIDEO,
        NONE,
    }

    public interface OnKeyboardShow{
        void onKeyboardShow(boolean isShow);
    }

}
