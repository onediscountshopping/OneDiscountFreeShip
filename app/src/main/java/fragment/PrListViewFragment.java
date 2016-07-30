package fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.onediscountfreeship.GoodsDetailActivity;
import com.example.administrator.onediscountfreeship.ODApp;
import com.example.administrator.onediscountfreeship.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.FrontBaseAdapter;
import bean.Goods;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrListViewFragment extends Fragment {
    private OperateListener listener;
    private PullToRefreshListView lv;
    private String url;
    private Map<String,String> map;
    private FrontBaseAdapter adapter;

    public PrListViewFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public PrListViewFragment(OperateListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("==", "onCreateView: -----");
        View view = inflater.inflate(R.layout.fragment_pr_listview, container, false);
        lv= (PullToRefreshListView) view.findViewById(R.id.pull_to_refresh);
        url = getArguments().getString("url");
        map = (Map<String, String>) getArguments().getSerializable("map");
        adapter = new FrontBaseAdapter(getContext(), null);
        lv.setAdapter(adapter);
        loadData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goods goods = (Goods) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("url",goods.getProductUrl());
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadData() {
        final List<Goods>list = new ArrayList<>();
        Log.i("==", "loadData: "+url);
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("==", "onResponse: "+response);
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
        },null ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setTag("canel");
        ODApp.queue.add(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ODApp.queue.cancelAll("cancel");
    }
}
interface OperateListener {
    public void operate(PullToRefreshListView lv);
}
