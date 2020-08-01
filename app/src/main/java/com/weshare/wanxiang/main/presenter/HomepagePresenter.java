package com.weshare.wanxiang.main.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.weshare.wanxiang.main.contract.HomepageContract;
import com.weshare.wanxiang.main.model.api.service.HomepageServer;
import com.weshare.wanxiang.main.model.bean.ExchangeListBean;
import com.weshare.wanxiang.main.model.bean.ProjectBean;
import com.weshare.wanxiang.main.model.bean.ProjectListBean;

import java.util.List;

import com.wanshare.wscomponent.http.ApiClient;


/**
 * Presenter层实现
 */
public class HomepagePresenter extends BasePresenter<HomepageContract.View> implements HomepageContract.Presenter {
    private static final String TAG = HomepagePresenter.class.getSimpleName();
    private HomepageServer mServer;

    public HomepagePresenter(HomepageContract.View rootView) {
        super(rootView);
        mServer = (HomepageServer) ApiClient.getInstance().create(HomepageServer.class);
    }

    public HomepagePresenter() {
    }

    public void getExchangeList(int currentPage, int pageSize) {
        mServer.getExchangeList(currentPage, pageSize).compose(RxSchedulers.<ExchangeListBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<ExchangeListBean>(this) {
                    @Override
                    public void onNext(ExchangeListBean exchangeListBean) {
                        if (mRootView == null) {
                            return;
                        }
                        if (null != exchangeListBean) {
                            List<Exchange> exchanges = exchangeListBean.getExchange();
                            if (null != exchanges) {
                                mRootView.showExchangeList(exchanges);
                            }
                        }
                    }
                });
    }

    @Override
    public void getProjectList(int currentPage, int pageSize) {
        mServer.getProjectList(currentPage, pageSize).compose(RxSchedulers.<ProjectListBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<ProjectListBean>(this) {
                    @Override
                    public void onNext(ProjectListBean projectListBean) {
                        if (mRootView == null) {
                            return;
                        }
                        if (null != projectListBean) {
                            List<ProjectBean> projects = projectListBean.getItems();
                            if (null != projects) {
                                mRootView.showProjectList(projects);
                            }
                        }
                    }
                });
    }
}
