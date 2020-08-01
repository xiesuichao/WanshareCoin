package com.wanshare.crush.exchange.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.wanshare.crush.exchange.R;
import com.wanshare.wscomponent.image.GlideRoundTransform;
import com.wanshare.wscomponent.image.ImageLoader;
import com.wanshare.wscomponent.image.ImageOptions;
import com.wanshare.wscomponent.utils.DensityUtil;

import java.util.List;

/**
 * @author wangdunwei
 */
public class ExchangeBannerAdapter extends PagerAdapter {
    private final ImageOptions imageOptions;
    private List<String> list;

    public ExchangeBannerAdapter(List<String> list) {
        this.list = list;
        imageOptions = new ImageOptions();
        imageOptions.setOptions(new RequestOptions().transform(new GlideRoundTransform()));
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                                  .inflate(R.layout.exchange_banner_item, container, false);
        CardView cardView = view.findViewById(R.id.card_view);
        ImageView iv = view.findViewById(R.id.iv);
        ImageLoader.with(container.getContext())
                   .load(list.get(position))
                   .apply(imageOptions)
                   .into(iv);

        cardView.setMaxCardElevation(DensityUtil.dip2px(container.getContext(), 5));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
