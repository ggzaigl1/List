package Application;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gab.myapplication.R;

import Application.StatusBarColor.BaseActivity;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
public class SettingsActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
