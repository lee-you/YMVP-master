package io.liyou.sample.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import io.liyou.sample.contract.BaseRefreshContract;
import io.liyou.ymvp.presenters.YBasePresenter;
import io.liyou.ymvp.view.base.YBaseActivity;

/**
 * Time: 2018/11/7 0007
 * Created by LiYou
 * Description :
 */
public abstract class BaseRefreshActivity <X extends BaseQuickAdapter, T extends YBasePresenter> extends YBaseActivity implements
        BaseRefreshContract.View,
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    protected int currentPage;//当前的页面
    protected X mAdapter;
    protected boolean isEnd;
    protected SwipeRefreshLayout swipeLayout;
    protected RecyclerView mRecyclerView;

    @Override
    public void onInit() {
        mAdapter = getAdapter();
        mRecyclerView = getRecyclerView();
        swipeLayout = getSwipeLayout();
        swipeLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }


    protected abstract X getAdapter();

    protected abstract SwipeRefreshLayout getSwipeLayout();

    protected abstract RecyclerView getRecyclerView();

    /**
     * 更新列表成功
     */
    @Override
    public void updateListSuccess(List datas, boolean isEnd) {
        this.isEnd = isEnd;
        currentPage = 1;
        mAdapter.setNewData(datas);
        swipeLayout.setRefreshing(false);
        if (isEnd) {
            loadListDateOver();
        } else {
            mAdapter.setEnableLoadMore(true);
            mAdapter.loadMoreComplete();
        }
    }

    /**
     * 更新失败
     */
    @Override
    public void updateListFail(String err) {
        swipeLayout.setRefreshing(false);
        mAdapter.setEnableLoadMore(true);
    }

    /**
     * 加载数据成功
     */
    @Override
    public void loadListDataSuccess(List datas, int currentPage, boolean isEnd) {
        this.currentPage = currentPage;
        this.isEnd = isEnd;
        mAdapter.addData(datas);
        swipeLayout.setEnabled(true);
        mAdapter.loadMoreComplete();
    }

    /**
     * 加载数据失败
     *
     * @param err
     */
    @Override
    public void loadListDataFail(String err) {
        swipeLayout.setEnabled(true);
        mAdapter.loadMoreFail();
    }

    /**
     * 数据已经被加载完
     */
    @Override
    public void loadListDateOver() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void onRefresh() {
        if (!swipeLayout.isRefreshing())
        {
            swipeLayout.setRefreshing(true);
        }
        getPresenter().requestUpdateListData();
        mAdapter.setEnableLoadMore(false);
    }

    @Override
    public void onLoadMoreRequested() {
        if (isEnd) {
            loadListDateOver();
            return;
        }
        swipeLayout.setEnabled(false);
        getPresenter().requestLoadListData(++currentPage);
    }

    protected BaseRefreshContract.Presenter getPresenter() {
        if (presenter instanceof BaseRefreshContract.Presenter) {
            return  (BaseRefreshContract.Presenter) presenter;
        } else {
            throw new RuntimeException("presenter please extends BaseRefreshContract.Presenter");
        }
    }


}
