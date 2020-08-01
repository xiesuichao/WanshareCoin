package com.weshare.wanxiang.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.wscomponent.image.ImageLoader;
import com.wanshare.wscomponent.utils.ArithUtil;
import com.weshare.wanxiang.R;
import com.weshare.wanxiang.main.model.bean.ProjectBean;
import com.weshare.wanxiang.main.model.bean.ProjectListBean;

import java.util.ArrayList;

/**
 * Created by Jason on 2018/8/20.
 */

public class RecycleProjectAdapter extends CommonAdapter<ProjectBean> {
    public RecycleProjectAdapter(Context context) {
        super(context, R.layout.app_item_recycle_project, new ArrayList<ProjectBean>());
    }

    @Override
    protected void convert(ViewHolder holder, ProjectBean projectBean, int position) {
        if (null == projectBean) {
            return;
        }
//        ImageLoader.with(mContext).load(projectBean.getCoinLogo()).into((ImageView) holder.getView(R.id.iv_project_logo));
        holder.setText(R.id.tv_project_name, projectBean.getShortName());
        holder.setText(R.id.tv_project_full_name, "(" + projectBean.getFullName() + ")");
        holder.setText(R.id.tv_onlie_exchange_num, projectBean.getListedExchange() + "个");
        String marketValue = projectBean.getMarketValue();
        if (ArithUtil.bigVol(marketValue, "99999999")) {
            marketValue = ArithUtil.div(marketValue, "100000000", 2) + "亿";
        } else if (ArithUtil.bigVol(marketValue, "9999")) {
            marketValue = ArithUtil.div(marketValue, "10000", 2) + "万";
        }
        holder.setText(R.id.tv_market_value_num, "$" + marketValue);

        String usdAmount24H = projectBean.getUsdAmount24H();
        if (ArithUtil.bigVol(usdAmount24H, "99999999")) {
            usdAmount24H = ArithUtil.div(usdAmount24H, "100000000", 2) + "亿";
        } else if (ArithUtil.bigVol(usdAmount24H, "9999")) {
            usdAmount24H = ArithUtil.div(usdAmount24H, "10000", 2) + "万";
        }
        holder.setText(R.id.tv_project_amout_num, "$" + usdAmount24H);

        holder.setOnClickListener(R.id.tv_project_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnMessageListener) {
                    mOnMessageListener.onMessageClick(v);
                }
            }
        });
    }

    public interface OnMessageListener {
        void onMessageClick(View view);
    }

    private OnMessageListener mOnMessageListener;

    public void setOnMessageListener(OnMessageListener onMessageListener) {
        this.mOnMessageListener = onMessageListener;
    }
}
