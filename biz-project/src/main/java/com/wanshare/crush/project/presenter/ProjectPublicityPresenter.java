package com.wanshare.crush.project.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.project.contract.ProjectPublicityContract;
import com.wanshare.crush.project.model.bean.Project;
import com.wanshare.crush.project.model.server.ProjectServer;


import com.wanshare.wscomponent.http.ApiClient;

/**
 * Created by xiesuichao on 2018/8/21.
 */

public class ProjectPublicityPresenter extends BasePresenter<ProjectPublicityContract.View>
        implements ProjectPublicityContract.Presenter{

    public ProjectPublicityPresenter(ProjectPublicityContract.View rootView){
        super(rootView);
    }

    @Override
    public void getProjectList(int page, String sortKey, int limit) {
        ProjectServer server = ApiClient.getInstance().create(ProjectServer.class);
        server.getProjectList(page, sortKey, limit)
                .compose(RxSchedulers.<Project>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Project>(this) {

                    @Override
                    public void onNext(Project projectModel) {
                        if (mRootView == null) {
                            return;
                        }
                        System.out.println("---getProjectList: " + projectModel.toString());

                        mRootView.getProjectListSuccess(projectModel);

                    }

                });

    }



}
