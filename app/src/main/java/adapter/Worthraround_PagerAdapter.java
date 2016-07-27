package adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 熊二 on 2016/7/26.
 */
public class Worthraround_PagerAdapter extends PagerAdapter {
    private List<View> mViewList;
    private String[] mTitles;

    public Worthraround_PagerAdapter(List<View> mViewList, String[] mTitles) {
        this.mViewList = mViewList;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
