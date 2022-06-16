package ltd.maimeng.core.ui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.hjq.toast.ToastUtils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ltd.maimeng.core.R;
import ltd.maimeng.core.ui.BaseActivity;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/13
 *     desc   : 列表界面（进入首次加载数据、网络错误界面等用StatusActivity的；空数据、下拉刷新、上拉加载更多、没有更多数据等用RecyclerActivity的）
 * </pre>
 */
public abstract class RecyclerActivity<T> extends BaseActivity implements RecyclerBehavior<ListBean> {

    private SwipeRefreshLayout mRefreshLayout;
    private RelativeLayout layoutNoData;

    private ListCache<ListBean> listCache;
    private RecyclerAdapter mAdapter;
    // private LoadMoreWrapper mLoadMore;

    @Override
    public View setContentView() {
        View contentView = View.inflate(RecyclerActivity.this, R.layout.activity_recycler, null);
        return contentView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listCache = bindCache();

        mRefreshLayout = findViewById(R.id.recycler_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listCache.setPageIndex(1);
                // mLoadMore.setLoadMoreEnabled(false);
                getData();
            }
        });

        RecyclerView mRecyclerView = findViewById(R.id.recycler_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(RecyclerActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(RecyclerActivity.this).bindData(listCache);
        bindViewHolder(mAdapter);
        mRecyclerView.setAdapter(mAdapter);

//        mLoadMore = LoadMoreWrapper.with(mAdapter);
//        mLoadMore.setFooterView(View.inflate(RecyclerActivity.this, R.layout.view_load_more_loadding, null))
//                .setNoMoreView(View.inflate(RecyclerActivity.this, R.layout.view_load_no_more_data, null))
//                .setShowNoMoreEnabled(true)
//                .setLoadMoreEnabled(false)
//                .setListener(new LoadMoreAdapter.OnLoadMoreListener() {
//                    @Override
//                    public void onLoadMore(LoadMoreAdapter.Enabled enabled) {
//                        if (mRefreshLayout.isRefreshing()) {
//                            return;
//                        }
//                        if (enabled.getLoadMoreEnabled()) {
//                            listCache.indexAdd();
//                            getData();
//                        }
//                    }
//                })
//                .into(mRecyclerView);

        layoutNoData = findViewById(R.id.recycler_no_data);

        getData();
    }

    public abstract ListCache<ListBean> bindCache();

    public abstract void bindViewHolder(RecyclerAdapter mAdapter);

    public abstract void getData();

    @Override
    public void onBeforeRequest() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onAfterRequest() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDataSuccess(List<ListBean> datas) {
        if (listCache.getPageIndex() == 1) {
            listCache.reset();
        }
        if (datas.size() < listCache.getPageSize()) {
            listCache.setHasMore(false);
            // mLoadMore.setLoadMoreEnabled(false);
        } else {
            // mLoadMore.setLoadMoreEnabled(true);
        }

        listCache.addData(datas);
        mAdapter.notifyDataSetChanged();

        if (listCache.getPageIndex() == 1 && listCache.getData().size() == 0) {
            showNoDataLayout();
        } else {
            showRecyclerLayout();
        }
    }

    @Override
    public void onDataFailure(String msg) {
        ToastUtils.show(msg);
    }

    protected void showNoDataLayout() {
        switchLayout(layoutNoData);
    }

    protected void showRecyclerLayout() {
        switchLayout(mRefreshLayout);
    }

    private void switchLayout(View view) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setVisibility(mRefreshLayout == view ? View.VISIBLE : View.GONE);
        }
        if (layoutNoData != null) {
            layoutNoData.setVisibility(layoutNoData == view ? View.VISIBLE : View.GONE);
        }
    }
}
