package com.wuxiaolong.androidmvpsample.mvp.simple_activity;

import com.wuxiaolong.androidmvpsample.mvp.other.BasePresenter;
import com.wuxiaolong.androidmvpsample.retrofit.ApiCallback;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/15.
 */

public class SimplePresenter extends BasePresenter<SimpleView> {
    public SimplePresenter(SimpleView view) {
        attachView(view);
    }
    public void loadDataByRetrofitRxjava(int page, final int action) {
        addSubscription(apiStores.rxBenefits(20, page),
                new ApiCallback<BaseModel<ArrayList<Benefit>>>() {
                    @Override
                    public void onSuccess(BaseModel<ArrayList<Benefit>> model) {
                        mvpView.getDataSuccess(model,action);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }


                    @Override
                    public void onFinish() {
                        //mvpView.hideLoading();
                    }

                });
        ;
    }
}
