package Application.StatusBarColor;

import android.os.Bundle;

import com.gab.myapplication.R;

public class PlanTwoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_two);
        addStatusBarView();
    }
}
