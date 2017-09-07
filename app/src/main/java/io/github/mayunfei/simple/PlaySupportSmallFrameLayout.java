package io.github.mayunfei.simple;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * Created by mayunfei on 17-9-7.
 */

public class PlaySupportSmallFrameLayout extends PlayFrameLayout {
    public PlaySupportSmallFrameLayout(@NonNull Context context) {
        super(context);
    }

    public PlaySupportSmallFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaySupportSmallFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showSmallScreen(){
        final ViewGroup vp = getViewGroup();

        //TODO remove 应该判断是否释放资源
        removeVideo(vp, R.id.id_drag_group);

        PlayFrameLayout playFrameLayout = new PlaySupportSmallFrameLayout(getContext());
        playFrameLayout.setId(R.id.id_small_screen);
        DragGroup dragGroup = new DragGroup(getContext());
        dragGroup.setId(R.id.id_drag_group);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        playFrameLayout.setLayoutParams(layoutParams);
        playFrameLayout.showPPt();
        dragGroup.addView(playFrameLayout);
        vp.addView(dragGroup);

    }
    private ViewGroup getViewGroup() {
        return (ViewGroup) (Utils.scanForActivity(getContext())).findViewById(Window.ID_ANDROID_CONTENT);
    }

    /**
     * 移除没用的
     */
    private void removeVideo(ViewGroup vp, int id) {
        View old = vp.findViewById(id);
        if (old != null) {
            if (old.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) old.getParent();
                vp.removeView(viewGroup);
            }
        }
    }


}
