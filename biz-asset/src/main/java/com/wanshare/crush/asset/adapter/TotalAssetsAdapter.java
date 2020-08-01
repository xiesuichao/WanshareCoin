package com.wanshare.crush.asset.adapter;

import android.content.Context;
import android.os.Parcelable;

import com.wanshare.common.adapter.ItemViewDelegate;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.model.bean.AssetInfoBean;
import com.wanshare.crush.asset.model.bean.EstimatesBean;

import java.util.List;

/**
 * Created by Richard on 2018/8/28
 */
public class TotalAssetsAdapter extends MultiItemTypeAdapter<Parcelable> {
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public TotalAssetsAdapter(Context context, List<Parcelable> datas) {
        super(context, datas);
        addItemViewDelegate(VIEW_TYPE_HEADER, new ItemViewDelegate<Parcelable>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.asset_item_total_assets_header;
            }

            @Override
            public boolean isForViewType(Parcelable item, int position) {
                return getItemViewType(position) == VIEW_TYPE_HEADER;
            }

            @Override
            public void convert(ViewHolder holder, Parcelable parcelable, int position) {
                if (parcelable instanceof EstimatesBean) {
                    EstimatesBean estimatesBean = (EstimatesBean) parcelable;
                    holder.setText(R.id.tv_total_assets, estimatesBean.getBtc() + "");
                    String showCny = getResources().getString(R.string.asset_format_approximate_cny, estimatesBean.getCny() + "");
                    holder.setText(R.id.tv_cny, showCny);
                }
            }
        });
        addItemViewDelegate(VIEW_TYPE_NORMAL, new ItemViewDelegate<Parcelable>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.asset_item_total_assets;
            }

            @Override
            public boolean isForViewType(Parcelable item, int position) {
                return getItemViewType(position) == VIEW_TYPE_NORMAL;
            }

            @Override
            public void convert(ViewHolder holder, Parcelable parcelable, int position) {
                if (parcelable instanceof AssetInfoBean) {
                    AssetInfoBean assetBean = (AssetInfoBean) parcelable;
                    holder.setText(R.id.tv_name, assetBean.getShortName());
                    holder.setText(R.id.tv_balance, assetBean.getTotal());
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

}
