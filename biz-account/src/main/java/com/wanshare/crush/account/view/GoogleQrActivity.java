package com.wanshare.crush.account.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.contract.GoogleQrContract;
import com.wanshare.crush.account.model.bean.GoogleInfoEntity;
import com.wanshare.crush.account.model.bean.GoogleQrReq;
import com.wanshare.crush.account.model.bean.event.UserInfoEvent;
import com.wanshare.crush.account.presenter.GoogleQrPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 获取google验证key
 * */
@Route(path = AccountArouterConstant.ACCOUNT_GOOGLE_QR)
public class GoogleQrActivity extends BaseActivity<GoogleQrContract.Presenter> implements GoogleQrContract.View {

    @BindView(R2.id.iv_google_verify_qr)
    ImageView mIvGoogleVerifyQr;
    @BindView(R2.id.tv_google_qr_key)
    TextView mTvGoogleQrKey;

    private String mKey = "";

    private boolean isModify;

    @Override
    protected GoogleQrContract.Presenter getPresenter() {
        return new GoogleQrPresenter(this);
    }


    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            isModify = bundle.getBoolean("isModify");
        }
    }

    @Override
    protected void initData() {
        GoogleQrReq req = new GoogleQrReq();
        req.setAccount("164942747@qq.com", "");
        mPresenter.initGoogleauth(req);
    }

    @Override
    protected void initView() {
        initEvent();
        mMyToolbar.setTitle(R.string.account_google_verify_title);

        mTvGoogleQrKey.setText(getString(R.string.account_google_verify_secret_key, mKey));

    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_google_qr;
    }


    @OnClick({R2.id.btn_google_qr_previous, R2.id.btn_google_qr_next_steps, R2.id.btn_google_qr_copy})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_google_qr_previous) {
            finish();

        } else if (i == R.id.btn_google_qr_next_steps) {
            if (isModify) {
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GOOGLE_OPERATION)
                        .withInt("type", 2)
                        .navigation(this);
            }else{
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GOOGLE_START)
                        .withBoolean("isModify", isModify)
                        .withInt("type", 1)
                        .withString("key", mKey)
                        .navigation(this);
            }

        } else if (i == R.id.btn_google_qr_copy) {
            copy();
            showLongToast(getString(R.string.account_copy_success));

        }
    }

    private void copy() {
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", mKey);
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public void initGoogleauthResult(GoogleInfoEntity entity) {

        if (entity == null) {
            return;
        }
        mKey = entity.getKey();
        mTvGoogleQrKey.setText(getString(R.string.account_google_verify_secret_key, mKey));
        mIvGoogleVerifyQr.setImageBitmap(CodeUtils.createImage(entity.getUri(), 600, 600, null));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoEvent event) {
        finish();
    }
}
