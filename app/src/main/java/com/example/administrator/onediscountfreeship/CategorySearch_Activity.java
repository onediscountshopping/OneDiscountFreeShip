package com.example.administrator.onediscountfreeship;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
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
    private boolean flagb = true;
    // private boolean flaga = true;
    private boolean flagd = true;

    private PopupWindow mPopupWindow;
    private LinearLayout popwinss, popWindow, draweLaout_layout;
    private FrameLayout topred;
    private RelativeLayout nvhzunag_item001;
    private ViewGroup menuview;
    private ImageView nvzhuang_jiao;
    private TextView quanbu_name, baoyou_name, fengding_name, quanbunvzhuang_name, shangyi2_name, qunku_name, neiyi_name, taozhuang_name;
    private TextView nanzhuang_name, jujia_name, muying_name, meishi_name, xiabao_name, paishi_name, shuma_name, meizhuang_name, wenti_name, oldman_name;
    private DrawerLayout draweLayout;
    private EditText DrawerLayout_floorprice;
    private EditText DrawerLayout_highestprice;

    private int lprice = 0;
    private int hprice = 0;
    private String tbclass = 0 + "";
    private RadioButton DrawerLayout_tianmao;
    private RadioButton DrawerLayout_quanbu;
    private RadioButton DrawerLayout_taobao;

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

        filtrate_name.setText(name);
        Log.e(TAG, "onCreate:filtrate_name "+filtrate_name.toString());
        ChangeTextPopColer(filtrate_name);
        moren.setTextColor(Color.RED);
        listAdapter = new Wortharound_listviewAdapter(this);
        postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
        mListview.setAdapter(listAdapter);
    }
    //不能实现该方法
    public void ChangeTextPopColer(TextView filtrate_name) {
        if (filtrate_name.equals("全部")) {
            quanbu_name.setTextColor(Color.RED);
        } else if (filtrate_name.equals("9.9包邮")) {
            baoyou_name.setTextColor(Color.RED);
        } else if (filtrate_name.equals("20元封顶")) {
            fengding_name.setTextColor(Color.RED);
        } else if (filtrate_name.equals("全部女装")) {
            quanbunvzhuang_name.setTextColor(Color.RED);
        } else if (filtrate_name.equals("上衣")) {
            shangyi2_name.setTextColor(Color.RED);
        } else if (filtrate_name.equals("裙裤")) {
            qunku_name.setTextColor(Color.RED);
        } else if (filtrate_name.equals("套装")) {
            taozhuang_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("男装")) {
            nanzhuang_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("居家")) {
            jujia_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("母婴")) {
            muying_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("鞋包")) {
            xiabao_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("配饰")) {
            paishi_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("美食")) {
            meishi_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("数码家电")) {
            shuma_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("美妆个护")) {
            meizhuang_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("文体")) {
            wenti_name.setTextColor(Color.RED);
        }else if (filtrate_name.equals("老年人")) {
            oldman_name.setTextColor(Color.RED);
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

        //创建PopWindow窗体
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        menuview = (ViewGroup) layoutInflater.inflate(R.layout.popwindow, null);
        popWindow = (LinearLayout) menuview.findViewById(R.id.PopWindow_parent);
        mPopupWindow = new PopupWindow(menuview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        nvhzunag_item001 = (RelativeLayout) menuview.findViewById(R.id.nvhzunag_item001);
        nvzhuang_jiao = (ImageView) menuview.findViewById(R.id.nvzhuang_jiantou);

        quanbu_name = (TextView) menuview.findViewById(R.id.quanbu_name);
        baoyou_name = (TextView) menuview.findViewById(R.id.baoyou_name);
        fengding_name = (TextView) menuview.findViewById(R.id.fengding_name);
        quanbunvzhuang_name = (TextView) menuview.findViewById(R.id.quanbunvzhuang_name);
        shangyi2_name = (TextView) menuview.findViewById(R.id.shangyi2_name);
        qunku_name = (TextView) menuview.findViewById(R.id.qunku_name);
        neiyi_name = (TextView) menuview.findViewById(R.id.neiyi_name);
        taozhuang_name = (TextView) menuview.findViewById(R.id.taozhuang_name);
        nanzhuang_name = (TextView) menuview.findViewById(R.id.nanzhuang_name);
        jujia_name = (TextView) menuview.findViewById(R.id.jujia_name);
        muying_name = (TextView) menuview.findViewById(R.id.muying_name);
        xiabao_name = (TextView) menuview.findViewById(R.id.xiabao_name);
        paishi_name = (TextView) menuview.findViewById(R.id.paishi_name);
        meishi_name = (TextView) menuview.findViewById(R.id.meishi_name);
        shuma_name = (TextView) menuview.findViewById(R.id.shuma_name);
        meizhuang_name = (TextView) menuview.findViewById(R.id.meizhuang_name);
        wenti_name = (TextView) menuview.findViewById(R.id.wenti_name);
        oldman_name = (TextView) menuview.findViewById(R.id.oldman_name);

        draweLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        draweLaout_layout = (LinearLayout) findViewById(R.id.draweLayout_LinearLayout);
        DrawerLayout_floorprice = (EditText) findViewById(R.id.DrawerLayout_floorprice);
        DrawerLayout_highestprice = (EditText) findViewById(R.id.DrawerLayout_highestprice);
        DrawerLayout_tianmao = (RadioButton) findViewById(R.id.DrawerLayout_tianmao);
        DrawerLayout_quanbu = (RadioButton) findViewById(R.id.DrawerLayout_quanbu);
        DrawerLayout_taobao = (RadioButton) findViewById(R.id.DrawerLayout_taobao);
    }

    //PopupWindow的女装部分的列表
    public void nvzhuang_sss(View view) {
        // Log.e(TAG, "nvzhuang_sss: ");
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

    //完成提交
    public void wancheng(View view) {
        String l = DrawerLayout_floorprice.getText().toString();
        if (l.isEmpty()) {
            lprice = 0;
        } else {
            lprice = Integer.parseInt(l);
        }
        String h = DrawerLayout_highestprice.getText().toString();

        if (h.isEmpty()) {
            hprice = 0;
        } else {
            hprice = Integer.parseInt(h);
        }

        if (DrawerLayout_tianmao.isChecked()) {
            tbclass = 1 + "";
        } else if (DrawerLayout_taobao.isChecked()) {
            tbclass = 2 + "";
        } else if (DrawerLayout_quanbu.isChecked()) {
            tbclass = 0 + "";
        }
        //Log.e(TAG, "wancheng: tbclass" + tbclass);
        draweLayout.closeDrawer(Gravity.RIGHT);
        postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
    }

    //抽屉的开关操作
    public void Onshaixuan(View view) {
        if (mPopupWindow.isShowing()) {
            draweLayout.openDrawer(Gravity.RIGHT);
            mPopupWindow.dismiss();
        } else {
            if (flagd) {
                draweLayout.openDrawer(Gravity.RIGHT);
                flagd = false;
            } else {
                draweLayout.closeDrawer(Gravity.RIGHT);
                flagd = true;
            }
        }
    }

    //PopupWindow的开关操作
    public void onPopOnClick(View view) {
        if (draweLayout.isDrawerOpen(Gravity.RIGHT)) {
            draweLayout.closeDrawer(Gravity.RIGHT);
        }
//        Log.e(TAG, "onPop:flaga =" + flaga);
//        if (flaga) {
//            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//            ViewGroup menuview = (ViewGroup) layoutInflater.inflate(R.layout.popwindow, null);
//            mPopupWindow = new PopupWindow(menuview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mPopupWindow.setFocusable(true);
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAsDropDown(topred, 0, 0);

//         flaga = false;
//         Log.e(TAG, "onPop:flaga11 =" + flaga);
//        } else {
//             Log.e(TAG, "onPop:flaga22 =" + flaga);
//           // mPopupWindow.dismiss();
//            flaga = true;
//            Log.e(TAG, "onPop:flaga33 =" + flaga);
//        }

    }

    //给popwin的跟布局添加点击事件，就可以点击popwindow的外面让窗体消失
    public void onPop_layout(View view) {
        // Log.e(TAG, "onPop_layout: =");
        mPopupWindow.dismiss();
    }

    public void postJson(int bc, int sc, int channel, String sorts, String daynews, int lprice, int hprice, String tbclass) {
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
        builder.add("lprice", "" + lprice);
        builder.add("hprice", "" + hprice);
        builder.add("tbclass", tbclass);
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

    //改变PopupWindow中字体的颜色为黑色
    public void ChangeTextColor() {
        //全部
        RelativeLayout childAt_quanbu = (RelativeLayout) popWindow.getChildAt(0);
        TextView quanbu = (TextView) childAt_quanbu.findViewById(R.id.quanbu_name);
        quanbu.setTextColor(Color.BLACK);

        //包邮
        RelativeLayout childAt_baoyou = (RelativeLayout) popWindow.getChildAt(1);
        TextView baoyou = (TextView) childAt_baoyou.findViewById(R.id.baoyou_name);
        baoyou.setTextColor(Color.BLACK);

        //封顶
        RelativeLayout childAt_fengding = (RelativeLayout) popWindow.getChildAt(2);
        TextView fengding = (TextView) childAt_fengding.findViewById(R.id.fengding_name);
        fengding.setTextColor(Color.BLACK);

        //女装
        RelativeLayout childAt_nvzhuang = (RelativeLayout) popWindow.getChildAt(4);
        //全部女装
        TextView quanbunvzhuang = (TextView) childAt_nvzhuang.findViewById(R.id.quanbunvzhuang_name);
        quanbunvzhuang.setTextColor(Color.BLACK);
        //上衣
        TextView shangyi = (TextView) childAt_nvzhuang.findViewById(R.id.shangyi2_name);
        shangyi.setTextColor(Color.BLACK);
        //裙裤
        TextView qunku = (TextView) childAt_nvzhuang.findViewById(R.id.qunku_name);
        qunku.setTextColor(Color.BLACK);
        //内衣
        TextView neiyi = (TextView) childAt_nvzhuang.findViewById(R.id.neiyi_name);
        neiyi.setTextColor(Color.BLACK);
        //套装
        TextView taozhuang = (TextView) childAt_nvzhuang.findViewById(R.id.taozhuang_name);
        taozhuang.setTextColor(Color.BLACK);

        //男装
        RelativeLayout childAt_nanzhuang = (RelativeLayout) popWindow.getChildAt(5);
        TextView nanzhuang = (TextView) childAt_nanzhuang.findViewById(R.id.nanzhuang_name);
        nanzhuang.setTextColor(Color.BLACK);

        //居家
        RelativeLayout childAt_jujia = (RelativeLayout) popWindow.getChildAt(6);
        TextView jujia = (TextView) childAt_jujia.findViewById(R.id.jujia_name);
        jujia.setTextColor(Color.BLACK);

        //母婴
        RelativeLayout childAt_muying = (RelativeLayout) popWindow.getChildAt(7);
        TextView muying = (TextView) childAt_muying.findViewById(R.id.muying_name);
        muying.setTextColor(Color.BLACK);

        //母婴
        RelativeLayout childAt_xiebao = (RelativeLayout) popWindow.getChildAt(8);
        TextView xiebao = (TextView) childAt_xiebao.findViewById(R.id.xiabao_name);
        xiebao.setTextColor(Color.BLACK);

        //母婴
        RelativeLayout childAt_peishi = (RelativeLayout) popWindow.getChildAt(9);
        TextView peishi = (TextView) childAt_peishi.findViewById(R.id.paishi_name);
        peishi.setTextColor(Color.BLACK);

        //美食
        RelativeLayout childAt_meishi = (RelativeLayout) popWindow.getChildAt(10);
        TextView meishi = (TextView) childAt_meishi.findViewById(R.id.meishi_name);
        meishi.setTextColor(Color.BLACK);

        //数码家电
        RelativeLayout childAt_shuma = (RelativeLayout) popWindow.getChildAt(11);
        TextView shuma = (TextView) childAt_shuma.findViewById(R.id.shuma_name);
        shuma.setTextColor(Color.BLACK);

        //美妆个护
        RelativeLayout childAt_meizhunag = (RelativeLayout) popWindow.getChildAt(12);
        TextView meizhuang = (TextView) childAt_meizhunag.findViewById(R.id.meizhuang_name);
        meizhuang.setTextColor(Color.BLACK);

        //文体
        RelativeLayout childAt_wenti = (RelativeLayout) popWindow.getChildAt(13);
        TextView wenti = (TextView) childAt_wenti.findViewById(R.id.wenti_name);
        wenti.setTextColor(Color.BLACK);

        //老年人
        RelativeLayout childAt_oldman = (RelativeLayout) popWindow.getChildAt(14);
        TextView oldman = (TextView) childAt_oldman.findViewById(R.id.oldman_name);
        oldman.setTextColor(Color.BLACK);
    }

    //默认  销量 价格  最新 ， 单击事件
    public void onSearch(View view) {
        //  Log.e(TAG, "onSearch: ");
        switch (view.getId()) {
            case R.id.moren:
                flag = true;
                image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_unselected);
                moren.setTextColor(Color.RED);
                xiaoliang.setTextColor(Color.BLACK);
                jiage.setTextColor(Color.BLACK);
                zuixin.setTextColor(Color.BLACK);
                sorts = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                break;
            case R.id.xiaoliang:
                flag = true;
                image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_unselected);
                moren.setTextColor(Color.BLACK);
                xiaoliang.setTextColor(Color.RED);
                jiage.setTextColor(Color.BLACK);
                zuixin.setTextColor(Color.BLACK);
                sorts = "sale2l";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                break;
            case R.id.jiage:
                moren.setTextColor(Color.BLACK);
                xiaoliang.setTextColor(Color.BLACK);
                jiage.setTextColor(Color.RED);
                zuixin.setTextColor(Color.BLACK);
                if (flag) {
                    image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_up);
                    sorts = "pl2h";
                    postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                    flag = false;
                } else {
                    image_jiage.setImageResource(R.mipmap.bg_sort_tab_center_btn_normal_down);
                    sorts = "ph2l";
                    postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
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
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                break;
        }
    }

    //PopWindow里面的内容的点击事件
    public void PopWindow(View view) {
        // Log.e(TAG, "PopWindow: ");
        switch (view.getId()) {
            case R.id.quanbu_layout:
                filtrate_name.setText("全部");
                ChangeTextColor();
                quanbu_name.setTextColor(Color.RED);
                bc = 0;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.baoyou_layout:
                filtrate_name.setText("9.9包邮");
                ChangeTextColor();
                baoyou_name.setTextColor(Color.RED);
                bc = 0;
                sc = 0;
                channel = 3;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.fengzhuang_layout:
                filtrate_name.setText("20元封顶");
                ChangeTextColor();
                fengding_name.setTextColor(Color.RED);
                bc = 0;
                sc = 0;
                channel = 4;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.quanbunvzhuang_name:
                filtrate_name.setText("全部女装");
                ChangeTextColor();
                quanbunvzhuang_name.setTextColor(Color.RED);
                bc = 1;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.shangyi2_name:
                filtrate_name.setText("上衣");
                ChangeTextColor();
                shangyi2_name.setTextColor(Color.RED);
                bc = 1;
                sc = 11;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.qunku_name:
                filtrate_name.setText("裤裙");
                ChangeTextColor();
                qunku_name.setTextColor(Color.RED);
                bc = 1;
                sc = 12;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.neiyi_name:
                filtrate_name.setText("内衣");
                ChangeTextColor();
                neiyi_name.setTextColor(Color.RED);
                bc = 1;
                sc = 13;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.taozhuang_name:
                filtrate_name.setText("套装");
                ChangeTextColor();
                taozhuang_name.setTextColor(Color.RED);
                bc = 1;
                sc = 14;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.nanzhuang_layout:
                filtrate_name.setText("男装");
                ChangeTextColor();
                nanzhuang_name.setTextColor(Color.RED);
                bc = 2;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.jujia_lauout:
                filtrate_name.setText("居家");
                ChangeTextColor();
                jujia_name.setTextColor(Color.RED);
                bc = 3;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.muying_lauyout:
                filtrate_name.setText("母婴");
                ChangeTextColor();
                muying_name.setTextColor(Color.RED);
                bc = 4;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.xiabao_layout:
                filtrate_name.setText("鞋包");
                ChangeTextColor();
                xiabao_name.setTextColor(Color.RED);
                bc = 5;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.paishi_layout:
                filtrate_name.setText("配饰");
                ChangeTextColor();
                paishi_name.setTextColor(Color.RED);
                bc = 6;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.meishi_layout:
                filtrate_name.setText("美食");
                ChangeTextColor();
                meishi_name.setTextColor(Color.RED);
                bc = 7;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.shuma_layout:
                filtrate_name.setText("数码家电");
                ChangeTextColor();
                shuma_name.setTextColor(Color.RED);
                bc = 8;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.meizhuang_layout:
                filtrate_name.setText("美妆个护");
                ChangeTextColor();
                meizhuang_name.setTextColor(Color.RED);
                bc = 9;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.wenti_layout:
                filtrate_name.setText("文体");
                ChangeTextColor();
                wenti_name.setTextColor(Color.RED);
                bc = 10;
                sc = 0;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
            case R.id.oldman_layout:
                filtrate_name.setText("中老年");
                ChangeTextColor();
                oldman_name.setTextColor(Color.RED);
                bc = 1;
                sc = 16;
                channel = 0;
                sorts = "";
                daynews = "";
                postJson(bc, sc, channel, sorts, daynews, lprice, hprice, tbclass);
                mPopupWindow.dismiss();
                break;
        }
    }
}
