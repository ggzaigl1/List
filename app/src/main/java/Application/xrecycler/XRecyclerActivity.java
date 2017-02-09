package Application.xrecycler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gab.myapplication.R;

public class XRecyclerActivity extends Activity {

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xrecyclerview);

		toolbar = (Toolbar) findViewById(R.id.tl_custom);
		toolbar.setTitle("XRecycleView");//设置Toolbar标题
	}

	public void gotoLinearActivity(View v) {
		Intent intent = new Intent();
		intent.setClass(this, XRecyclerViews.class);
		startActivity(intent);
	}

	public void gotoGridActivity(View v) {
		Intent intent = new Intent();
		intent.setClass(this, GridActivity.class);
		startActivity(intent);
	}

	public void gotoStaggeredGridActivity(View v) {
		Intent intent = new Intent();
		intent.setClass(this, StaggeredGridActivity.class);
		startActivity(intent);
	}

}
