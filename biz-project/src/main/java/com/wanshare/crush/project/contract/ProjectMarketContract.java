package com.wanshare.crush.project.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.project.model.bean.ProjectMarket;

/**
 * Created by xiesuichao on 2018/9/14.
 */

public class ProjectMarketContract {

    public interface View extends IView{
        void getProjectMarketListSuccess(ProjectMarket projectMarket);
    }

    public interface Presenter extends IPresenter{
        void getProjectMarketList(String id, int page);
    }

}
