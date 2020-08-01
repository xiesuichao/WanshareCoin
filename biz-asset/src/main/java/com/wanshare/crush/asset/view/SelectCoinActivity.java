package com.wanshare.crush.asset.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.RecycleViewDivider;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.widget.text.SimpleTextWatcher;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.adapter.CoinListAdapter;
import com.wanshare.crush.asset.contract.SelectCoinContract;
import com.wanshare.crush.asset.model.bean.CoinInfo;

import java.util.List;

import butterknife.OnClick;


/**
 * 选择币种
 * </br>
 * Date: 2018/8/24 11:55
 *
 * @author hemin
 */
@Route(path = AssetArouterConstant.ASSET_SELECT_COIN)
public abstract class SelectCoinActivity extends BaseActivity<SelectCoinContract.Presenter> implements SelectCoinContract.View {

    EditText mEtSearch;
    ImageView mIvDelete;
    RecyclerView mRvCoinList;

    CoinListAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_select_coin;
    }

    @Override
    protected void initView() {
        mEtSearch = findViewById(R.id.et_search);
        mIvDelete = findViewById(R.id.iv_delete);
        mRvCoinList = findViewById(R.id.rv_coin_list);

        mEtSearch.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIvDelete.setVisibility(s.length()>0? View.VISIBLE:View.GONE);

                if(mAdapter!=null){
                    mAdapter.getFilter().filter(s);
                }
            }
        });

        mRvCoinList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter =new CoinListAdapter(getContext());
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                SelectCoinActivity.this.onItemClick(mAdapter.getItem(position));
            }
        });

        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.color_gray_light1));
        mRvCoinList.addItemDecoration(recycleViewDivider);
        mRvCoinList.setAdapter(mAdapter);
    }

    protected abstract void onItemClick(CoinInfo item);

    @Override
    public void showCoinList(List<CoinInfo> resultList) {
        mAdapter.updateList(resultList);
    }

    @Override
    protected void initData() {
        mPresenter.getCoinLists();
    }

    @OnClick(R2.id.iv_delete)
    public void onViewClicked(View v) {
        int id = v.getId();
        if(id == R.id.iv_delete){
            mEtSearch.setText("");
        }
    }
}
