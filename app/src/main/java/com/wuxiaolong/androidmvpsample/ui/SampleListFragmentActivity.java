package com.wuxiaolong.androidmvpsample.ui;

import android.os.Bundle;

import com.wuxiaolong.androidmvpsample.R;

public class SampleListFragmentActivity extends BaseActivity  {
    private SampleListFragment mSampleListFragment;
    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.fragment);
    }
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_sample_list_fragment, -1);
        mSampleListFragment = new SampleListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mSampleListFragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_list_fragment);
    }
}
