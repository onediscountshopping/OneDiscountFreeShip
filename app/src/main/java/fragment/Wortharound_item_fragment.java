package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.onediscountfreeship.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;


import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import adapter.Wortharound_listviewAdapter;
import bean.Wortharound_bean;

/**
 * Created by 熊二 on 2016/7/26.
 */
public class Wortharound_item_fragment extends Fragment {

    private static final String TAG = "Wortharound_item_fragment";
    private int bc;

    //private Wortharound_bean.RowsBean rowsBean;
   // private List<Wortharound_bean.RowsBean> data;
    private Wortharound_listviewAdapter listAdapter;
    private ListView mlistView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);
        mlistView = (ListView) view.findViewById(R.id.listview);

        bc = getArguments().getInt("bc");

        listAdapter = new Wortharound_listviewAdapter(getActivity());
        postJson(getArguments().getInt("bc"));
        mlistView.setAdapter(listAdapter);

        return view;
    }

    public void postJson(int bc) {
        // act=getworth&pages=1&bc=2&brandid=0&v=33
        Log.e(TAG, "postJson: bc=" + bc);
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("act", "getworth");
        builder.add("pages", "1");
        builder.add("bc", "" + bc);
        builder.add("brandid", "0");
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
                    Log.e(TAG, "response.body().String(): =" + s);
                    final Wortharound_bean mBean = gson.fromJson(s, Wortharound_bean.class);
                    Log.e(TAG, "onResponse: mBean" + mBean);

                    //JSONObject object = new JsonObject(response.body().string());

                    mlistView.post(new Runnable() {
                        @Override
                        public void run() {
                            listAdapter.setData(mBean.getRows());
                        }
                    });
                }
            }
        });
    }
}
