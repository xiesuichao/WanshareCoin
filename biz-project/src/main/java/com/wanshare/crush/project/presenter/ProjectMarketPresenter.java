package com.wanshare.crush.project.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.project.contract.ProjectMarketContract;
import com.wanshare.crush.project.model.bean.ProjectMarket;
import com.wanshare.crush.project.model.server.ProjectServer;

import com.wanshare.wscomponent.http.ApiClient;

/**
 * Created by xiesuichao on 2018/9/14.
 */

public class ProjectMarketPresenter extends BasePresenter<ProjectMarketContract.View>
        implements ProjectMarketContract.Presenter {

    public ProjectMarketPresenter(ProjectMarketContract.View rootView){
        super(rootView);
    }

    @Override
    public void getProjectMarketList(String id, int page) {
        ProjectServer server = ApiClient.getInstance().create(ProjectServer.class);
        server.getProjectMarketList(id, page)
                .compose(RxSchedulers.<ProjectMarket>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<ProjectMarket>(this) {

                    @Override
                    public void onNext(ProjectMarket projectMarket) {
                        if (mRootView == null) {
                            return;
                        }
                        System.out.println("---getProjectMarketList: " + projectMarket.toString());

                        mRootView.getProjectMarketListSuccess(projectMarket);

                    }

                });
    }


}
