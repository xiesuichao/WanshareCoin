package com.wanshare.common.widget.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用fragment的ViewPagerAdapter
 * </br>
 * Date: 2018/6/21 17:43
 *
 * @author hemin
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public CommonFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mFragments= list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment f = (Fragment) super.instantiateItem(container, position);
        // fragment重新创建后替换列表中对象
        mFragments.set(position,f);
        return f;
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 0 || mFragments == null || position >= mFragments.size()) {
            return null;
        }
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments==null?0:mFragments.size();
    }
}