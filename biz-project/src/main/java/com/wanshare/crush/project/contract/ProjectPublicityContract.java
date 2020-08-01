package com.wanshare.crush.project.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.project.model.bean.Project;

/**
 * Created by xiesuichao on 2018/8/21.
 */

public class ProjectPublicityContract {

    public interface View extends IView{
        void getProjectListSuccess(Project projectModel);
    }

    public interface Presenter extends IPresenter{
        void getProjectList(int page, String sortKey, int limit);
    }


}
