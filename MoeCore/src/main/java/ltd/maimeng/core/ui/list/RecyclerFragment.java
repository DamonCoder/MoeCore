package ltd.maimeng.core.ui.list;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hjq.toast.ToastUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ltd.maimeng.core.R;
import ltd.maimeng.core.ui.BaseFragment;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/13
 *     desc   : 列表界面（进入首次加载数据、网络错误界面等用StatusActivity的；空数据、下拉刷新、上拉加载更多、没有更多数据等用RecyclerActivity的）
 * </pre>
 */
public abstract class RecyclerFragment<T> extends BaseFragment implements RecyclerBehavior<ListBean> {

    private SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    private FrameLayout layoutNoData;

    private ListCache<ListBean> listCache;
    protected RecyclerAdapter mAdapter;
    // private LoadMoreWrapper mLoadMore;

    // private int footerViewResId = R.layout.view_load_more_loadding;
    private int footerViewResId = 0;
    private int noMoreViewResId = R.layout.view_load_no_more_data;

    @Override
    public View setContentView() {
        View contentView = View.inflate(getContext(), R.layout.fragment_recycler, null);
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listCache = bindCache();

        mRefreshLayout = view.findViewById(R.id.recycler_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listCache.setPageIndex(1);
                // mLoadMore.setLoadMoreEnabled(false);
                getData();
            }
        });

        mRecyclerView = view.findViewById(R.id.recycler_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(getActivity()).bindData(listCache);
        bindViewHolder(mAdapter);
        mRecyclerView.setAdapter(mAdapter);

//        mLoadMore = LoadMoreWrapper.with(mAdapter);
//        mLoadMore.setFooterView(footerViewResId)
//                .setNoMoreView(noMoreViewResId)
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

        layoutNoData = view.findViewById(R.id.recycler_no_data);

        getData();
    }

    public void setFooterViewResId(int footerViewResId) {
        this.footerViewResId = footerViewResId;
    }

    public void setNoMoreView(int noMoreViewResId) {
        this.noMoreViewResId = noMoreViewResId;
    }

    protected void setEmptyView(int layoutId) {
        layoutNoData.removeAllViews();
        layoutNoData.addView(View.inflate(getActivity(), layoutId, null), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        layoutNoData.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    protected void showRecyclerLayout() {
        layoutNoData.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    protected void onReceiveEventBusRefresh() {
        listCache.setPageIndex(1);
        // mLoadMore.setLoadMoreEnabled(false);
    }
}
