package Application;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;
import com.gab.myapplication.R;

/**
 * Created by Administrator on 2016/11/4 0004.
 */

public class Navigationactivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static long lastClickTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationview);
        //设置ToolBar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("主页");
        setSupportActionBar(mToolbar);
        //设置抽屉DrawerLayout
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.content_hint, R.string.drawer_hint);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        //設置FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /**
         * 给DrawerLayout 头部 添加点击事件
         */
        View headerView = navigationView.getHeaderView(0);
        headerView.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Navigationactivity.this, "OnClicklistener", Toast.LENGTH_SHORT).show();
            }
        });
        //水波按钮设置
        final RippleView rippleView = (RippleView) findViewById(R.id.RippleView);
        rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防止控件被重复点击
                if (isFastDoubleClick()){
                    return;
                }else {
                    Toast.makeText(Navigationactivity.this, "onClick.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void logOut(final Activity activity) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                .title("Title")
                .titleColor(Color.parseColor("#F50057"))
                .content("Are You Sure You Want To A GirlFriend?")
                .positiveColor(Color.parseColor("#c2c7cc")).cancelable(false)
                .positiveText(android.R.string.yes).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Toast.makeText(activity, "I Have A GirlFriend", Toast.LENGTH_SHORT).show();
//                        activity.finish();
                    }
                }).negativeText(android.R.string.no).negativeColor(Color.parseColor("#146e6e")).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Toast.makeText(activity, "I Lost GirlFriend", Toast.LENGTH_SHORT).show();
                    }
                });
        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //在这里处理item的点击事件
        switch (item.getItemId()) {
            case R.id.story_start:
                logOut(Navigationactivity.this);
                break;
            case R.id.nav_squabble:
                new MaterialDialog.Builder(Navigationactivity.this)
                        .title("No Icon")
                        .content("Context")
                        .positiveText(android.R.string.yes)
                        .show();
                break;
            case R.id.nav_sweet:
                new MaterialDialog.Builder(Navigationactivity.this)
                        .iconRes(R.drawable.ic_launcher)
                        .limitIconToDefaultSize()
                        .title("Icon")
                        .content("Context")
                        .positiveText(android.R.string.yes)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(Navigationactivity.this, android.R.string.yes, Toast.LENGTH_SHORT).show();
                            }
                        }).negativeText(android.R.string.no).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Toast.makeText(Navigationactivity.this, android.R.string.no, Toast.LENGTH_SHORT).show();
                    }
                }).show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setIcon(R.drawable.ic_launcher);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
