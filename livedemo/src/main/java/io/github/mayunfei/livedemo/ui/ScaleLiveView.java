package io.github.mayunfei.livedemo.ui;


import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import io.github.mayunfei.livelib.LiveView;

/** 16:9 的播放器
 * Created by AlphaGo on 2017/9/14 0014.
 */

public class ScaleLiveView extends LiveView {
    public ScaleLiveView(@NonNull Context context) {
        this(context,null);
    }

    public ScaleLiveView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScaleLiveView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
