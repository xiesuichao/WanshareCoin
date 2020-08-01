package com.wanshare.crush.account.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayvytr.prettyitemdecoration.header.StickyHeaderAdapter;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.model.bean.Country;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author wangdunwei
 * @date 2018/8/18
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>
        implements StickyHeaderAdapter<CountryAdapter.HeaderViewHolder> {
    private List<Country> list = new ArrayList<>(0);
    private OnItemClickListener onItemClickListener;

    @Override
    public int getId(int position) {
        return list.get(position).getId();
    }

    public int getPosition(String pinyin) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPinyin().substring(0, 1).equals(pinyin)) {
                return i;
            }
        }

        return 0;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(RecyclerView parent) {
        return new HeaderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item_country_header, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        holder.bind(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item_country, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    public void setData(List<Country> countries) {
        this.list = countries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Country getItem(int position) {
        return list.get(position);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv_key)
        TextView tvKey;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(int position) {
            tvKey.setText(list.get(position).getPinyin().substring(0, 1));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv_country)
        TextView tvCountry;
        @BindView(R2.id.tv_code)
        TextView tvCode;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(final int position) {
            Country country = list.get(position);
            tvCountry.setText(country.getCn());
            tvCode.setText("+" + country.getNo());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
