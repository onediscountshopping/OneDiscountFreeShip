package com.example.administrator.onediscountfreeship;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import adapter.FrontBaseAdapter;
import bean.Goods;

public class HeaderDetailActivity extends AppCompatActivity {
    private String url="http://www.1zhebaoyou.com/apptools/productlist.aspx";
    private int pages=1;
    private int[]actid={14,15,9,13};
    private TextView tv;
    private ListView lv;
    private FrontBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_detail);
        int index = getIntent().getIntExtra("index",-1);
        tv = (TextView) findViewById(R.id.tv_detail_title);
        tv.setText(getIntent().getStringExtra("cName"));
        lv = (ListView) findViewById(R.id.lv_header_detail);
        Bitmap img = (Bitmap) getIntent().getParcelableExtra("img");
        ImageView imageView = new ImageView(this);
        AbsListView.LayoutParams params=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,170);
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(img);
        lv.addHeaderView(imageView);
        adapter = new FrontBaseAdapter(this,null);
        lv.setAdapter(adapter);
        loadData(index);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goods goods = (Goods) parent.getItemAtPosition(position);
                Intent intent = new Intent(HeaderDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra("url",goods.getProductUrl());
                startActivity(intent);
            }
        });
    }

    private void loadData(final int index) {
        final List<Goods>list = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                map.put("pages",pages+"");
                map.put("actid",actid[index]+"");
                return map;
            }
        };
        request.setTag("cancel");
        ODApp.queue.add(request);
    }
}
