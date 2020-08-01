package com.wanshare.crush.asset.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.model.bean.AssetRecord;
import com.wanshare.crush.asset.model.bean.RechargeStatus;
import com.wanshare.crush.asset.model.bean.WithdrawStatus;
import com.wanshare.wscomponent.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 财务记录详情页
 *
 * @author wangdunwei
 */
@Route(path = AssetArouterConstant.ASSET_RECORD_DETAIL)
public class AssetRecordDetailActivity extends BaseActivity {
    public static final String EXTRA_IS_RECHARGE = "is_recharge";
    public static final String EXTRA_RECORD = "extra_record";

    @BindView(R2.id.tv_coin)
    TextView tvCoin;
    @BindView(R2.id.tv_type)
    TextView tvType;
    @BindView(R2.id.tv_count)
    TextView tvCount;
    @BindView(R2.id.tv_status)
    TextView tvStatus;
    @BindView(R2.id.tv_submit_time)
    TextView tvSubmitTime;
    @BindView(R2.id.tv_address)
    TextView tvAddress;
    @BindView(R2.id.tv_txid)
    TextView tvTxid;
    @BindView(R2.id.btn_copy_txid)
    Button btnCopyTxid;
    @BindView(R2.id.tv_fee)
    TextView tvFee;
    @BindView(R2.id.ll_fee)
    LinearLayout llFee;
    @BindView(R2.id.tv_confirmation_info)
    TextView tvConfirmationInfo;
    @BindView(R2.id.tv_finish_time)
    TextView tvFinishTime;
    @BindView(R2.id.ll_submit_time)
    LinearLayout llSubmitTime;

    private AssetRecord ar;
    private boolean isRecharge;

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        isRecharge = getIntent().getBooleanExtra(EXTRA_IS_RECHARGE, true);
        mMyToolbar.setTitle(isRecharge ? R.string.asset_recharge_record_detail : R.string.asset_withdraw_record_detail);
        ar = getIntent().getParcelableExtra(EXTRA_RECORD);
        if(ar == null) {
            ToastUtil.showShort(getContext(), R.string.asset_get_asset_detail_failed);
            return;
        }

        tvCoin.setText(ar.getShortName());
        tvType.setText(isRecharge ? R.string.asset_recharge_coin : R.string.asset_withdraw_coin);
        tvCount.setText(String.valueOf(ar.getAmount()));

        String status;
        if(isRecharge) {
            status = RechargeStatus.of(ar.getStatus()).toVisibleString(getContext());
        } else {
            status = WithdrawStatus.of(ar.getStatus()).toVisibleString(getContext());
        }
        tvStatus.setText(status);

        if(status.equals(RechargeStatus.CONFIRMING.toString())) {
            tvConfirmationInfo.setText(
                    getString(R.string.asset_confirmation_info, ar.getConfirmedTimes(), ar.getConfirmationNumber()));
        }

        tvFinishTime.setText(ar.getCompletedAt());
        tvAddress.setText(ar.getAddress());
        tvTxid.setText(ar.getTxid());

        if(!isRecharge) {
            llFee.setVisibility(View.VISIBLE);
            tvFee.setText(ar.getFee());
            tvSubmitTime.setText(ar.getSubmittedAt());
            llSubmitTime.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_record_detail_activity;
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @OnClick(R2.id.btn_copy_txid)
    public void onViewClicked() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, ar.getTxid()));
        ToastUtil.showShort(getContext(), R.string.asset_copied_txid_to_clipboard);
    }
}
