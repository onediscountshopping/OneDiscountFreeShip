package fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.onediscountfreeship.FengqiangActivity;
import com.example.administrator.onediscountfreeship.GoodsDetailActivity;
import com.example.administrator.onediscountfreeship.HeaderDetailActivity;
import com.example.administrator.onediscountfreeship.ODApp;
import com.example.administrator.onediscountfreeship.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.FrontBaseAdapter;
import adapter.ODPagerAdapter;
import bean.Goods;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrontPageFragment extends Fragment {

    private static FrontPageFragment fragment;
    private ListView lv;
    private View headView;
    private ViewPager vpHeader;
    private LinearLayout llIcons;
    private GridLayout GlHeader;
//    private GridLayout Gl2;
    private ImageView iv1,iv2,iv3;
    private FrontBaseAdapter adapter;
    private int page = 1;
    private int page=1,index;
    private Handler handler=new Handler(){
        int anInt = 0;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                vpHeader.setCurrentItem(anInt%4,true);
                anInt++;
                handler.sendEmptyMessageDelayed(1,3000);
            }
        }
    };


    @SuppressLint("ValidFragment")
    private FrontPageFragment() {
    }
    public static FrontPageFragment getFragment(){
        if (fragment == null) {
            fragment = new FrontPageFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_front_page, container, false);
        lv = ((ListView) view.findViewById(R.id.lv_frontpage));
        headView = inflater.inflate(R.layout.frontpage_header, null);
        lv.addHeaderView(headView);
        initView();
        adapter = new FrontBaseAdapter(getContext(), null);
        lv.setAdapter(adapter);
        loadData(page);
        initListener();//设置监听
        return view;
    }

    private void initListener() {//设置监听
        vpHeader.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < llIcons.getChildCount(); i++) {
                    llIcons.getChildAt(i).setSelected(false);
                }
                llIcons.getChildAt(position).setSelected(true);
            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        for (int i = 0; i < GlHeader.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) GlHeader.getChildAt(i);
            layout.setTag(i);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    switch (((int) v.getTag())){
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                    }
                    startActivity(intent);
                }
            });
        }
        //ListView每一项的点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goods goods = (Goods) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("url",goods.getProductUrl());
                startActivity(intent);
            }
        });

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FengqiangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData(final int index) {//ListView加载数据
        final List<Goods>list = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, "http://www.1zhebaoyou.com/apptools/productlist.aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONObject(response).getJSONArray("rows");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        Goods goods = new Goods();
                        goods.setName(jo.getString("Name"));
                        goods.setNewPrice(jo.getDouble("NewPrice"));
                        goods.setOldPrice(jo.getDouble("OldPrice"));
                        goods.setProductImg(jo.getString("ProductImg"));
                        goods.setProductUrl(jo.getString("ProductUrl"));
                        goods.setSaleTotal(jo.getInt("SaleTotal"));
                        list.add(goods);
                    }
                    adapter.addList(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>map = new HashMap<>();
                map.put("act","getproductlist");
                map.put("pages",index+"");
                return map;
            }
        };
        request.setTag("cancel");
        ODApp.queue.add(request);
    }

    private void initView() {//初始化header控件
        vpHeader = ((ViewPager) headView.findViewById(R.id.vp_front_header));
        llIcons = (LinearLayout) headView.findViewById(R.id.ll_icons);
        GlHeader = ((GridLayout) headView.findViewById(R.id.gl_front_header));
//        Gl2 = ((GridLayout) headView.findViewById(R.id.gl_front2));
        //首页的四张图viewpager
        final List<View> imgs = new ArrayList<>();
        StringRequest sRequest = new StringRequest(Request.Method.POST, "http://www.1zhebaoyou.com/apptools/indexad" +
                ".aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONObject(response).getJSONArray("adList");
                    for (int i = 0; i < ja.length(); i++) {
                        index=i;
                        JSONObject jo = ja.getJSONObject(i);
                        final String cName = jo.getString("cName");
                        String imgUrl = jo.getString("imgUrl");
                        ImageRequest request = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                ImageView iv=new ImageView(getContext());
                                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                iv.setImageBitmap(response);
                                iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), HeaderDetailActivity.class);
                                        intent.putExtra("index",index);
                                        intent.putExtra("cName",cName);
                                        intent.putExtra("img",response);
                                        startActivity(intent);
                                    }
                                });
                                imgs.add(iv);
                                Log.i("==img", "onResponse: "+imgs.size());
                                if(imgs.size()==4){
                                    ODPagerAdapter iAdapter =new ODPagerAdapter(imgs,null);
                                    vpHeader.setAdapter(iAdapter);
                                    handler.sendEmptyMessage(1);
                                }
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, null);
                        request.setTag("cancel");
                        ODApp.queue.add(request);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("v", "33");
                return map;
            }
        };
        sRequest.setTag("cancel");
        ODApp.queue.add(sRequest);

        iv1 = (ImageView) headView.findViewById(R.id.iv_header_fengqiang);
        iv2 = (ImageView) headView.findViewById(R.id.iv_header_meishi);
        iv3 = (ImageView) headView.findViewById(R.id.iv_header_zhoubian);
        final ImageView []imageViews={iv1,iv2,iv3};
        //最后疯抢等
        StringRequest sRequest2=new StringRequest("http://www.1zhebaoyou.com/apptools/app.aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {
//                        final ImageView iv = (ImageView) Gl2.getChildAt(i);
                        final ImageView iv = imageViews[i];
                        String imgUrl = ja.getJSONObject(i).getString("imgUrl");
                        ImageRequest request =new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                                iv.setImageBitmap(response);
                            }
                        },0,0, Bitmap.Config.RGB_565,null );
                        request.setTag("cancel");
                        ODApp.queue.add(request);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },null);
        sRequest2.setTag("cancel");
        ODApp.queue.add(sRequest2);

        //设置gridlayout的子控件宽度
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        for (int i = 0; i < 8; i++) {
            GlHeader.getChildAt(i).setMinimumWidth(width/4);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ODApp.queue.cancelAll("cancel");
    }
}
