package fragment;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.onediscountfreeship.R;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.Wortharound_fragmentpagetAdapter;


/**
 * Created by 熊二 on 2016/7/26.
 */
public class Wortharound_fragment extends Fragment {
    private static final String TAG = "Wortharound_fragment";
    private ViewPager mViewpager;
    private TabLayout mTabLayout;
    private View views;
    private List<View> mViewList = new ArrayList<>();

    private List<Fragment> fragmengts;
    private String[] mTitles = new String[]{"精选", "女装", "男装", "居家", "母婴", "鞋包", "配饰", "美食", "数码", "美妆", "文体",};

//    private String path = "http://www.1zhebaoyou.com/apptools/productlist.aspx&act=getworth&pages=1&bc=%d&brandid=0&v=33";
//    private int bc = 0;
//    private String finalPath;

    private Wortharound_fragmentpagetAdapter fragAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wortharound_fragment, container, false);

        mViewpager = (ViewPager) view.findViewById(R.id.wortharound_viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.wortharound_tabLayout);
        fragmengts = new ArrayList<>();


//        finalPath = String.format(path, bc);

        fragAdapter = new Wortharound_fragmentpagetAdapter(getChildFragmentManager(), fragmengts, mTitles);
//        创建Fragment集合
        for (int i = 0; i < 11; i++) {
            Wortharound_item_fragment fragment = new Wortharound_item_fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("bc", i);
            Log.e(TAG, "onCreateView: i"+i );
            fragment.setArguments(bundle);
            fragmengts.add(fragment);
        }
        //添加标题
        for (int i = 0; i < mTitles.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitles[i]));
        }

        mViewpager.setAdapter(fragAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }


}
