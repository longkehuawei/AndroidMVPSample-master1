package com.wuxiaolong.androidmvpsample.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.mvp.main.MainModel;
import com.wuxiaolong.androidmvpsample.mvp.main.MainPresenter;
import com.wuxiaolong.androidmvpsample.mvp.main.MainView;
import com.wuxiaolong.androidmvpsample.mvp.other.MvpActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 由Activity/Fragment实现View里方法，包含一个Presenter的引用
 * Created by WuXiaolong
 * on 2015/9/23.
 * github:https://github.com/WuXiaolong/
 * weibo:http://weibo.com/u/2175011601
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @Bind(R.id.text)
    TextView text;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main, -1);
    }
    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.mvp);
    }



    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void getDataSuccess(MainModel model) {
        //接口成功回调
        dataSuccess(model);
    }

    @Override
    public void getDataFail(String msg) {
        //toastShow("网络不给力");

    }


    @OnClick({R.id.button0, R.id.button1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
                startActivity(new Intent(MainActivity.this,SimpleListActivity.class));
                //loadDataByRetrofit();
                break;
            case R.id.button1:
                startActivity(new Intent(MainActivity.this,SampleListFragmentActivity.class));
                //loadDataByRetrofitRxjava();
                break;

        }
    }



    private void dataSuccess(MainModel model) {
        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                + getResources().getString(R.string.time) + weatherinfo.getTime();
        text.setText(showData);
    }
}
