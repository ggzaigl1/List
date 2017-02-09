package Application;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import Application.ProgressWeb.ProgressWebViewActivity;

import com.gab.myapplication.R;

import Application.StatusBarColor.StatusBarMainActivity;
import Application.recyclerviewdelete.Deleteactivity;
import Application.recycleviewday.RecyclerViewDay;
import Application.recycleviewhead.RecycleviewStickyhead;
import Application.xrecycler.XRecyclerActivity;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        toolbar.setTitle("主页");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
    }

    public void Onxrecycler(View view) {
        jump(XRecyclerActivity.class);
    }

    public void OnMaterialDialog(View view) {
        jump(Navigationactivity.class);
    }

    public void OnRecycleViewDay(View view) {
        jump(RecyclerViewDay.class);
    }

    public void OnDeleteactivity(View view) {
        jump(Deleteactivity.class);
    }

    public void OnRecycleviewStickyhead(View view) {
        jump(RecycleviewStickyhead.class);
    }

    public void OnProgressWebView(View view) {
        jump(ProgressWebViewActivity.class);
    }

    public void OnStatusBarColor(View view) {
        jump(StatusBarMainActivity.class);
    }

    public void OnViewPageIndicator(View view) {
        jump(ViewPageIndicator.class);
    }

    public void OnScrollingActivity(View view) {
        jump(ScrollingActivity.class);
    }


    /**
     * 泛型Intent
     * @param clz
     */
    public void jump(Class<? extends Activity> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    //退出程序
    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(MainActivity.this)
                .limitIconToDefaultSize()
                .title("确认退出吗")
                .content("确定要退出程序吗?")
                .positiveText(android.R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        MainActivity.this.finish();
                    }
                }).negativeText(android.R.string.no).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        }).show();
    }
}
