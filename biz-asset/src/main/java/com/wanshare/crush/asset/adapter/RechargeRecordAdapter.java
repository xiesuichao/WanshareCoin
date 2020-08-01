package com.wanshare.crush.asset.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.model.bean.AssetRecord;
import com.wanshare.crush.asset.model.bean.RechargeStatus;
import com.wanshare.crush.asset.model.bean.WithdrawStatus;

import java.util.ArrayList;

/**
 * @author wangdunwei
 * @date 2018/8/24
 */
public class RechargeRecordAdapter extends CommonAdapter<AssetRecord> {
    private boolean isRecharge;

    public RechargeRecordAdapter(Context context, boolean isRecharge) {
        super(context, R.layout.assest_record_item, new ArrayList<AssetRecord>(0));
        this.isRecharge = isRecharge;
    }

    @Override
    protected void convert(ViewHolder holder, AssetRecord assetRecord, int position) {
        holder.setText(R.id.tv_coin, assetRecord.getShortName());
        holder.setText(R.id.tv_count, String.valueOf(assetRecord.getAmount()));
        holder.setText(R.id.tv_time, assetRecord.getCompletedAt());

        String status;
        if(isRecharge) {
            status = RechargeStatus.of(assetRecord.getStatus()).toVisibleString(getContext());
        } else {
            status = WithdrawStatus.of(assetRecord.getStatus()).toVisibleString(getContext());
        }
        holder.setText(R.id.tv_status, status);

        if(!TextUtils.isEmpty(assetRecord.getFee())) {
            holder.setText(R.id.tv_fee, assetRecord.getFee());
        }
    }
}
