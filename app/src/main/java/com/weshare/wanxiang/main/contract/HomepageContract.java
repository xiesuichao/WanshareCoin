package com.weshare.wanxiang.main.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.crush.project.model.bean.Project;
import com.weshare.wanxiang.main.model.bean.ProjectBean;

import java.util.List;

/**
 * 首页
 */
public class HomepageContract {

    public interface View extends IView{
        void showExchangeList(List<Exchange> exchanges);

        void showProjectList(List<ProjectBean> projects);
    }

    public interface Presenter extends IPresenter {
         void getExchangeList(int currentPage,int pageSize);

         void getProjectList(int currentPage,int pageSize);
    }

}
