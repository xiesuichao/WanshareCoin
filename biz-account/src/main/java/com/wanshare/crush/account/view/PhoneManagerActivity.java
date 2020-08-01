package com.wanshare.crush.account.view;

import android.content.Intent;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.contract.PhoneManagerContract;
import com.wanshare.crush.account.model.bean.event.UserInfoEvent;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.PhoneManagerPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 手机开启界面
 */
@Route(path = AccountArouterConstant.ACCOUNT_PHONE_MANAGER)
public class PhoneManagerActivity extends BaseActivity<PhoneManagerContract.Presenter> implements PhoneManagerContract.View {

    @BindView(R2.id.tv_phone_manager_phone)
    TextView mTvPhoneManagerPhone;
    @BindView(R2.id.sw_phone_manager_set)
    Switch mSwPhoneManagerSet;

    private boolean isOpen;

    @Override
    protected PhoneManagerContract.Presenter getPresenter() {
        return new PhoneManagerPresenter(this);
    }

    @Override
    protected void initData() {
        AccountInfoBean.AccountInfo.PhoneNumberBean bean = AccountCacheManager.getInstance().getPhoneNumberBean();
        if (bean != null) {
            mTvPhoneManagerPhone.setText(bean.getPhone());
        }
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_phone_manager);
        isOpen();
    }

    private void isOpen() {
        isOpen = AccountCacheManager.getInstance().getIsPhoneAuthEnable();
        mSwPhoneManagerSet.setChecked(isOpen);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_phone_manager;
    }


    @Override
    public void resultSetBindStatus(Object object) {

    }

    @OnClick(R2.id.btn_phone_manager_set)
    public void onViewClicked() {
        if (isOpen) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_PHONE_CLOSE).navigation(this, 1);
        }else{
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_PHONE_START).navigation(this, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            AccountCacheManager.getInstance().putIsPhoneAuthEnable(!isOpen);
            EventBus.getDefault().post(new UserInfoEvent());
            isOpen();
        }
    }
}
