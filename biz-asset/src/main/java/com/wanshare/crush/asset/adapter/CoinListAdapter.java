package com.wanshare.crush.asset.adapter;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;


import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;

import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.wscomponent.image.ImageLoader;
import com.wanshare.wscomponent.image.ImageOptions;
import com.wanshare.wscomponent.image.LoadOption;

import java.util.ArrayList;
import java.util.List;

/**
 * 币种列表
 * </br>
 * Date: 2018/8/24 15:32
 *
 * @author hemin
 */
public class CoinListAdapter extends CommonAdapter<CoinInfo> implements Filterable {

    private List<CoinInfo> originDatas;
    LoadOption mImageOptions;
    public CoinListAdapter(Context context) {
        super(context, R.layout.asset_item_coin_to_select,  new ArrayList<CoinInfo>());
        originDatas = new ArrayList<>();
        mImageOptions = new ImageOptions().error(R.drawable.ic_home_btb).placeHolder(R.drawable.ic_home_btb);
    }

    @Override
    protected void convert(ViewHolder holder, CoinInfo coinInfo, int position) {
        holder.setText(R.id.tv_short_name,coinInfo.getShortName());
        holder.setText(R.id.tv_fullname,coinInfo.getFullName());
        ImageView ivLogo = holder.getView(R.id.iv_logo);
        ImageLoader.with(getContext()).load(coinInfo.getLogo()).apply((ImageOptions) mImageOptions).into(ivLogo);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    mDatas = originDatas;
                }else{
                    List<CoinInfo> filteredList = new ArrayList<>();
                    for (CoinInfo row : originDatas) {

                        if (row.getFullName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mDatas=filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDatas;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDatas = (ArrayList<CoinInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void updateList(List<CoinInfo> list) {
        super.updateList(list);
        if(originDatas==null){
            originDatas = new ArrayList<>();
        }else{
            originDatas.clear();
        }
        originDatas.addAll(list);
        notifyDataSetChanged();
    }
}
