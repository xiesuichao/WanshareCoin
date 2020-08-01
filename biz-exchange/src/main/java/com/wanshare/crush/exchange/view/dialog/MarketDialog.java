package com.wanshare.crush.exchange.view.dialog;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.biz.exchange.IMarketDialog;
import com.wanshare.common.biz.exchange.OnMarketItemClickListener;
import com.wanshare.common.widget.viewpager.ViewPagerIndicator;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.R2;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.crush.exchange.view.fragment.MarketFragment;
import com.wanshare.wscomponent.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wanshare.common.constant.IntentConstant.EXTRA_EXCHANGE;

/**
 * 市场Dialog，用来显示交易所下的行情列表.
 *
 * @author wangdunwei
 * @date 2018/8/30
 */
public class MarketDialog extends DialogFragment implements IMarketDialog {
    private static final String EXTRA_Y = "extra_y";
    @BindView(R2.id.vpi_exchange)
    ViewPagerIndicator vpiExchange;
    @BindView(R2.id.vp_exchange)
    ViewPager vpExchange;

    private Exchange exchange;
    private int y = 0;
    private List<BaseFragment> marketFragmentList = new ArrayList<>(0);
    private OnMarketItemClickListener onItemClickListener;

    /**
     * 使用这个创建MarketDialog
     *
     * @param exchange  Exchange
     * @param belowView Dialog显示在belowView底部
     * @return MarketDialog
     */
    public static MarketDialog newInstance(Exchange exchange, View belowView) {
        int[] location = new int[2];
        belowView.getLocationOnScreen(location);
        int y = location[1] + belowView.getMeasuredHeight() / 2;

        MarketDialog marketDialog = new MarketDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_EXCHANGE, exchange);
        bundle.putInt(EXTRA_Y, y);
        marketDialog.setArguments(bundle);
        return marketDialog;
    }

    public MarketDialog(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if(bundle != null) {
            y = bundle.getInt(EXTRA_Y);
            exchange = bundle.getParcelable(EXTRA_EXCHANGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        Window window = getDialog().getWindow();
        //需要用android.R.id.content这个view
        View view = inflater.inflate(R.layout.exchange_market_dialog,
                ((ViewGroup) window.findViewById(android.R.id.content)), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setDimAmount(0);
        window.setLayout(-1, -2);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.TOP;
        lp.y = y;
        lp.height = DensityUtil.dip2px(getContext(), 430);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        //测试代码
        String groupId = "0";
        String exchangeId = "0";
        marketFragmentList.add(MarketFragment.newInstance(exchangeId, groupId));
        marketFragmentList.add(MarketFragment.newInstance(exchangeId, groupId));

        vpExchange.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return marketFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return marketFragmentList.size();
            }
        });
        vpiExchange.setTitles(new String[]{"全部", "标题1"}, vpExchange);
    }

    @Override
    public void showMarketDialog(FragmentManager manager, View view, String tag) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int y = location[1] + view.getMeasuredHeight() / 2;

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_EXCHANGE, exchange);
        bundle.putInt(EXTRA_Y, y);
        setArguments(bundle);
        this.show(manager,tag);
    }

    @Override
    public void hideMarketDialog() {
        this.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (this.onItemClickListener != null) {
            onItemClickListener.onDismiss();
        }
        super.onDismiss(dialog);
    }

    @Override
    public void setOnItemClick(OnMarketItemClickListener onItemClickListener) {
        this.onItemClickListener=onItemClickListener;
    }

    public void onItemClick(String marketId){
        if(this.onItemClickListener!=null){
            onItemClickListener.onItemClick(marketId);
        }
    }
}
