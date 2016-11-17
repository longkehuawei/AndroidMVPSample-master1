package com.wuxiaolong.androidmvpsample.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.mvp.other.MvpBaseListActivity;
import com.wuxiaolong.androidmvpsample.mvp.simple_activity.BaseModel;
import com.wuxiaolong.androidmvpsample.mvp.simple_activity.Benefit;
import com.wuxiaolong.androidmvpsample.mvp.simple_activity.SimplePresenter;
import com.wuxiaolong.androidmvpsample.mvp.simple_activity.SimpleView;
import com.wuxiaolong.androidmvpsample.pull.BaseViewHolder;
import com.wuxiaolong.androidmvpsample.pull.PullRecycler;
import com.wuxiaolong.androidmvpsample.pull.layoutmanager.ILayoutManager;
import com.wuxiaolong.androidmvpsample.pull.layoutmanager.MyGridLayoutManager;
import com.wuxiaolong.androidmvpsample.pull.layoutmanager.MyLinearLayoutManager;
import com.wuxiaolong.androidmvpsample.pull.layoutmanager.MyStaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2016/11/15.
 */

public class SimpleListActivity extends MvpBaseListActivity<SimplePresenter, Benefit> implements SimpleView {
    private int page = 1;
    private int random;


    @Override
    public void getDataSuccess(BaseModel<ArrayList<Benefit>> model, int action) {
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            mDataList.clear();
        }
        if (model.results == null || model.results.size() == 0) {
            recycler.enableLoadMore(false);
        } else {
            recycler.enableLoadMore(true);
            mDataList.addAll(model.results);
            adapter.notifyDataSetChanged();
        }
        recycler.onRefreshCompleted();
    }

    @Override
    public void getDataFail(String msg) {
        //Toast.makeText(mActivity,msg, Toast.LENGTH_LONG).show();
        recycler.onRefreshCompleted();

    }

    @Override
    protected SimplePresenter createPresenter() {
        return new SimplePresenter(SimpleListActivity.this);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onRefresh(int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }
        mvpPresenter.loadDataByRetrofitRxjava(page++, action);

    }

    @Override
    protected ILayoutManager getLayoutManager() {
        random = new Random().nextInt(3);
        switch (random) {
            case 0:
                return new MyLinearLayoutManager(getApplicationContext());
            case 1:
                return new MyGridLayoutManager(getApplicationContext(), 3);
            case 2:
                return new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        return super.getLayoutManager();
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        if (random == 0) {
            return super.getItemDecoration();
        } else {
            return null;
        }
    }


    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.meinvtu);
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView mSampleListItemImg;
        TextView mSampleListItemLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
            mSampleListItemImg = (ImageView) itemView.findViewById(R.id.mSampleListItemImg);
        }

        @Override
        public void onBindViewHolder(int position) {
            mSampleListItemLabel.setVisibility(View.GONE);
            Glide.with(mSampleListItemImg.getContext())
                    .load(mDataList.get(position).url)
                    .centerCrop()
                    .placeholder(R.color.app_primary_color)
                    .crossFade()
                    .into(mSampleListItemImg);
        }

        @Override
        public void onItemClick(View view, int position) {
           /* Intent intent=new Intent(SimpleListActivity.this,PhotoViewFragmentActivity.class);
            intent.putExtra(PhotoViewFragmentActivity.EXTRA_CURRENT_ITEM,position);
            ArrayList<String> urls=new ArrayList<>();
            for(int i=0;i<mDataList.size();i++){
                urls.add(mDataList.get(i).url);
            }
            intent.putStringArrayListExtra(PhotoViewFragmentActivity.EXTRA_PHOTOS,urls);
            startActivity(intent);*/
        }

    }
}
