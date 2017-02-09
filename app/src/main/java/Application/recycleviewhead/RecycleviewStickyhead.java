package Application.recycleviewhead;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gab.myapplication.R;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class RecycleviewStickyhead extends AppCompatActivity {
    private int mHalfScreenWidth;

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private StaggeredGridLayoutManager mManager;

    private RelativeLayout mStickyHeadLayout;
    private RelativeLayout mFakeStickyHeadLayout;
    private TextView mStickyHead;
    private TextView mFakeStickyHead;
    private int mStickyHeadHeight;

    private int mHeaderOneCount = 0;

    private List<Integer> mProductInfos;
    private int mProductInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swickyhead);
        initView();
        initData();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("瀑布流粘性头部");
        }
        // 帧布局Header One的头部
        mStickyHeadLayout = (RelativeLayout) findViewById(R.id.rl_sticky_head);
        mStickyHead = (TextView) findViewById(R.id.tv_sticky_head);
        mStickyHead.setText("Header One");
        mStickyHead.measure(0, 0);
        // 获取头部的高度，作为是否移动头部的范围
        mStickyHeadHeight = mStickyHead.getMeasuredHeight();

        // 帧布局的Header Two的头部，在帧布局里面的最外的一层，
        // 默认为不可见，用于recycleview移动真正的第二个头部到刚刚出界面时设置为可见
        // 形成一种recycleview真正的第二个头部到顶端停住的效果
        // 实际recycleview真正的第二个头部还是继续上移，只是帧布局的Header Two的头部设置为可见而已
        mFakeStickyHeadLayout = (RelativeLayout) findViewById(R.id.rl_sticky_head_fake);

        mFakeStickyHead = (TextView) findViewById(R.id.tv_fake_sticky_head);
        mFakeStickyHead.setText("Header Two");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.addOnScrollListener(new MyScrollListener());

    }

    class MyScrollListener extends RecyclerView.OnScrollListener {
        // 用于获取瀑布流的不完全可见的位置,0为左边不完全可见的位置，1为右边不完全可见的位置
        int mVisiblePosition[] = new int[2];
        // recycleview的第二个头部，内容高度跟帧布局的Header Two的头部一模一样
        private View header;
        // 用于recycleview的第二个头部刚刚进入帧布局Header One的头部的范围时，
        // 强制设置位移量dy为1，防止用户上推过快，dy过大造成的帧布局Header One的头部不同步移动
        boolean isFirstIn;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 查找第一个不完全可见的左右两个item的位置
            mManager.findFirstVisibleItemPositions(mVisiblePosition);

            // 获取第二个头部的示例，有可能为null
            header = mManager.findViewByPosition(mHeaderOneCount + 1);

            if (mHeaderOneCount != 0 && header != null && ViewHelper.getY(header) > 0 && ViewHelper.getY(header) < mStickyHeadHeight) {
                //  0<ViewHelper.getY(header)<mStickyHeadHeight 说明recycleview的第二个头部
                // 进入帧布局Header One的头部高度的范围，此时可以移动帧布局Header One的头部
                if (!isFirstIn) {
                    // 刚刚进入高度范围强制设置位移量dy为1，防止用户上推过快，
                    // dy过大造成的帧布局Header One的头部不同步移动
                    isFirstIn = true;
                    dy = 1;
                }
                // 移动帧布局Header One的头部
                mStickyHeadLayout.scrollBy(0, dy);
                // 在此过程中帧布局的Header Two的头部设为不可见
                mFakeStickyHeadLayout.setVisibility(View.INVISIBLE);
                if (mStickyHeadLayout.getScrollY() < 0) {
                    // 防止上推过快，移动帧布局Header One的头部上移超过其高度
                    mStickyHeadLayout.scrollBy(0, -mStickyHeadLayout.getScrollY());
                }
            } else if (mHeaderOneCount != 0 && header != null && ViewHelper.getY(header) <= 0) {
                //  recycleview的第二个头部刚刚移出界面的时候，设置帧布局的Header Two的头部为可见
                //  这样看上去好像recycleview的第二个头部刚好停在顶部
                mFakeStickyHeadLayout.setVisibility(View.VISIBLE);
            } else if (mHeaderOneCount != 0 && header != null && ViewHelper.getY(header) >= mStickyHeadHeight) {
                // recycleview的第二个头部超出帧布局Header One的头部高度的范围，
                // 此时设置isFirstIn为false方便下次上推设dy值
                isFirstIn = false;
            }

            if (header != null && dy > 0 && mStickyHeadLayout.getScrollY() != mStickyHeadHeight && ViewHelper.getY(header) < 0) {
                // 这个情况是针对recycleview的第二个头部移出布局的时候，由于上推滑动过快，
                // 造成的同步上移的帧布局Header One的头部未完全移出或移出超过其高度
                // 这里再调整帧布局Header One的头部上移距离为其高度，即刚刚好移出界面
                mStickyHeadLayout.scrollBy(0, mStickyHeadHeight - mStickyHeadLayout.getScrollY());
            } else if (header != null
                    // 这个情况是针对下推的时候，recycleview的第二个头部超出帧布局Header One的头部高度的范围，
                    // 但是帧布局Header One的头部未完全下移到原来的位置，这里再调整它移回到最初的位置
                    && dy < 0 && mStickyHeadLayout.getScrollY() > 0 && ViewHelper.getY(header) > mStickyHeadHeight
                    // 这个起来是针对使用RecyclerView.scrollToPosition(0)的时候，如果此时已经上推了帧布局Header One的头部，
                    // 此时帧布局Header One的头部不会回到原来位置，因为scrollToPosition太快了
                    // 上面的ViewHelper.getY(header) > 0 && ViewHelper.getY(header) < mStickyHeadHeight此处的判断执行不了
                    // 下移不回原来的位置，所以这里特殊处理，如果位置为0的时候，帧布局Header One的头部的scrollY不为0，强制移回原处
                    || (mStickyHeadLayout.getScrollY() > 0 && mVisiblePosition[0] == 0)) {
                mStickyHeadLayout.scrollBy(0, -mStickyHeadLayout.getScrollY());
            }

            if (mVisiblePosition[0] > mHeaderOneCount + 1 && mFakeStickyHeadLayout.getVisibility() == View.INVISIBLE) {
                // 针对用户超快速滑动情况，当位置大于第二个头部的时候，如果FakeStickyHeadLayout还为不可见的话，需要设为可见
                mFakeStickyHeadLayout.setVisibility(View.VISIBLE);
            } else if (mVisiblePosition[0] < mHeaderOneCount + 1 && mFakeStickyHeadLayout.getVisibility() == View.VISIBLE) {
                // 针对用户超快速滑动情况，当位置小于第二个头部的时候，如果FakeStickyHeadLayout还为可见的话，需要设为不可见
                mFakeStickyHeadLayout.setVisibility(View.INVISIBLE);
            }

        }
    }

    class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;
        private StaggeredGridLayoutManager.LayoutParams lp;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);

            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            if (position == 0) {
                outRect.top = 0;
            } else if (position == mHeaderOneCount + 1) {
                outRect.top = 0;
            } else {
                lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                // 左item的对右间隔设为0，保证item间隔一致
                if (lp.getSpanIndex() == 0) {
                    outRect.right = 0;
                }
            }
        }
    }

    private void initData() {

        mProductInfos = new ArrayList<>();

        mProductInfos.add(R.drawable.ic_girls_0);
        mProductInfos.add(R.drawable.ic_girls_1);
        mProductInfos.add(R.drawable.ic_girls_2);
        mProductInfos.add(R.drawable.ic_girls_3);
        mProductInfos.add(R.drawable.ic_girls_4);
        mProductInfos.add(R.drawable.ic_girls_5);
        mProductInfos.add(R.drawable.ic_girls_6);
        mProductInfos.add(R.drawable.ic_girls_7);
        mProductInfos.add(R.drawable.ic_girls_8);
        mProductInfos.add(R.drawable.ic_girls_9);
        mProductInfos.add(R.drawable.ic_girls_10);

        // Header One下面的图片总数
        mHeaderOneCount = mProductInfos.size();
        mProductInfos.add(R.drawable.ic_view_0);
        mProductInfos.add(R.drawable.ic_view_1);
        mProductInfos.add(R.drawable.ic_view_2);
        mProductInfos.add(R.drawable.ic_view_3);
        mProductInfos.add(R.drawable.ic_view_4);
        mProductInfos.add(R.drawable.ic_view_5);
        mProductInfos.add(R.drawable.ic_view_6);
        mProductInfos.add(R.drawable.ic_view_7);
        mProductInfos.add(R.drawable.ic_view_8);
        mProductInfos.add(R.drawable.ic_view_9);
        mProductInfos.add(R.drawable.ic_view_10);
        mProductInfos.add(R.drawable.ic_view_11);
        mProductInfos.add(R.drawable.ic_view_12);
        mProductInfos.add(R.drawable.ic_view_13);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mHalfScreenWidth = metrics.widthPixels / 2;

        // 瀑布流，两列，垂直方向
        mManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.common_margin_left)));
        mAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // recycleview返回位置0
            mRecyclerView.scrollToPosition(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

        class RecyclerViewHolder extends RecyclerView.ViewHolder {
            // item展示图片
            ImageView mProductImage;
            // 头部的文字
            TextView stickyTextview;

            public RecyclerViewHolder(View itemView) {
                super(itemView);
                mProductImage = (ImageView) itemView.findViewById(R.id.iv_home_product);
                stickyTextview = (TextView) itemView.findViewById(R.id.tv_sticky_head);
            }
        }

        // 头部类型
        public final int VIEW_TYPE_HEADER = 0;
        // item类型
        public final int VIEW_TYPE_REPLY = 1;

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerViewHolder viewHolder;
            switch (viewType) {
                case VIEW_TYPE_HEADER:
                    viewHolder = new RecyclerViewHolder
                            (LayoutInflater.from(RecycleviewStickyhead.this).inflate(R.layout.flashgo_header, parent, false));
                    return viewHolder;
                case VIEW_TYPE_REPLY:
                    viewHolder = new RecyclerViewHolder
                            (LayoutInflater.from(RecycleviewStickyhead.this).inflate(R.layout.recycleview_item, parent, false));
                    return viewHolder;
            }
            return null;
        }

        // 封装图片宽高
        ImageSize mImageSize;

        @Override
        public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

            switch (getItemViewType(position)) {
                case VIEW_TYPE_HEADER:
                    StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) holder.stickyTextview.getLayoutParams();
                    // 占满一行
                    lp.setFullSpan(true);
                    if (position == 0) {
                        holder.stickyTextview.setText("Header One");
                    } else {
                        holder.stickyTextview.setText("Header Two");
                    }
                    holder.stickyTextview.setLayoutParams(lp);

                    break;
                case VIEW_TYPE_REPLY:

                    if (position <= mHeaderOneCount) {
                        // 头部一占一个位置
                        mProductInfo = mProductInfos.get(position - 1);
                    } else {
                        // 头部一和二占两个位置
                        mProductInfo = mProductInfos.get(position - 2);
                    }

                    // 获取图片size
                    mImageSize = getImageSize(RecycleviewStickyhead.this, mProductInfo);

                    // 图片实际宽度设为屏幕一半，再等比例等到高度
                    final ViewGroup.LayoutParams lp1 = holder.mProductImage.getLayoutParams();
                    lp1.width = mHalfScreenWidth;
                    lp1.height = lp1.width * mImageSize.imageHeight / mImageSize.imageWidth;
                    holder.mProductImage.setLayoutParams(lp1);

                    // 加载图片
                    Glide.with(RecycleviewStickyhead.this)
                            .load(mProductInfo)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_loading)
                            .override(lp1.width, lp1.height)
                            .into(holder.mProductImage);
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            // 根据位置确定itemview的类型
            return position == 0 || position == mHeaderOneCount + 1 ? VIEW_TYPE_HEADER : VIEW_TYPE_REPLY;
        }

        @Override
        public int getItemCount() {
            // 处理mProductInfos还有两个头部
            return mProductInfos.size() + 2;
        }

    }

    private ImageSize getImageSize(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        return new ImageSize(options.outWidth, options.outHeight);
    }

    class ImageSize {
        int imageHeight;
        int imageWidth;

        public ImageSize(int imageWidth, int imageHeight) {
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
        }
    }
}
