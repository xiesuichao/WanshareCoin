package com.wanshare.crush.exchange.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.adapter.CustomRecyclerViewDivider;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.exchange.ExchangeArouterConstant;
import com.wanshare.common.widget.text.CustomSearchLayout;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.R2;
import com.wanshare.crush.exchange.adapter.SearchCollectionExchangeAdapter;
import com.wanshare.crush.exchange.model.api.ExchangeServer;
import com.wanshare.crush.exchange.model.bean.TopExchange;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * 搜索币对收藏页
 * <p>
 * 截止2018-8-25接口还未提供，后台说下版本提供
 *
 * @author wangdunwei
 */
@Route(path = ExchangeArouterConstant.SEARCH_COLLECTION_EXCHANGE)
public class SearchCollectionExchangeActivity extends BaseActivity {

    @BindView(R2.id.searchLayout)
    CustomSearchLayout searchLayout;
    @BindView(R2.id.rv)
    RecyclerView rv;
    private SearchCollectionExchangeAdapter adapter;

    @Override
    protected void initData() {
        //测试代码
        ApiClient.getInstance().create(ExchangeServer.class)
                 .getExchangeBySort(1, "amount", 10)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .unsubscribeOn(Schedulers.io())
                 .subscribe(new Consumer<TopExchange>() {
                     @Override
                     public void accept(TopExchange topExchange) {
                         adapter.updateList(topExchange.getExchange());
                     }
                 });
    }

    @Override
    protected void initView() {
        searchLayout.setOnTextChangeListener(new CustomSearchLayout.OnInputChangeListener() {
            @Override
            public void textChange(Editable s) {

            }
        });
        searchLayout.setOnSearchCancelListener(new CustomSearchLayout.OnSearchCancelListener() {
            @Override
            public void cancel() {
                finish();
            }
        });

        adapter = new SearchCollectionExchangeAdapter(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new CustomRecyclerViewDivider(2,
                getResources().getColor(R.color.color_gray_light), 0));
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.exchange_search_collection_activity;
    }
}
