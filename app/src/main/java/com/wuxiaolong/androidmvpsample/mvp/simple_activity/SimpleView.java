package com.wuxiaolong.androidmvpsample.mvp.simple_activity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/15.
 */

public interface  SimpleView {
    void getDataSuccess(BaseModel<ArrayList<Benefit>> model, int action);

    void getDataFail(String msg);
}
