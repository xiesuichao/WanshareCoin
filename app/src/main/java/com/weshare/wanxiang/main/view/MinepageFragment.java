package com.weshare.wanxiang.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.biz.setting.SettingArouterConstant;
import com.wanshare.common.biz.setting.SettingInfoManager;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.asset.model.bean.UserAsserts;
import com.wanshare.wscomponent.utils.DeviceUtil;
import com.weshare.wanxiang.R;
import com.weshare.wanxiang.main.contract.MinepageContract;
import com.weshare.wanxiang.main.presenter.MinepagePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yangwenwu on 2018/8/29.
 */

public class MinepageFragment extends BaseFragment<MinepageContract.Presenter> implements MinepageContract.View {

    @BindView(R.id.fl_app_fragment_minepage_please_sign_in)
    FrameLayout mFlPleaseSignIn;
    @BindView(R.id.ll_root_container)
    LinearLayout mLlRootContainer;
    @BindView(R.id.ll_app_mine_page)
    LinearLayout mLlAppMinepage;
    @BindView(R.id.iv_app_fragment_minepage_mode)
    ImageView mIvAppMode;
    @BindView(R.id.tv_app_minepage_asset_total)
    TextView mAssetTotal;
    @BindView(R.id.tv_app_minepage_asset_rmb)
    TextView mAssetRmb;
    @BindView(R.id.tv_app_fragment_minepage_account)
    TextView mAccount;
    @BindView(R.id.tv_app_fragment_minepage_id)
    TextView mUid;


    public static MinepageFragment newInstance() {

        Bundle args = new Bundle();

        MinepageFragment fragment = new MinepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MinepageContract.Presenter getPresenter() {
        return new MinepagePresenter(this);
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_minepage;
    }

    @Override
    protected void initView() {
        mLlRootContainer.setPadding(mLlRootContainer.getPaddingLeft(), DeviceUtil.getStatusBarHeight(mActivity), mLlRootContainer.getPaddingRight(), mLlRootContainer.getPaddingBottom());

        if (SettingInfoManager.isNightMode()) {
            mIvAppMode.setBackgroundResource(R.drawable.ic_center_hy);
        } else {
            mIvAppMode.setBackgroundResource(R.drawable.ic_center_bt);
        }

        if (!UserInfoManager.getInstance().isLogin()) {
            mFlPleaseSignIn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {

        if (UserInfoManager.getInstance().isLogin()) {
            mAssetTotal.setText("0");
            mAssetRmb.setText("≈ ¥ 0");
            if (mPresenter != null) {
                mPresenter.getUserInfo();
                mPresenter.getUserAsserts(0, 0, "", "", false, "");
            }
        } else {
            mAssetTotal.setText("******");
            mAssetRmb.setText("*******");
        }
    }

    @Override
    public void showUserAsserts(UserAsserts userAsserts) {

        mAssetTotal.setText(userAsserts.getEstimates().getUsdt() + "");
        String strCny = String.format("≈ ¥ %s", userAsserts.getEstimates().getCny() + "");
        mAssetRmb.setText(strCny);

    }

    @Override
    public void showUserInfo() {

        if (!UserInfoManager.getInstance().isLogin()) {
            mFlPleaseSignIn.setVisibility(View.VISIBLE);
            mAssetTotal.setText("******");
            mAssetRmb.setText("*******");
        } else {
            mFlPleaseSignIn.setVisibility(View.GONE);
            mAssetTotal.setText("0");
            mAssetRmb.setText("≈ ¥ 0");

            if (TextUtils.isEmpty(UserInfoManager.getInstance().getAccountId())) {
                mUid.setText("");
            } else {
                String uid = String.format(getResources().getString(R.string.app_mine_uid), UserInfoManager.getInstance().getAccountId());
                mUid.setText(uid);
            }

            mAccount.setText(getEmail(UserInfoManager.getInstance().getEmail()));
        }
    }

    @OnClick({R.id.fl_app_fragment_minepage_please_sign_in, R.id.iv_app_fragment_minepage_mode, R.id.iv_app_fragment_minepage_msg, R.id.ll_app_mine_page, R.id.tv_minepage_asset_charge_coin, R.id.tv_minepage_asset_mention_coin, R.id.ll_app_mine_hand_fee, R.id.ll_app_mine_authentication,
            R.id.ll_app_mine_safety, R.id.ll_app_mine_coin_address, R.id.ll_app_mine_help, R.id.ll_app_mine_set})
    public void setOnClick(View v) {
        switch (v.getId()) {
            case R.id.fl_app_fragment_minepage_please_sign_in:
                if (!UserInfoManager.getInstance().isLogin()) {
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(mActivity);
                }
                break;
            case R.id.iv_app_fragment_minepage_mode:
                if (SettingInfoManager.isNightMode()) {
                    mIvAppMode.setBackgroundResource(R.drawable.ic_center_hy);
                } else {
                    mIvAppMode.setBackgroundResource(R.drawable.ic_center_bt);
                }
                SettingInfoManager.changeNightMode();
                getHoldingActivity().recreate();
                break;
            case R.id.iv_app_fragment_minepage_msg:
                ARouter.getInstance().build(SettingArouterConstant.SETTING_MESSAGES).navigation(mActivity);
                break;
            case R.id.ll_app_mine_page:
                if (!UserInfoManager.getInstance().isLogin()) {
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(mActivity);
                    return;
                }
                ARouter.getInstance().build(AssetArouterConstant.ASSET_TOTAL_ASSETS).navigation(mActivity);
                break;
            case R.id.tv_minepage_asset_charge_coin:
                if (!UserInfoManager.getInstance().isLogin()) {
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(mActivity);
                    return;
                }
                ARouter.getInstance().build(AssetArouterConstant.ASSET_RECHARGE_SELECT_COIN).navigation(mActivity);
                break;
            case R.id.tv_minepage_asset_mention_coin:
                if (!UserInfoManager.getInstance().isLogin()) {
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(mActivity);
                    return;
                }
                ARouter.getInstance().build(AssetArouterConstant.ASSET_WITHDRAW_SELECT_COIN).navigation(mActivity);
                break;
            case R.id.ll_app_mine_hand_fee:
                break;
            case R.id.ll_app_mine_authentication:
                if (!UserInfoManager.getInstance().isLogin()) {
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(mActivity);
                    return;
                }
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_AUTHOR_INFO).navigation(mActivity);
                break;
            case R.id.ll_app_mine_safety:
                if (!UserInfoManager.getInstance().isLogin()) {
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(mActivity);
                    return;
                }
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SECURITY).navigation(mActivity);
                break;
            case R.id.ll_app_mine_coin_address:
                if (!UserInfoManager.getInstance().isLogin()) {
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(mActivity);
                    return;
                }
                ARouter.getInstance().build(AssetArouterConstant.WITHDRAW_ADDRESS_LIST).navigation(mActivity);
                break;
            case R.id.ll_app_mine_help:
                ARouter.getInstance().build(SettingArouterConstant.SETTING_HELP).navigation(mActivity);
                break;
            case R.id.ll_app_mine_set:
                ARouter.getInstance().build(SettingArouterConstant.SETTING_MAIN).navigation(mActivity);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPresenter != null) {
            mPresenter.getUserInfo();
            mPresenter.getUserAsserts(0, 0, "", "", false, "");

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (UserInfoManager.getInstance().isLogin()) {
                if (mPresenter != null) {
                    mPresenter.getUserInfo();
                    mPresenter.getUserAsserts(0, 0, "", "", false, "");
                }
            }else {
                mFlPleaseSignIn.setVisibility(View.VISIBLE);
                mAssetTotal.setText("******");
                mAssetRmb.setText("*******");
            }
        }
    }

    private String getEmail(String email) {
        if (TextUtils.isEmpty(email))
            return "";

        int eLength = email.lastIndexOf("@");

        if (eLength <= 3)
            return email;
        else
            return email.substring(0, 3) + "...." + email.substring(email.indexOf("@"), email.length());
    }

}
