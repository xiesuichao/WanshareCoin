package com.weshare.wanxiang.main.view;

import android.text.TextUtils;
import android.widget.TextView;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.common.base.Searchable;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.ToastUtil;
import com.weshare.wanxiang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2018/8/22.
 * <p>
 * 搜索结果
 */
public class HomeSearchResultFragment extends BaseListFragment implements Searchable {

    private CommonAdapter<String> mAdapter;
    @Override
    protected int getContentView() {
        return R.layout.fragment_homepage_search_result;
//        return R.layout.layout_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new CommonAdapter<String>(getContext(), R.layout.app_item_search_result, new ArrayList()) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                holder.setText(R.id.tv_user_name, item);
            }
        };
        setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSearch(String keyword) {
        if(TextUtils.isEmpty(keyword)){
            updateData(new ArrayList(), 10);
            return;
        }
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("结果: "+keyword + i);
        }
        updateData(datas, 10);
    }


}
