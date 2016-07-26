package adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yhy on 2016/7/26.
 */
public class ODPagerAdapter extends PagerAdapter {
    private List<View> views;
    private List<String> titles;

    public ODPagerAdapter(List<View> views, List<String> titles) {
        this.views = views;
        if (titles != null) {
            this.titles = titles;
        }
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles.get(position);
        } else
            return super.getPageTitle(position);
    }
}
