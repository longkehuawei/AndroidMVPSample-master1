package com.wuxiaolong.androidmvpsample.mvp.other;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.pull.BaseListAdapter;
import com.wuxiaolong.androidmvpsample.pull.BaseViewHolder;
import com.wuxiaolong.androidmvpsample.pull.DividerItemDecoration;
import com.wuxiaolong.androidmvpsample.pull.PullRecycler;
import com.wuxiaolong.androidmvpsample.pull.layoutmanager.ILayoutManager;
import com.wuxiaolong.androidmvpsample.pull.layoutmanager.MyLinearLayoutManager;
import com.wuxiaolong.androidmvpsample.ui.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/16.
 */

public abstract class MvpBaseListFragment <P extends BasePresenter,T> extends BaseFragment implements PullRecycler.OnRecyclerRefreshListener{
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullRecycler recycler;
    protected P mvpPresenter;
    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
        recycler = (PullRecycler) view.findViewById(R.id.pullRecycler);
        setUpAdapter();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
        recycler.setRefreshing();
    }

    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getContext(), R.drawable.list_divider);
    }

    @Override
    public void onRefresh(int action) {

    }

    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return MvpBaseListFragment.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }

    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);
}
