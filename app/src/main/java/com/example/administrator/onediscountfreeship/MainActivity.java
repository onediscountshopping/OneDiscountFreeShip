package com.example.administrator.onediscountfreeship;

import android.graphics.Color;
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
import fragment.PersonalCenterFragment;
import fragment.Wortharound_fragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private LinearLayout llGuide;
    private RelativeLayout rlMain;
    //主页面的五个板块，可添加fragment
    private Fragment[] fragments = {FrontPageFragment.getFragment(), new Wortharound_fragment(), new Wortharound_fragment(), new CategorySearch_fragment(), new PersonalCenterFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //传值
//        initData();

        rlMain = (RelativeLayout) findViewById(R.id.rl_main);
        llGuide = (LinearLayout) findViewById(R.id.ll_guide);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_fragment, FrontPageFragment.getFragment()).commit();
        LinearLayout childLl = (LinearLayout) llGuide.getChildAt(0);
        childLl.getChildAt(0).setSelected(true);
        onclick();
    }

//    //传值
//    private void initData() {
//        //获得一个intent来接收传过来的值
//        Intent intent = getIntent();
//        //定义一个字符串类型的变量来接收传过来的值
//        int tag = intent.getIntExtra("key", 0);
//        //if判断语句
//        if (tag == 1) {
//            //获得一个PersonalCenterFragment的对象
//            PersonalCenterFragment pcfm = new PersonalCenterFragment();
//            //获得一个bundle
//            Bundle bundle = new Bundle();
//            //向bundle中添加键值对
//            bundle.putInt("key", 1);
//            //发送
//            pcfm.setArguments(bundle);
//            Log.e("TAG", "走了");
//        }
//    }

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
                        ((TextView) ll.getChildAt(1)).setTextColor(Color.rgb(93,100,111));
                    }
                    ((LinearLayout)v).getChildAt(0).setSelected(true);
                    ((TextView) ((LinearLayout)v).getChildAt(1)).setTextColor(0xffdf2624);//颜色要用8位
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_fragment,fragments[tag]).commit();
                }
            });
        }
    }
}
