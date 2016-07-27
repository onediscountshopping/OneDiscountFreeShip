package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.administrator.onediscountfreeship.CategorySearch_Activity;
import com.example.administrator.onediscountfreeship.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 熊二 on 2016/7/27.
 */
public class CategorySearch_fragment extends Fragment {

    private static final String TAG = "CategorySearch_fragment";
    private GridView mGridView;
    private int[] imges = {R.mipmap.fushi, R.mipmap.nanzhuang,
            R.mipmap.jujia, R.mipmap.muying, R.mipmap.xiebao,
            R.mipmap.nieyi, R.mipmap.meishi, R.mipmap.shuma,
            R.mipmap.huazhuangpin, R.mipmap.wenti, R.mipmap.oldman,
            R.mipmap.peishi, R.mipmap.jkj, R.mipmap.esfd,
            R.mipmap.newsort};
    private String[] name = {"女装", "男装", "居家",
            "母婴", "鞋帽", "内衣", "美食", "数码家电"
            , "美妆个护", "文体", "中老年", "配饰", "9.9包邮",
            "20元封顶", "今日更新",};
    private List<Map<String, Object>> data;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categorysearch_fragment, container, false);
        mGridView = (GridView) view.findViewById(R.id.mGridView);

        initData();
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.categorysearch_item, new String[]{"name", "imges"}, new int[]{

                R.id.CategorySearch_item_name, R.id.CategorySearch_item_image});
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = data.get(position);
                String name = (String) map.get("name");
                Log.e(TAG, "onItemClick:data.indexOf(position)= "+data.indexOf(position));
                Intent intent = new Intent(getActivity(), CategorySearch_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name",name);
                bundle.putInt("position",position);
                intent.putExtra("filtrate",bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initData() {
        data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imges.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name[i]);
            map.put("imges", imges[i]);
            data.add(map);
        }

    }
}
