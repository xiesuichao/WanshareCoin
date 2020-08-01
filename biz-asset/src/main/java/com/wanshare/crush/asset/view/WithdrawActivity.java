package com.wanshare.crush.asset.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wanshare.common.base.BaseActivity;

import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.common.biz.account.model.SecondVerifyEvent;
import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.common.widget.text.SimpleTextWatcher;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.contract.WithdrawContract;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.model.bean.GetWithdrawTotalBean;
import com.wanshare.crush.asset.model.bean.WithdrawReqBean;
import com.wanshare.crush.asset.presenter.WithdrawPresenter;
import com.wanshare.crush.asset.view.dialog.ConfirmWithdrawDialog;
import com.wanshare.crush.asset.view.dialog.VerifyTradePasswordDialog;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.ArithUtil;
import com.wanshare.wscomponent.utils.KeyBoardUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提币
 * </br>
 * Date: 2018/8/24 17:43
 *
 * @author hemin
 */
@Route(path = AssetArouterConstant.ASSET_WITHDRAW_COIN)
public class WithdrawActivity extends BaseActivity<WithdrawContract.Presenter> implements WithdrawContract.View {

    @BindView(R2.id.tv_short_name)
    TextView mTvShortName;
    @BindView(R2.id.tv_fullname)
    TextView mTvFullname;
    @BindView(R2.id.tv_current_available)
    TextView mTvCurrentAvailable;
    @BindView(R2.id.tv_current_frozen)
    TextView mTvCurrentFrozen;
    @BindView(R2.id.et_address)
    ClearAbleEditText mEtAddress;
    @BindView(R2.id.iv_wallet_address)
    ImageView mIvWalletAddress;
    @BindView(R2.id.iv_scan)
    ImageView mIvScan;
    @BindView(R2.id.cb_is_remember_address)
    CheckBox mCbIsRememberAddress;
    @BindView(R2.id.tv_amount_error_tip)
    TextView mTvAmountErrorTip;
    @BindView(R2.id.et_withdraw_amount)
    ClearAbleEditText mEtWithdrawAmount;
    @BindView(R2.id.tv_withdraw_all)
    TextView mTvWithdrawAll;
    @BindView(R2.id.tv_daily_used)
    TextView mTvDailyUsed;
    @BindView(R2.id.tv_daily_all)
    TextView mTvDailyAll;
    @BindView(R2.id.tv_daily_currency)
    TextView mTvDailyCurrency;
    @BindView(R2.id.tv_fee_ratio)
    TextView mTvFeeRatio;
    @BindView(R2.id.tv_fee_currency)
    TextView mTvFeeCurrency;
    @BindView(R2.id.tv_arrival)
    TextView mTvArrival;
    @BindView(R2.id.tv_arrival_currency)
    TextView mTvArrivalCurrency;
    @BindView(R2.id.tv_explain)
    TextView mTvExplain;

    @BindView(R2.id.et_mark)
    ClearAbleEditText mEtAddressMark;

    private CoinInfo mCoinInfo;
    /**
     * 当日已提币
     */
    private String dailyAmount = "0";
    AssetByCoinBean assetBean;

    @BindString(R2.string.asset_amount_input_tip)
    String inputAmountTip;
    @BindString(R2.string.asset_number_format_error)
    String numberFormatError;
    @BindString(R2.string.asset_amount_more_than_tip)
    String moreAmountTip;
    @BindString(R2.string.asset_amount_less_than_tip)
    String lessAmountTip;
    @BindString(R2.string.asset_over_daily_amount)
    String overDailyAmount = "0";
    @BindString(R2.string.asset_more_than_balance)
    String moreThanBalance = "";

    private TextWatcher mTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {
            checkInput();
        }
    };

    private TextWatcher mAmountTextWatcher = new SimpleTextWatcher() {

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().startsWith(".")) {
                mTvAmountErrorTip.setVisibility(View.VISIBLE);
                mTvAmountErrorTip.setText(numberFormatError);
                mMyToolbar.setRightButtonTextEnable(false);
                return;
            }

            if (TextUtils.isEmpty(editable.toString())) {
                mTvAmountErrorTip.setVisibility(View.VISIBLE);
                mTvAmountErrorTip.setText(inputAmountTip);
            } else if (mCoinInfo != null) {
                double edit = Double.valueOf(editable.toString());
                // 超过最大提币限额
                if (edit > new BigDecimal(mCoinInfo.getMaxWithdraw()).doubleValue()) {
                    mTvAmountErrorTip.setVisibility(View.VISIBLE);
                    mTvAmountErrorTip.setText(moreAmountTip);
                    // 不足最小提币限额
                } else if (edit < new BigDecimal(mCoinInfo.getMinWithdraw()).doubleValue()) {
                    mTvAmountErrorTip.setVisibility(View.VISIBLE);
                    mTvAmountErrorTip.setText(lessAmountTip);
                    // 超过当日最大提币限额
                } else if (edit > new BigDecimal(mCoinInfo.getMaxWithdrawOneDay()).doubleValue() - Double.valueOf(dailyAmount)) {
                    mTvAmountErrorTip.setVisibility(View.VISIBLE);
                    mTvAmountErrorTip.setText(overDailyAmount);
                    // 余额不足
                } else if (assetBean != null && new BigDecimal(assetBean.getBalance()).doubleValue() < edit) {
                    mTvAmountErrorTip.setVisibility(View.VISIBLE);
                    mTvAmountErrorTip.setText(moreThanBalance);
                } else {
                    mTvAmountErrorTip.setVisibility(View.GONE);
                }
            }
            setFee(editable.toString());
            checkInput();
        }
    };
    private ConfirmWithdrawDialog mConfirmDialog;
    private VerifyTradePasswordDialog mVerifyTradePasswordDialog;
    private ClearAbleEditText.OnFocusChangeCustomListener mFocusChangeListener = new ClearAbleEditText.OnFocusChangeCustomListener() {
        @Override
        public void focusChange(boolean hasFocus) {
            if (!hasFocus) {
                KeyBoardUtils.closeKeybord(getActivity());
            }
        }
    };

    @Override
    protected WithdrawContract.Presenter getPresenter() {
        return new WithdrawPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_withdraw;
    }

    @Override
    protected void initIntent() {
        mCoinInfo = (CoinInfo) getIntent().getSerializableExtra(IntentConstant.EXTRA_COIN_INFO);
        if (mCoinInfo == null) {
            LogUtil.d("RechargeActivity mCoinInfo is null");
            finish();
        }
    }

    @Override
    protected void initView() {
        initEvent();
        initToolbar();

        mTvShortName.setText(mCoinInfo.getShortName());
        mTvFullname.setText("(" + mCoinInfo.getFullName() + ")");

        mTvDailyUsed.setText("0/");

        mCbIsRememberAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mEtAddressMark.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                checkInput();
            }
        });
        mEtAddress.addTextChangedListener(mTextWatcher);
        mEtWithdrawAmount.addTextChangedListener(mAmountTextWatcher);
        mEtAddressMark.addTextChangedListener(mTextWatcher);


        mEtAddress.setOnFocusChangeCustomListener(mFocusChangeListener);
        mEtWithdrawAmount.setOnFocusChangeCustomListener(mFocusChangeListener);
        mEtAddressMark.setOnFocusChangeCustomListener(mFocusChangeListener);
    }

    @Override
    protected void initData() {
        mPresenter.getAssetsInfo(mCoinInfo.getId());
        mPresenter.getWithdrawTotalToday(mCoinInfo.getId());
        mPresenter.getCoinInfo(mCoinInfo.getId());
    }

    private void checkInput() {
        mMyToolbar.setRightButtonTextColor(ContextCompat.getColor(getContext(), checkCommitEnable() ? R.color.color_main_light : R.color.color_gray_dark2));
        mMyToolbar.setRightButtonTextEnable(checkCommitEnable());
    }

    private void initToolbar() {
        mMyToolbar.setTitle(R.string.asset_withdraw);
        mMyToolbar.setRightButtonText(R.string.asset_confirm);
        mMyToolbar.setOnRightButtonTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybord(getActivity());
                showConfirmDialog();
            }
        });
        mMyToolbar.setRightButtonTextEnable(false);
    }

    private void showConfirmDialog() {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmWithdrawDialog(getActivity());
            mConfirmDialog.setOnConfirmClickListener(new ConfirmWithdrawDialog.OnConfirmClickListener() {
                @Override
                public void onConfirmClick() {
                    showVerifyPasswordDialog();
                }
            });
        }

        mConfirmDialog.setCoin(mCoinInfo.getShortName());
        mConfirmDialog.setAddress(mEtAddress.getText());
        mConfirmDialog.setAmount(mEtWithdrawAmount.getText());
        mConfirmDialog.show();
    }

    private void showVerifyPasswordDialog() {
        if (mVerifyTradePasswordDialog == null) {
            mVerifyTradePasswordDialog = new VerifyTradePasswordDialog(getActivity());
            mVerifyTradePasswordDialog.setOnConfirmClickListener(new VerifyTradePasswordDialog.OnConfirmClickListener() {
                @Override
                public void onConfirmClick(String password) {
                    doWithdraw(password, "");
                    mVerifyTradePasswordDialog.dismiss();
                }
            });

        }
        mVerifyTradePasswordDialog.clearPassword();

        mVerifyTradePasswordDialog.show();
    }

    private void doWithdraw(String password, String token) {
        WithdrawReqBean reqBean = new WithdrawReqBean();
        reqBean.setCoinId(mCoinInfo.getId());
        reqBean.setAmount(mEtWithdrawAmount.getText());
        reqBean.setAddress(mEtAddress.getText().toString());
        reqBean.setIsSaveAddress(mCbIsRememberAddress.isChecked());
        if (!TextUtils.isEmpty(token)) {
            reqBean.setToken(token);
        }
        if (mCbIsRememberAddress.isChecked()) {
            reqBean.setAddressTag(mEtAddressMark.getText());
        }
        reqBean.setTraPassword(password);
        mPresenter.doWithdraw(reqBean);
    }

    @OnClick({R2.id.tv_withdraw_all, R2.id.iv_wallet_address, R2.id.iv_scan})
    public void onViewClicked(View v) {
        int id = v.getId();
        if (id == R.id.tv_withdraw_all) {
            if (mCoinInfo == null) {
                return;
            }
            try {
                BigDecimal withdrawAll = new BigDecimal("0");

                BigDecimal currentAvailable = new BigDecimal(mTvCurrentAvailable.getText().toString().trim());
                BigDecimal withdrawMax = new BigDecimal(mCoinInfo.getMaxWithdraw());
                if (currentAvailable.doubleValue() > withdrawMax.doubleValue()) {
                    withdrawAll = withdrawMax;
                } else {
                    withdrawAll = currentAvailable;
                }
                BigDecimal withdrawTodayBalance = new BigDecimal(mCoinInfo.getMaxWithdrawOneDay()).subtract(new BigDecimal(dailyAmount));
                if (withdrawAll.doubleValue() > withdrawTodayBalance.doubleValue()) {
                    withdrawAll = withdrawTodayBalance;
                }
                mEtWithdrawAmount.setText(ArithUtil.round(withdrawAll.toPlainString(), 8));
                mEtWithdrawAmount.setSelection(mEtWithdrawAmount.getText().length());
            } catch (Exception ex) {
                LogUtil.ex(ex);
            }
        } else if (id == R.id.iv_wallet_address) {
            ARouter.getInstance().build(AssetArouterConstant.WITHDRAW_SELECT_ADDRESS)
                    .withString(IntentConstant.EXTRA_CURRENCY, mCoinInfo.getId())
                    .navigation(this, WithdrawSelectAddressActivity.REQUEST_SELECT_ADDRESS);
        } else if (id == R.id.iv_scan) {
            ARouter.getInstance().build(AssetArouterConstant.SCAN).navigation(this, ScanActivity.REQUEST_SCAN);
        }
    }

    @Override
    public void showAssetInfo(AssetByCoinBean result) {
        if (result == null) {
            return;
        }
        assetBean = result;
        mTvCurrentAvailable.setText(result.getBalance());
        mTvCurrentFrozen.setText(result.getFrozen());
    }

    @Override
    public void showWithdrawTotalToday(GetWithdrawTotalBean result) {
        dailyAmount = result.getTotal();
        if (TextUtils.isEmpty(dailyAmount)) {
            dailyAmount = "0";
        }
        mTvDailyUsed.setText(dailyAmount + "/");
    }

    @Override
    public void onWithdrawSuccess() {
        LogUtil.d("提币成功");
        showShortToast(getResources().getString(R.string.asset_withdraw_success));
        setResult(RESULT_OK);
        KeyBoardUtils.closeKeybord(getActivity());
        finish();
    }

    @Override
    public void showCoinInfo(CoinInfo coinInfo) {
        this.mCoinInfo = coinInfo;
        mTvDailyAll.setText(coinInfo.getMaxWithdrawOneDay());
        mTvDailyCurrency.setText(coinInfo.getShortName());
        mTvExplain.setText(getString(R.string.asset_withdraw_explain_body, coinInfo.getMinWithdraw() + " " + coinInfo.getShortName(), coinInfo.getMaxWithdraw() + " " + coinInfo.getShortName()));

        mEtWithdrawAmount.setHint(getString(R.string.asset_min_withdraw_amount, coinInfo.getMinWithdraw() + " " + coinInfo.getShortName()));
    }

    @Override
    public void onSecondVerify(SecondVerifyParams secondVerifyParams) {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SECOND_VERIFY).withParcelable(IntentConstant.EXTRA_SECOND_VERIFY_PARAMS, secondVerifyParams).navigation(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SecondVerifyEvent event) {
        if (VerifyTypeConstants.WITHDRAW.equals(event.getVerifyType())) {
            JsonObject response = event.getResponse();
            LogUtil.d("SecondVerifyEvent response:" + response);
            String key = ApiParamConstants.KEY_SECOND_TOKEN;
            if (response != null && response.has(key)) {
                String token = response.get(key).getAsString();
                if (!TextUtils.isEmpty(token) && mVerifyTradePasswordDialog != null
                        && !TextUtils.isEmpty(mVerifyTradePasswordDialog.getPassword())) {
                    doWithdraw(mVerifyTradePasswordDialog.getPassword(), token);
                }
            }
        }
    }

    private boolean checkCommitEnable() {
        if (TextUtils.isEmpty(mEtAddress.getText())) {
            return false;
        }

        if (mCbIsRememberAddress.isChecked()) {
            if (TextUtils.isEmpty(mEtAddressMark.getText())) {
                return false;
            }
        }

        if (TextUtils.isEmpty(mEtWithdrawAmount.getText())) {
            return false;
        }

        if (mTvAmountErrorTip.getVisibility() == View.VISIBLE) {
            return false;
        }
        return true;
    }

    private void setFee(String inputAmount) {
        BigDecimal input = new BigDecimal(TextUtils.isEmpty(inputAmount) ? "0" : inputAmount.toString());
        BigDecimal fee = mCoinInfo == null ? new BigDecimal("0") : new BigDecimal(ArithUtil.mul(mCoinInfo.getWithdrawFee(), input.toPlainString()));
        BigDecimal minFee = mCoinInfo == null ? new BigDecimal("0") : new BigDecimal(mCoinInfo.getMinFee());
        BigDecimal realFee = fee.doubleValue() > minFee.doubleValue() ? fee : minFee;
        mTvFeeRatio.setText(ArithUtil.round(realFee.toPlainString(), 8));
        String showArrival = ArithUtil.round(input.doubleValue() > realFee.doubleValue() ? input.subtract(realFee).toPlainString() : "0", 8);
        mTvArrival.setText(showArrival);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ScanActivity.REQUEST_SCAN:
                    updateAddress(data, CodeUtils.RESULT_STRING);
                    break;
                case WithdrawSelectAddressActivity.REQUEST_SELECT_ADDRESS:
                    updateAddress(data, IntentConstant.EXTRA_ADDRESS);
                    break;
            }
        }
    }

    private void updateAddress(Intent data, String extra) {
        String selectedAddress = data.getStringExtra(extra);
        if (selectedAddress == null) {
            selectedAddress = "";
        }
        mEtAddress.setText(selectedAddress);
    }

    @Override
    protected void onDestroy() {
        if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
            mConfirmDialog.dismiss();
        }

        if (mVerifyTradePasswordDialog != null && mVerifyTradePasswordDialog.isShowing()) {
            mVerifyTradePasswordDialog.dismiss();
        }
        super.onDestroy();
    }
}
