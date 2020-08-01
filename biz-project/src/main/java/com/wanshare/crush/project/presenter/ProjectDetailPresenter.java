package com.wanshare.crush.project.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.project.contract.ProjectDetailContract;
import com.wanshare.crush.project.model.bean.ProjectDetail;
import com.wanshare.crush.project.model.server.ProjectServer;

import com.wanshare.wscomponent.http.ApiClient;

/**
 * Created by xiesuichao on 2018/9/13.
 */

public class ProjectDetailPresenter extends BasePresenter<ProjectDetailContract.View>
        implements ProjectDetailContract.Presenter {

    public ProjectDetailPresenter(ProjectDetailContract.View rootView){
        super(rootView);
    }

    @Override
    public void getProjectDetail(String id) {
        ProjectServer server = ApiClient.getInstance().create(ProjectServer.class);
        server.getProjectDetail(id)
                .compose(RxSchedulers.<ProjectDetail>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<ProjectDetail>(this) {

                    @Override
                    public void onNext(ProjectDetail projectDetail) {
                        if (mRootView == null) {
                            return;
                        }
                        System.out.println("---getProjectDetail: " + projectDetail.toString());

                        mRootView.getProjectDetailSuccess(projectDetail);

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        System.out.println("---getProjectDetail onError ");
                        e.printStackTrace();
                    }
                });

    }


}
