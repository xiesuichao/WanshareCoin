package com.wanshare.crush.project.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.biz.setting.SettingInfoManager;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.model.bean.Project;
import com.wanshare.wscomponent.image.ImageLoader;

import java.util.ArrayList;

/**
 * Created by xiesuichao on 2018/8/21.
 */

public class ProjectPublicityRvAdapter extends CommonAdapter<Project.ItemsBean> {

    private Context mContext;

    public ProjectPublicityRvAdapter(Context context) {
        super(context, R.layout.project_item_activity_project_publicity, new ArrayList<Project.ItemsBean>());
        this.mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, Project.ItemsBean project, int position) {
        if (position == 0) {
            holder.setVisible(R.id.tv_project_publicity_line, View.VISIBLE);
        } else {
            holder.setVisible(R.id.tv_project_publicity_line, View.GONE);
        }

        ImageView iconIv = holder.getView(R.id.iv_project_publicity_logo);
//        ImageLoader.with(mContext).load(project.getCoinLogo()).into(iconIv);

        holder.setText(R.id.tv_project_publicity_short_name, project.getShortName());
        holder.setText(R.id.tv_project_publicity_full_name, "(" + project.getFullName() + ")");
        holder.setText(R.id.tv_project_publicity_value, mContext.getString(R.string.project_online_exchange)
                + " " + project.getListedExchange() + " " + mContext.getString(R.string.project_unit) + " | "
                + mContext.getString(R.string.project_market_value) + " " + mContext.getString(R.string.project_usd_sign)
                + project.getMarketValue());

        holder.setText(R.id.tv_project_publicity_trade_amount, mContext.getString(R.string.project_trade_amount)
                + " " +  mContext.getString(R.string.project_usd_sign) + project.getUsdAmount24H() + "/24h");

        holder.setText(R.id.tv_project_publicity_news, "text");

        if (SettingInfoManager.isNightMode()){
            holder.itemView.setBackgroundColor(0xff2c2f43);
            holder.getView(R.id.cv_project_publicity).setBackgroundColor(0xff2c2f43);
            holder.getView(R.id.tv_project_publicity_line).setBackgroundColor(0xff2c2f43);
            holder.getView(R.id.ll_project_publicity_item).setBackgroundColor(0xff2c2f43);
            ((TextView)holder.getView(R.id.tv_project_publicity_short_name)).setTextColor(0xffffffff);
            ((TextView)holder.getView(R.id.tv_project_publicity_full_name)).setTextColor(0xff808490);
            ((TextView)holder.getView(R.id.tv_project_publicity_value)).setTextColor(0xff808490);
            ((TextView)holder.getView(R.id.tv_project_publicity_trade_amount)).setTextColor(0xff808490);
            holder.getView(R.id.tv_project_publicity_bot_line).setBackgroundColor(0xff1f2436);
            holder.getView(R.id.tv_project_publicity_news).setBackgroundColor(0xffffffff);

        }else {
            holder.itemView.setBackgroundColor(0xffFFFFFF);
            holder.getView(R.id.cv_project_publicity).setBackgroundColor(0xffFFFFFF);
            holder.getView(R.id.tv_project_publicity_line).setBackgroundColor(0xffFFFFFF);
            holder.getView(R.id.ll_project_publicity_item).setBackgroundColor(0xffFFFFFF);
            ((TextView)holder.getView(R.id.tv_project_publicity_short_name)).setTextColor(0xff294058);
            ((TextView)holder.getView(R.id.tv_project_publicity_short_name)).setTextColor(0xff65707c);
            ((TextView)holder.getView(R.id.tv_project_publicity_value)).setTextColor(0xff65707c);
            ((TextView)holder.getView(R.id.tv_project_publicity_trade_amount)).setTextColor(0xff65707c);
            holder.getView(R.id.tv_project_publicity_bot_line).setBackgroundColor(0xfff3f4f6);
            holder.getView(R.id.tv_project_publicity_news).setBackgroundColor(0xff294058);
        }

    }


}
