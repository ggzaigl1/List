package Application.recyclerviewdelete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gab.myapplication.R;


public class Deleteactivity extends AppCompatActivity implements Adapter.IonSlidingViewClickListener {

    private RecyclerView mRecyclerView;

    private Adapter mAdapter;

    private final String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        initView();
        setAdapter();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Recycler侧滑删除");
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

    private void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new Adapter(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG, "" + position);
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        mAdapter.removeData(position);
    }
}
