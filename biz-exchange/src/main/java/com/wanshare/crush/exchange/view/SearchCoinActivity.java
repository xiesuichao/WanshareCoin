package com.wanshare.crush.exchange.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.adapter.CustomRecyclerViewDivider;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.exchange.ExchangeArouterConstant;
import com.wanshare.common.widget.text.CustomSearchLayout;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.R2;
import com.wanshare.crush.exchange.adapter.CoinAdapter;
import com.wanshare.crush.exchange.contract.SearchCoinContract;
import com.wanshare.crush.exchange.model.bean.Market;
import com.wanshare.crush.exchange.presenter.SearchCoinPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

import static com.wanshare.common.constant.IntentConstant.EXTRA_BUYER_COIN_NAME;
import static com.wanshare.common.constant.IntentConstant.EXTRA_EXCHANGE_ID;

/**
 * 搜索币页面
 *
 * @author wangdunwei
 */
@Route(path = ExchangeArouterConstant.SEARCH_COIN)
public class SearchCoinActivity extends BaseActivity<SearchCoinContract.Presenter> implements SearchCoinContract.View {

    @BindView(R2.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R2.id.search_layout)
    CustomSearchLayout searchLayout;

    private CoinAdapter coinAdapter;

    private String exchangeId;
    private String buyerCoinName;
    private List<Market> list;

    @Override
    protected SearchCoinContract.Presenter getPresenter() {
        return new SearchCoinPresenter(this);
    }

    @Override
    protected void initIntent() {
        super.initIntent();
        exchangeId = getIntent().getStringExtra(EXTRA_EXCHANGE_ID);
        buyerCoinName = getIntent().getStringExtra(EXTRA_BUYER_COIN_NAME);
    }

    @Override
    protected void initData() {
        if(!TextUtils.isEmpty(exchangeId) && !TextUtils.isEmpty(buyerCoinName)) {
            mPresenter.requestMarketList(exchangeId, buyerCoinName);
        } else {
            ToastUtil.showShort(getContext(), R.string.exchange_cannot_get_market_info);
        }
    }

    @Override
    protected void initView() {
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.addItemDecoration(new CustomRecyclerViewDivider(2,
                getResources().getColor(R.color.color_gray_light), 0));
        coinAdapter = new CoinAdapter(getContext());
        rvSearch.setAdapter(coinAdapter);

        searchLayout.setOnSearchCancelListener(new CustomSearchLayout.OnSearchCancelListener() {
            @Override
            public void cancel() {
                finish();
            }
        });

        searchLayout.setOnTextChangeListener(new CustomSearchLayout.OnInputChangeListener() {
            @Override
            public void textChange(Editable s) {
                if(s.toString().isEmpty()) {
                    coinAdapter.updateList(list);
                } else {
                    filterByCoin(s.toString().toUpperCase());
                }
            }
        });
    }

    private void filterByCoin(final String key) {
        Observable.fromIterable(list)
                  .filter(new Predicate<Market>() {
                      @Override
                      public boolean test(Market market) {
                          return market.getSellerCoin().contains(key) || market.getBuyerCoin().contains(key);
                      }
                  })
                  .toList()
                  .subscribe(new Consumer<List<Market>>() {
                      @Override
                      public void accept(List<Market> markets) {
                          coinAdapter.updateList(markets);
                      }
                  });
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.exchange_activity_search_coin;
    }

    @Override
    public void showExchangeList(List<Market> list) {
        this.list = list;
        coinAdapter.updateList(this.list);
    }
}
