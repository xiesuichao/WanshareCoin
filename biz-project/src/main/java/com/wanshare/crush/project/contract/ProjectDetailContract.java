package com.wanshare.crush.project.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.project.model.bean.ProjectDetail;

/**
 * Created by xiesuichao on 2018/8/22.
 */

public class ProjectDetailContract {

    public interface View extends IView {
        void getProjectDetailSuccess(ProjectDetail projectDetail);
    }

    public interface Presenter extends IPresenter {
        void getProjectDetail(String id);
    }

}
