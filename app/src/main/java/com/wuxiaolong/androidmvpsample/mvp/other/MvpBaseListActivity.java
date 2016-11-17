package com.wuxiaolong.androidmvpsample.mvp.other;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.pull.BaseListAdapter;
import com.wuxiaolong.androidmvpsample.pull.BaseViewHolder;
import com.wuxiaolong.androidmvpsample.pull.DividerItemDecoration;
import com.wuxiaolong.androidmvpsample.pull.PullRecycler;
import com.wuxiaolong.androidmvpsample.pull.layoutmanager.ILayoutManager;
import com.wuxiaolong.androidmvpsample.pull.layoutmanager.MyLinearLayoutManager;
import com.wuxiaolong.androidmvpsample.ui.BaseActivity;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/11/15.
 */

public abstract class MvpBaseListActivity <P extends BasePresenter,T> extends BaseActivity implements Toolbar.OnMenuItemClickListener,PullRecycler.OnRecyclerRefreshListener{
    protected P mvpPresenter;
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullRecycler recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        setUpView();
        setUpData();

    }
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_base_list, -1);
    }

    protected void setUpView() {
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
    protected void setUpData() {
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
        return new MyLinearLayoutManager(getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
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
            return MvpBaseListActivity.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }
    protected int getItemType(int position) {
        return 0;
    }
    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }
}
