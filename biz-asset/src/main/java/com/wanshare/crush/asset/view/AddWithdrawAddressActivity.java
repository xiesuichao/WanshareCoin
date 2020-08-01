package com.wanshare.crush.asset.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.widget.NumberPickerView;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.contract.AddWithdrawAddressContract;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.presenter.AddWithdrawAddressPresenter;
import com.wanshare.crush.asset.view.dialog.SelectCoinDialog;
import com.wanshare.wscomponent.utils.ToastUtil;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * 添加提现地址页面
 *
 * @author wangdunwei
 */
@Route(path = AssetArouterConstant.WITHDRAW_ADDRESS_ADD)
public class AddWithdrawAddressActivity extends BaseActivity<AddWithdrawAddressContract.Presenter>
        implements AddWithdrawAddressContract.View {
    public static final String EXTRA_COIN_NAME = "extra_coin_name";

    @BindView(R2.id.et_title)
    EditText etTitle;
    @BindView(R2.id.cb_select_address)
    CheckBox cbSelectAddress;
    @BindView(R2.id.et_coin_address)
    EditText etCoinAddress;
    @BindView(R2.id.iv_scan)
    ImageView ivScan;

    @BindColor(R2.color.color_gray_dark2)
    int blackTextColor;

    private List<CoinInfo> coinInfos;
    private String[] availableCoinArray;

    private CoinInfo currentCoinInfo;

    @Override
    protected AddWithdrawAddressContract.Presenter getPresenter() {
        return new AddWithdrawAddressPresenter(this);
    }

    @Override
    protected void initData() {
        mPresenter.requestAvailableWithdrawCoinList();
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.asset_add_withdraw_address);
        mMyToolbar.setRightButtonText(R.string.finish);

        cbSelectAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(coinInfos == null || coinInfos.isEmpty()) {
                    ToastUtil.showShort(getContext(), R.string.asset_not_get_available_withdraw_list);
                    mPresenter.requestAvailableWithdrawCoinList();
                    cbSelectAddress.setChecked(false);
                    return;
                }

                if(isChecked) {
                    final SelectCoinDialog selectCoinDialog = new SelectCoinDialog(getActivity(),
                            availableCoinArray);
                    selectCoinDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            cbSelectAddress.setChecked(false);
                        }
                    });
                    selectCoinDialog.show();
                    selectCoinDialog.setOnValueChangeRelativeToRaw(
                            new NumberPickerView.OnValueChangeListenerRelativeToRaw() {
                                @Override
                                public void onValueChangeRelativeToRaw(NumberPickerView picker, int oldPickedIndex,
                                                                       int newPickedIndex,
                                                                       String[] displayedValues) {
                                    selectCurrentCoinInfo(newPickedIndex);
                                }
                            });
                    selectCurrentCoinInfo(0);
                }
            }
        });

        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                       .build(AssetArouterConstant.SCAN)
                       .navigation(getActivity(), ScanActivity.REQUEST_SCAN);
            }
        });

    }

    @Override
    protected void onRightButton(View view) {
        super.onRightButton(view);
        if(etTitle.getText().length() <= 0 || etCoinAddress.getText().length() <= 0 ||
                cbSelectAddress.getText().toString().equals(getString(R.string.asset_please_select_coin))) {
            ToastUtil.showShort(getContext(), R.string.asset_please_input_withdraw_coin_info);
            return;
        }

        mPresenter.addWithdrawAddress(currentCoinInfo.getId(), etTitle.getText().toString(),
                etCoinAddress.getText().toString());
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_add_withdraw_address;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //处理扫描结果（在界面上显示）
        if(requestCode == ScanActivity.REQUEST_SCAN && null != data && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            if(bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                etCoinAddress.setText(result);
                etCoinAddress.requestFocus();
                etCoinAddress.setSelection(etCoinAddress.getText().length());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void alterSucceed() {
        ToastUtil.showShort(this, R.string.asset_alter_withdraw_address_succeed);
        performFinish();
    }

    @Override
    public void addSucceed() {
        ToastUtil.showShort(this, R.string.asset_add_withdraw_address_succeed);
        performFinish();
    }

    @Override
    public void onGotCoinNames(List<CoinInfo> coinInfoList, List<String> strings) {
        coinInfos = coinInfoList;
        availableCoinArray = strings.toArray(new String[0]);
    }

    private void selectCurrentCoinInfo(int index) {
        currentCoinInfo = coinInfos.get(index);
        cbSelectAddress.setTextColor(blackTextColor);
        cbSelectAddress.setText(currentCoinInfo.getShortName());
    }

    private void performFinish() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
