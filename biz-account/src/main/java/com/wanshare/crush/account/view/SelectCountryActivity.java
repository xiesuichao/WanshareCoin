package com.wanshare.crush.account.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ayvytr.prettyitemdecoration.header.StickyHeaderItemDecoration;
import com.wanshare.common.adapter.CustomRecyclerViewDivider;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.widget.text.CustomSearchLayout;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.adapter.CountryAdapter;
import com.wanshare.crush.account.contract.SelectContryContract;
import com.wanshare.crush.account.model.bean.Country;
import com.wanshare.crush.account.presenter.SelectCountryPresenter;
import com.wanshare.crush.account.view.widget.QuickIndexView;
import com.wanshare.wscomponent.utils.DensityUtil;
import com.wanshare.wscomponent.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * 选择国家/国家代码页面.
 *
 * @author huyuqi
 */
@Route(path = AccountArouterConstant.ACCOUNT_SELECT_COUNTRY)
public class SelectCountryActivity extends BaseActivity<SelectContryContract.Presenter> implements SelectContryContract.View {

    public static final String EXTRA_COUNTY = "extra_country";

    @BindView(R2.id.csl_project_search)
    CustomSearchLayout customSearchLayout;
    @BindView(R2.id.rv_country)
    RecyclerView rvCountry;
    @BindView(R2.id.quick_index_view)
    QuickIndexView quickIndexView;

    private CountryAdapter mCountryAdapter;
    private LinearLayoutManager mLayoutManager;

    boolean move;
    int mIndex;

    private List<Country> list;
    private QuickIndexView.OnLetterChangeListener onLetterChangeListener;
    private boolean isNormal = true;

    @Override
    protected SelectContryContract.Presenter getPresenter() {
        return new SelectCountryPresenter(this);
    }

    @Override
    protected void initData() {
        mPresenter.getCountryList(getAssets());
    }

    @Override
    protected void initView() {
        mCountryAdapter = new CountryAdapter();
        mLayoutManager = new LinearLayoutManager(this);
        rvCountry.setLayoutManager(mLayoutManager);
        rvCountry.setAdapter(mCountryAdapter);

        customSearchLayout.setOnSearchCancelListener(new CustomSearchLayout.OnSearchCancelListener() {
            @Override
            public void cancel() {
                finish();
            }
        });
        customSearchLayout.setOnTextChangeListener(new CustomSearchLayout.OnInputChangeListener() {
            @Override
            public void textChange(final Editable s) {
                isNormal = s.toString().isEmpty();
                if(s.toString().length() == 0)
                {
                    mCountryAdapter.setData(list);
                }
                else{
                    filterByKey(s.toString());
                }
            }
        });

        mCountryAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_COUNTY, mCountryAdapter.getItem(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        onLetterChangeListener = new QuickIndexView.OnLetterChangeListener() {
            @Override
            public void onLetterChange(int position, String text, QuickIndexView quickIndexView) {
                if(isNormal) {
                    mIndex = mCountryAdapter.getPosition(text);
                    moveToPosition(mIndex);
                }
                ToastUtil.showShort(SelectCountryActivity.this, text);
            }
        };
        quickIndexView.setOnLetterChangeListener(onLetterChangeListener);
        rvCountry.addItemDecoration(new CustomRecyclerViewDivider(DensityUtil.dip2px(mContext,0.5f), getResources().getColor(R.color.color_gray_light1),0));
        rvCountry.addItemDecoration(new StickyHeaderItemDecoration(mCountryAdapter));
        rvCountry.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (move){
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - mLayoutManager.findFirstVisibleItemPosition();
                    if ( 0 <= n && n < rvCountry.getChildCount()){
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = rvCountry.getChildAt(n).getTop();
                        //最后的移动
                        rvCountry.scrollBy(0, top);
                    }
                }
            }
        });
    }

    private void filterByKey(final String key) {
        Observable.fromIterable(list)
                  .filter(new Predicate<Country>() {
                      @Override
                      public boolean test(Country country) {
                          //英文过滤
//                          return country.getEn().toLowerCase().contains(key.toLowerCase());

                          return country.getCn().contains(key) || country.getPinyin().contains(key.toUpperCase(Locale.getDefault()));
                      }
                  }).toList()
                    .subscribe(new Consumer<List<Country>>() {
                      @Override
                      public void accept(List<Country> countries) {
                          mCountryAdapter.setData(countries);
                      }
                  });
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            rvCountry.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = rvCountry.getChildAt(n - firstItem).getTop();
            rvCountry.scrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            rvCountry.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_select_country;
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    public void showCountryList(List<Country> list) {
        List<String> letterList = new ArrayList<>();
        for(int i = 4; i < list.size(); i++) {
            String key = list.get(i).getPinyin().substring(0,1);
            if(letterList.indexOf(key) < 0) {
                letterList.add(key + "");
            }
        }
        quickIndexView.setIndexList(letterList);
        this.list = list;
        mCountryAdapter.setData(this.list);
    }

    @Override
    public void getCountryListFailed() {

    }

}
