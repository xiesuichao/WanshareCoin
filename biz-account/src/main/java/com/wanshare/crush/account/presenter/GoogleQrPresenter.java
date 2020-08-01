package com.wanshare.crush.account.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.account.contract.GoogleQrContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.GoogleInfoEntity;
import com.wanshare.crush.account.model.bean.GoogleQrReq;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;

public class GoogleQrPresenter extends BasePresenter<GoogleQrContract.View> implements GoogleQrContract.Presenter {

    public GoogleQrPresenter(GoogleQrContract.View rootView) {
        super(rootView);
    }

    @Override
    public void initGoogleauth(GoogleQrReq req) {
        ApiClient.getInstance().create(AccountServer.class).initGoogleauth(BaseRequestBody.create(req))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<GoogleInfoEntity>(this) {
                    @Override
                    public void onNext(GoogleInfoEntity google) {
                        super.onNext(google);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.initGoogleauthResult(google);
                    }
                });
    }
}
