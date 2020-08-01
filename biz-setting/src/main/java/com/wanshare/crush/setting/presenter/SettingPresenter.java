package com.wanshare.crush.setting.presenter;

import com.google.gson.JsonObject;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.setting.contract.SettingContract;
import com.wanshare.crush.setting.model.service.SettingServer;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


/**
 * Presenter层实现
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {
    private static final String TAG = SettingPresenter.class.getSimpleName();
    private SettingServer mServer;

    public SettingPresenter(SettingContract.View rootView) {
        super(rootView);
        mServer = (SettingServer) ApiClient.getInstance().create(SettingServer.class);
    }

    public SettingPresenter() {
    }

    public void loginOut() {
        mServer.loginOut(BaseRequestBody.create(new JsonObject())).compose(RxSchedulers.<JsonObject>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<JsonObject>(this){
                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if (null == mRootView) {
                            return;
                        }

                        mRootView.onLoginOutSuccess(jsonObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (null == mRootView) {
                            return;
                        }

                        mRootView.onLoginOutError();
                    }
                });
    }

}
