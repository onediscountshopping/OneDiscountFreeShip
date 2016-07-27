package com.example.administrator.onediscountfreeship;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fragment.CategorySearch_fragment;
import fragment.FrontPageFragment;
import fragment.Wortharound_fragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private LinearLayout llGuide;
    private RelativeLayout rlMain;
    //主页面的五个板块，可添加fragment
    private Fragment[] fragments = {FrontPageFragment.getFragment(), new Wortharound_fragment(), new Wortharound_fragment(),new CategorySearch_fragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlMain = (RelativeLayout) findViewById(R.id.rl_main);
        llGuide = (LinearLayout) findViewById(R.id.ll_guide);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_fragment, FrontPageFragment.getFragment()).commit();
        LinearLayout childLl = (LinearLayout) llGuide.getChildAt(0);
        childLl.getChildAt(0).setSelected(true);
        onclick();
    }

    private void onclick() {//点击监听
        for (int i = 0; i < llGuide.getChildCount(); i++) {
            LinearLayout childAt = (LinearLayout) llGuide.getChildAt(i);
            childAt.setTag(i);
            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    if (tag == 0) {
                        rlMain.setVisibility(View.VISIBLE);
                    } else
                        rlMain.setVisibility(View.GONE);
                        for (int j = 0; j < llGuide.getChildCount(); j++) {
                            LinearLayout ll = (LinearLayout) llGuide.getChildAt(j);
                            ll.getChildAt(0).setSelected(false);
                          //  ((TextView) ll.getChildAt(1)).setTextColor(0x5d646f);
                        }
                        ((LinearLayout) v).getChildAt(0).setSelected(true);
                        //((TextView) ((LinearLayout) v).getChildAt(1)).setTextColor(0xdf2624);
                        getSupportFragmentManager().beginTransaction().replace(R.id.ll_fragment, fragments[tag]).commit();
                }
            });
        }
    }
}
