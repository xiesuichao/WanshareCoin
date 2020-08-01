package com.wanshare.crush.exchange;

import com.wanshare.common.base.BaseActivity;
import com.wanshare.crush.exchange.view.fragment.ExchangeFragment;

public class ExchangeFragmnetActivity extends BaseActivity{

    @Override
    protected int getContentView() {
        return R.layout.activity_exchange_fragmnet;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.fl, new ExchangeFragment())
                                   .commit();
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void initData() {

    }
}
