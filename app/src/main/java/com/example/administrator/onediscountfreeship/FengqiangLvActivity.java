package com.example.administrator.onediscountfreeship;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

import fragment.PrListViewFragment;

public class FengqiangLvActivity extends AppCompatActivity {

    private ImageView iv;
    private TextView tvName;
    private TextView tvDiscount;
    private DecimalFormat format = new DecimalFormat("0.0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fengqiang_lv);

        initView();
    }

    private void initView() {
//        Fengqiang fq = (Fengqiang) getIntent().getSerializableExtra("fq");
        String name = getIntent().getStringExtra("name");
        double discount = getIntent().getDoubleExtra("discount", 0);
        int brandId = getIntent().getIntExtra("brandId", -1);

        Bitmap img = (Bitmap) getIntent().getParcelableExtra("img");
        iv = (ImageView) findViewById(R.id.iv_lv_sml);
        tvName = (TextView) findViewById(R.id.tv_lv_name);
        tvDiscount = (TextView) findViewById(R.id.tv_lv_discount);
        if (img != null) {
            iv.setImageBitmap(img);
        }
        tvName.setText(name);
        tvDiscount.setText(format.format(discount)+"折起");
        PrListViewFragment fragment = new PrListViewFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url","http://www.1zhebaoyou.com/apptools/productlist.aspx");
        HashMap<String, String> map = new HashMap<>();
        map.put("act","getworth");
        map.put("pages","1");
        map.put("bc","0");
        map.put("brandid",brandId+"");
        map.put("v","33");
        bundle.putSerializable("map",map);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_refresh,fragment).commit();
    }
}
