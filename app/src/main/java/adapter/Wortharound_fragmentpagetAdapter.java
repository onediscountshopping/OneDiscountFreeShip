package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 熊二 on 2016/7/26.
 */
public class Wortharound_fragmentpagetAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmengts;
    private String[] mTitles;



    public Wortharound_fragmentpagetAdapter(FragmentManager fm, List<Fragment> fragmengts, String[] mTitles) {
        super(fm);
        this.fragmengts = fragmengts;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmengts.get(position);
    }

    @Override
    public int getCount() {
        return fragmengts!=null?fragmengts.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
