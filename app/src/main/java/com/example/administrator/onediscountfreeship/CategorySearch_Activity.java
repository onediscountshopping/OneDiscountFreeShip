package com.example.administrator.onediscountfreeship;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import adapter.Wortharound_listviewAdapter;
import bean.Wortharound_bean;

public class CategorySearch_Activity extends AppCompatActivity {

    private static final String TAG = "CategorySearch_Activity";
    private TextView filtrate_name, moren, xiaoliang, jiage, zuixin;
    private String name, daynews, sorts;
    private int bc, sc, channel;
    private ListView mListview;

    private Wortharound_listviewAdapter listAdapter;
    private ImageView image_jiage;
    private boolean flag = true;
    private boolean flaga = true;
    private boolean flagb = true;

    private PopupWindow mPopupWindow;
    private LinearLayout popwinss;
    private FrameLayout topred;
    private RelativeLayout nvhzunag_item001, baoyou_layout, fengzhuang_layout, nanzhuang_layout, jujia_lauout, muying_lauyout, oldman_layout;
    private ViewGroup menuview;
    private ImageView nvzhuang_jiao;
    private RelativeLayout quanbu_layout, xiabao_layout, paishi_layout, meishi_layout, shuma_layout, meizhuang_layout, wenti_layout;
    private TextView quanbu_name, baoyou_name, fengding, quanbunvzhuang, shangyi2, qunku, neiyi, taozhuang;
    private TextView nanzhuang_name, jujia_name, muying_name, xiabao_name, paishi_name, shuma_name, meizhuang_name, wenti_name, oldman_name;
    private LinearLayout popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_search_);

        initView();

        Bundle bundle = getIntent().getBundleExtra("filtrate");

        name = bundle.getString("name");
        daynews = bundle.getString("daynews");
        sorts = bundle.getString("sorts");
        bc = bundle.getInt("position");
        sc = bundle.getInt("sc");
        channel = bundle.getInt("channel");

        //Log.e(TAG, "onCreate: bc" + bc);

        filtrate_name.setText(name);
        moren.setTextColor(Color.RED);
        listAdapter = new Wortharound_listviewAdapter(this);
        postJson(bc, sc, channel, sorts, daynews);
        mListview.setAdapter(listAdapter);

        for (int i = 0; i < popWindow.getChildCount(); i++) {
            popWindow.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

        }


    }

    private void initView() {

        filtrate_name = (TextView) findViewById(R.id.CategorySearch_filtrate_name);
        mListview = (ListView) findViewById(R.id.CategorySearch_listView);
        moren = (TextView) findViewById(R.id.moren);
        xiaoliang = (TextView) findViewById(R.id.xiaoliang);
        jiage = (TextView) findViewById(R.id.jiage);
        zuixin = (TextView) findViewById(R.id.zuixin);
        image_jiage = (ImageView) findViewById(R.id.image_jiage);
        popwinss = (LinearLayout) findViewById(R.id.popwin);
        topred = (FrameLayout) findViewById(R.id.topred);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        menuview = (ViewGroup) layoutInflater.inflate(R.layout.popwindow, null);
        popWindow = (LinearLayout) menuview.findViewById(R.id.PopWindow_parent);

        nvhzunag_item001 = (RelativeLayout) menuview.findViewById(R.id.nvhzunag_item001);
        nvzhuang_jiao = (ImageView) menuview.findViewById(R.id.nvzhuang_jiantou);
        quanbu_layout = (RelativeLayout) findViewById(R.id.quanbu_layout);
        baoyou_layout = (RelativeLayout) findViewById(R.id.baoyou_layout);
        fengzhuang_layout = (RelativeLayout) findViewById(R.id.fengzhuang_layout);
        nanzhuang_layout = (RelativeLayout) findViewById(R.id.nanzhuang_layout);
        jujia_lauout = (RelativeLayout) findViewById(R.id.jujia_lauout);
        muying_lauyout = (RelativeLayout) findViewById(R.id.muying_lauyout);
        xiabao_layout = (RelativeLayout) findViewById(R.id.xiabao_layout);
        paishi_layout = (RelativeLayout) findViewById(R.id.paishi_layout);
        meishi_layout = (RelativeLayout) findViewById(R.id.meishi_layout);
        shuma_layout = (RelativeLayout) findViewById(R.id.shuma_layout);
        meizhuang_layout = (RelativeLayout) findViewById(R.id.meizhuang_layout);
        wenti_layout = (RelativeLayout) findViewById(R.id.wenti_layout);
        oldman_layout = (RelativeLayout) findViewById(R.id.oldman_layout);

        quanbu_name = (TextView) findViewById(R.id.quanbu_name);
        baoyou_name = (TextView) findViewById(R.id.baoyou_name);
        fengding = (TextView) findViewById(R.id.fengding);
        quanbunvzhuang = (TextView) findViewById(R.id.quanbunvzhuang);
        shangyi2 = (TextView) findViewById(R.id.shangyi2);
        qunku = (TextView) findViewById(R.id.qunku);
        neiyi = (TextView) findViewById(R.id.neiyi);
        taozhuang = (TextView) findViewById(R.id.taozhuang);
        nanzhuang_name = (TextView) findViewById(R.id.nanzhuang_name);
        jujia_name = (TextView) findViewById(R.id.jujia_name);
        muying_name = (TextView) findViewById(R.id.muying_name);
        xiabao_name = (TextView) findViewById(R.id.xiabao_name);
        paishi_name = (TextView) findViewById(R.id.paishi_name);
        shuma_name = (TextView) findViewById(R.id.shuma_name);
        meizhuang_name = (TextView) findViewById(R.id.meizhuang_name);
        wenti_name = (TextView) findViewById(R.id.wenti_name);
        oldman_name = (TextView) findViewById(R.id.oldman_name);


    }

    public void nvzhuang(View view) {
        if (flagb) {
            nvzhuang_jiao.setImageResource(R.mipmap.ic_triangle_down_category);
            nvhzunag_item001.setVisibility(View.GONE);
            flagb = false;
        } else {
            nvzhuang_jiao.setImageResource(R.mipmap.ic_triangle_up_category);
            nvhzunag_item001.setVisibility(View.VISIBLE);
            flagb = true;
        }


    }

    public void onPop(View view) {
        //   Log.e(TAG, "onPop:flaga =" + flaga);
        if (flaga) {
            //LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            // ViewGroup menuview = (ViewGroup) layoutInflater.inflate(R.layout.popwindow, null);
            mPopupWindow = new PopupWindow(menuview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            mPopupWindow.setFocusable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.showAsDropDown(topred, 0, 0);

            flaga = false;
            // Log.e(TAG, "onPop:flaga11 =" + flaga);
        } else {
            // Log.e(TAG, "onPop:flaga22 =" + flaga);
            mPopupWindow.dismiss();
            flaga = true;
            //Log.e(TAG, "onPop:flaga33 =" + flaga);
        }

    }

    //给popwin的跟布局添加点击事件，就可以点击婆婆window的外面让窗体消失
    public void onPop_layout(View view) {
        mPopupWindow.dismiss();
    }

    public void postJson(int bc, int sc, int channel, String sorts, String daynews) {
        //act=getproductlist&pages=	1&bc=1&sc=0&sorts	=&channel=07ckey=&daynews=	&lprice=
        // 0&hprice=	0&tbclass=0&actid=0&brandid=	0&predate=&index=0&v=33
        // Log.e(TAG, "postJson: bc=" + bc);
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("act", "getproductlist");
        builder.add("pages", "1");
        builder.add("bc", "" + bc);
        builder.add("sc", "" + sc);
        builder.add("sorts", sorts);
        builder.add("channel", "" + channel);
        builder.add("ckey", "");
        builder.add("daynews", daynews);
        builder.add("lprice", "0");
        builder.add("hprice", "0");
        builder.add("tbclass", "0");
        builder.add("actid", "0");
        builder.add("brandid", "0");
        builder.add("predate", "");
        builder.add("index", "0");
        builder.add("v", "33");

        Request request = new Request.Builder()
                .url("http://www.1zhebaoyou.com/apptools/productlist.aspx")
                .post(builder.build())
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response != null) {
                    Gson gson = new Gson();
                    String s = response.body().string();
                    //不能直接loge//response.body().string()汇报空指针
                    // Log.e(TAG, "response.body().String(): =" + s);
                    final Wortharound_bean mBean = gson.fromJson(s, Wortharound_bean.class);
                    //  Log.e(TAG, "onResponse: mBean" + mBean);

                    mListview.post(new Runnable() {
                        @Override
                        public void run() {
                            listAdapter.setData(mBean.getRows());
                        }
                    });
                }
            }
        });
    }

    public void onSearch(View view) {
        switch (view.getId()) {
            case R.id.moren:
                flag = true;
                image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_unselected);
                moren.setTextColor(Color.RED);
                xiaoliang.setTextColor(Color.BLACK);
                jiage.setTextColor(Color.BLACK);
                zuixin.setTextColor(Color.BLACK);
                sorts = "";
                postJson(bc, sc, channel, sorts, daynews);
                break;
            case R.id.xiaoliang:
                flag = true;
                image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_unselected);
                moren.setTextColor(Color.BLACK);
                xiaoliang.setTextColor(Color.RED);
                jiage.setTextColor(Color.BLACK);
                zuixin.setTextColor(Color.BLACK);
                sorts = "sale2l";
                postJson(bc, sc, channel, sorts, daynews);
                break;
            case R.id.jiage:
                moren.setTextColor(Color.BLACK);
                xiaoliang.setTextColor(Color.BLACK);
                jiage.setTextColor(Color.RED);
                zuixin.setTextColor(Color.BLACK);
                if (flag) {
                    image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_up);
                    sorts = "pl2h";
                    postJson(bc, sc, channel, sorts, daynews);
                    flag = false;
                } else {
                    image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_down);
                    sorts = "ph2l";
                    postJson(bc, sc, channel, sorts, daynews);
                    flag = true;
                }

                break;
            case R.id.zuixin:
                flag = true;
                image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_unselected);
                moren.setTextColor(Color.BLACK);
                xiaoliang.setTextColor(Color.BLACK);
                jiage.setTextColor(Color.BLACK);
                zuixin.setTextColor(Color.RED);
                sorts = "new";
                postJson(bc, sc, channel, sorts, daynews);
                break;
        }
    }


    public void PopWindow(View view) {
        switch (view.getId()) {
            case R.id.quanbu_layout:
                break;
            case R.id.baoyou_layout:
                break;
            case R.id.fengzhuang_layout:
                break;
            case R.id.quanbunvzhuang:
                break;
            case R.id.shangyi2:
                break;
            case R.id.qunku:
                break;
            case R.id.neiyi:
                break;
            case R.id.taozhuang:
                break;
            case R.id.nanzhuang_layout:
                break;
            case R.id.jujia_lauout:
                break;
            case R.id.muying_lauyout:
                break;
            case R.id.xiabao_layout:
                break;
            case R.id.paishi_layout:
                break;
            case R.id.meishi_layout:
                break;
            case R.id.shuma_layout:
                break;
            case R.id.meizhuang_layout:
                break;
            case R.id.wenti_layout:
                break;
            case R.id.oldman_layout:
                break;

        }
    }
}
