package com.weshare.wanxiang.main.view;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.common.base.SearchSuggestionsSelect;
import com.wanshare.common.base.Searchable;
import com.weshare.wanxiang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2018/8/22.
 * <p>
 * 首页搜索页面 搜索交易所和项目
 */

public class HomeSearchKeyWordsFragment extends BaseListFragment implements Searchable, MultiItemTypeAdapter.OnItemClickListener {

    private CommonAdapter<String> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_homepage_search_keywords;
    }

    @Override
    protected void initData() {

        mAdapter = new CommonAdapter<String>(getContext(), R.layout.app_item_search_keywords, new ArrayList()) {
              @Override
              protected void convert(ViewHolder holder, String item, int position) {
                  holder.setText(R.id.tv_user_name, item);
              }
          };
        setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onSearch(String keyword) {

        if(TextUtils.isEmpty(keyword)){
            updateData(new ArrayList(), 10);
            return;
        }
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(keyword + i);
        }
        updateData(datas, 10);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (getActivity() instanceof SearchSuggestionsSelect) {
            ((SearchSuggestionsSelect)getActivity()).onSearchSelect(mAdapter.getItem(position));
        }
    }
}
