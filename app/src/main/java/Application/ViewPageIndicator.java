package Application;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.gab.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class ViewPageIndicator extends FragmentActivity {

    private ViewPager mViewPager;
    private ViewPagerIndicator mViewPagerIndicator;
    private List<String> mTitles = Arrays.asList("短信", "收藏", "推荐", "篮球", "足球", "羽毛球");
    private List<ContentFragment> mContentFragments = new ArrayList<>();
    private ContentFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewpageindicator);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("ViewPageIndicator");
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < mTitles.size(); i ++) {
            ContentFragment fragment = ContentFragment.newInstance(mTitles.get(i));
            mContentFragments.add(fragment);
        }
        mAdapter = new ContentFragmentAdapter(getSupportFragmentManager());
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.id_viewpager_indicator);
        mViewPagerIndicator.setTabItemTitles(mTitles);
        mViewPagerIndicator.setViewPager(mViewPager, 0);
    }


    class ContentFragmentAdapter extends FragmentPagerAdapter {

        public ContentFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mContentFragments.get(position);
        }
    }
}
