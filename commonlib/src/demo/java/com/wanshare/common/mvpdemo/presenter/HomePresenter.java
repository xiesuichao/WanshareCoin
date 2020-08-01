package com.wanshare.common.mvpdemo.presenter;

import android.util.Log;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.mvpdemo.contract.HomeContract;
import com.wanshare.common.mvpdemo.model.api.service.HomeServer;
import com.wanshare.common.mvpdemo.model.bean.Notice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import wanshare.wscomponent.http.ApiClient;

/**
 * Presenter层实现
 * </br>
 * Date: 2018/7/21 17:47
 *
 * @author hemin
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private static final String TAG = HomePresenter.class.getSimpleName();
    public static final int NOTICE_SIZE = 3;

    public HomePresenter(HomeContract.View rootView) {
        super(rootView);
    }

    public HomePresenter() {
    }

    public void getNoticeList() {
        // 调用model层代码
        HomeServer server = (HomeServer) ApiClient.getInstance().create(HomeServer.class);
        server.getNoticeList()
                .compose(RxSchedulers.<List<Notice>>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<List<Notice>>(this) {

                    @Override
                    public void onNext(List<Notice> notices) {
                        if (mRootView == null) {
                            return;
                        }

                        Log.d(TAG, "onNext: " + notices);
                        Log.d(TAG, "onNext: on thread:" + Thread.currentThread().getName());

                        // 处理数据，更新UI
                        final List<String> list = new ArrayList<>(notices.size());
                        for (int i = 0; i < NOTICE_SIZE && i < notices.size(); i++) {
                            list.add(notices.get(i).getTitle());
                        }

                        mRootView.showNoticeList(list);
                    }
                });

    }

}
