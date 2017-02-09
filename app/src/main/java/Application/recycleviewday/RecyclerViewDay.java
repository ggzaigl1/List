package Application.recycleviewday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gab.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDay extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<DemoBean> mDataList = new ArrayList<DemoBean>();

    private DemoAdapter mAdapter;
    private List<DemoBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_main);
        initView();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Recycler显示日期");
        }
        mDataList = getData();
        mAdapter = new DemoAdapter(this, mDataList, null);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

    }

    private List<DemoBean> getData() {
        for (int i = 0; i < 3; i++) {
            DemoBean bean = new DemoBean();
            bean.setTimeend("2014-12-12 12:45:60");
            bean.setTime("2016-02-02");
            bean.setPaytype("微信支付");
            bean.setMoney("0.02");
            bean.setOuttradeno("P2013123123156");
            mDataList.add(bean);

        }

        for (int i = 0; i < 3; i++) {
            DemoBean bean = new DemoBean();
            bean.setTimeend("2016-02-06 12:45:60");
            bean.setTime("2016-02-06");
            bean.setPaytype("支付宝支付");
            bean.setMoney("0.04");
            bean.setOuttradeno("P2013123123156");
            mDataList.add(0, bean);
        }
        for (int i = 0; i < 3; i++) {
            DemoBean bean = new DemoBean();
            bean.setTimeend("2016-02-06 12:45:60");
            bean.setTime("2016-02-06");
            bean.setPaytype("唐人支付");
            bean.setMoney("0.04");
            bean.setOuttradeno("P2013123123156");
            mDataList.add(0, bean);
        }
        for (int i = 0; i < 3; i++) {
            DemoBean bean = new DemoBean();
            bean.setTimeend("2016-02-08 12:45:60");
            bean.setTime("2016-02-08");
            bean.setPaytype("QQ支付");
            bean.setMoney("0.04");
            bean.setOuttradeno("P2013123123156");
            mDataList.add(0, bean);
        }

        return mDataList;
    }
}
