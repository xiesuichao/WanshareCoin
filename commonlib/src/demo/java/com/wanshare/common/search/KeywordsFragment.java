package com.wanshare.common.search;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.wanshare.common.R;
import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.common.base.CommonSearchActivity;
import com.wanshare.common.base.SearchSuggestionsSelect;
import com.wanshare.common.base.Searchable;
import com.wanshare.wscomponent.logger.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 建议关键词列表
 * </br>
 * Date: 2018/9/3 15:09
 *
 * @author hemin
 */
public class KeywordsFragment extends BaseListFragment implements Searchable, MultiItemTypeAdapter.OnItemClickListener {
    private CommonAdapter<String> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.layout_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CommonAdapter<String>(getContext(), R.layout.item_type1, new ArrayList()) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                holder.setText(R.id.tv_user_name, item);
            }
        };
        setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSearch(String keyword) {
        LogUtil.d("KeywordsFragment onSearch keyword:" + keyword);
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
