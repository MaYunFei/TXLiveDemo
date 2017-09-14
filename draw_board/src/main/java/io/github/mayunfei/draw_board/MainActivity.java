package io.github.mayunfei.draw_board;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private Whiteboard whiteboard;
    private Button btn_change;
    private FrameLayout content;
    private PPTView ppt_view;
    private boolean isSmall = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whiteboard = (Whiteboard) findViewById(R.id.whiteboard);
        content = (FrameLayout) findViewById(R.id.content);
        ppt_view = (PPTView) findViewById(R.id.ppt_view);
        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSmall){
                    ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    content.setLayoutParams(layoutParams);
                    isSmall = false;
                }else{
                    ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
                    layoutParams.width = DensityUtils.dip2px(MainActivity.this,200);
                    content.setLayoutParams(layoutParams);
                    isSmall = true;
                }

            }
        });
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteboard.clear();
            }
        });
        findViewById(R.id.btn_drawline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteboard.drawLine();
            }
        });
        findViewById(R.id.btn_change_ppt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ppt_view.change();
            }
        });


    }
}
