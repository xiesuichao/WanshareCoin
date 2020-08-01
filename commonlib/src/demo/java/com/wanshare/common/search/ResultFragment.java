package com.wanshare.common.search;

import com.wanshare.common.R;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.common.base.Searchable;
import com.wanshare.wscomponent.logger.LogUtil;

/**
 * 搜索结果
 * </br>
 * Date: 2018/9/3 15:12
 *
 * @author hemin
 */
public class ResultFragment extends BaseListFragment implements Searchable {
    @Override
    protected int getContentView() {
        return R.layout.common_fragment_result;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSearch(String keyword) {
        LogUtil.d("ResultFragment onSearch keyword:"+keyword);
    }


}
