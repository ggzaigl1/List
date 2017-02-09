package Application.StatusBarColor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gab.myapplication.R;

public class StatusBarMainActivity extends BaseActivity {
    NavigationView mNavView;
    Toolbar mToolbar;
//    ListView mListView;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusbarcolor_main);
        initView();
        navViewToTop();
    }


    private void initView() {
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mListView = (ListView) findViewById(R.id.list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setUpDrawer();
//        initList();
        initNavigationView();
    }

    private void setUpDrawer() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.rounded, R.string.squared);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigationView() {
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_item1:
                        startActivity(TopImageActivity.class);
                        break;
                    case R.id.nav_item2:
                        startActivity(TabActivity.class);
                        break;
                    case R.id.nav_item3:
                        startActivity(PlanTwoActivity.class);
                        break;
                    case R.id.nav_item4:
                        startActivity(PlanThreeActivity.class);
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

//    private void initList() {
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add("item" + i);
//        }
//        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
//        mListView.setAdapter(adapter);
//    }

    private void navViewToTop() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mDrawerLayout.setFitsSystemWindows(true);
            mDrawerLayout.setClipToPadding(false);
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(StatusBarMainActivity.this, cls);
        startActivity(intent);
    }

}

