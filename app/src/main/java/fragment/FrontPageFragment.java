package fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
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
    private GridLayout Gl2;
    private FrontBaseAdapter adapter;
    private int page=1;

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
        return view;
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
        Gl2 = ((GridLayout) headView.findViewById(R.id.gl_front2));
        //首页的四张图viewpager
        final List<View> imgs = new ArrayList<>();
        StringRequest sRequest = new StringRequest(Request.Method.POST, "http://www.1zhebaoyou.com/apptools/indexad" +
                ".aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONObject(response).getJSONArray("adList");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        String imgUrl = jo.getString("imgUrl");
                        ImageRequest request = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                ImageView iv=new ImageView(getContext());
                                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                iv.setImageBitmap(response);
                                imgs.add(iv);
                                Log.i("==img", "onResponse: "+imgs.size());
                                if(imgs.size()==4){
                                    ODPagerAdapter iAdapter =new ODPagerAdapter(imgs,null);
                                    vpHeader.setAdapter(iAdapter);
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

        //最后疯抢等
        StringRequest sRequest2=new StringRequest("http://www.1zhebaoyou.com/apptools/app.aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {
                        final ImageView iv = (ImageView) Gl2.getChildAt(i);
                        String imgUrl = ja.getJSONObject(i).getString("imgUrl");
                        ImageRequest request =new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
