package com.example.administrator.onediscountfreeship;
//最后疯抢

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.FengqiangAdapter;
import bean.Fengqiang;

public class FengqiangActivity extends AppCompatActivity {

    private ListView lv;
    private String url="http://www.1zhebaoyou.com/apptools/brandsale.aspx";
    private int pages=1;
    private FengqiangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fengqiang);
        lv = (ListView) findViewById(R.id.lv_fengqiang);
        adapter = new FengqiangAdapter(this,null);
        lv.setAdapter(adapter);
        loadData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fengqiang fq = (Fengqiang) parent.getItemAtPosition(position);
                Intent intent =new Intent(FengqiangActivity.this, FengqiangLvActivity.class);
//                intent.putExtra("fq",fq);
                intent.putExtra("name",fq.getName());
                intent.putExtra("discount",fq.getDisCount());
                intent.putExtra("brandId",fq.getBrandId());
                ImageView iv = (ImageView) view.findViewById(R.id.iv_fengqiang_sml);
                if (iv.getDrawingCache() != null) {
                intent.putExtra("img",iv.getDrawingCache());
                }
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        final List<Fengqiang>list = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    JSONArray ja = response.getJSONArray("rows");
                    JSONArray ja = new JSONObject(response).getJSONArray("rows");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        Fengqiang fq=new Fengqiang();
                        fq.setDisCount(jo.getDouble("DisCount"));
                        fq.setEndDate(jo.getString("EndDate"));
                        fq.setImgUrlSml("http://img.1zhebaoyou.com"+jo.getString("ImgUrlSml"));
                        fq.setName(jo.getString("Name"));
                        fq.setBrandId(jo.getInt("brandId"));
                        String info = jo.getString("ProductInfo");
                        Log.i("===", "onResponse: "+info);
//                        String json = info.replaceAll("\\\"", "\"").replaceAll("/", "").replaceAll("\\\\", "\\");
                        JSONArray array = new JSONArray(info);
                        List<Fengqiang.ProductInfo>infos = new ArrayList<>();
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject object = array.getJSONObject(j);
                            Fengqiang.ProductInfo pInfo =fq.new ProductInfo();
                            pInfo.setNewPrice(object.getDouble("NewPrice"));
                            pInfo.setOldPrice(object.getDouble("oldPrice"));
                            pInfo.setProductImg(object.getString("ProductImg"));
                            infos.add(pInfo);
                        }
                        Log.i("===", "onResponse: infos"+infos.size());
                        fq.setInfos(infos);
                        list.add(fq);
                    }
                    Log.i("===", "onResponse: list"+list.size());
                    adapter.addList(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },null ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("act","brandlist");
                map.put("cpage",pages+"");
                map.put("pagesize","20");
                map.put("bclass","97");
                map.put("v","33");
                return map;
            }
        };
        request.setTag("cancel");
        ODApp.queue.add(request);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {//drawable转bitmap
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ODApp.queue.cancelAll("cancel");
    }
}
