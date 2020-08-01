package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.contract.AuthorRealNameContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.AuthorBean;
import com.wanshare.crush.account.model.bean.IndividualReq;
import com.wanshare.crush.account.model.bean.UploadBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;

import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_ACCEPTED;
import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_APPLIED;
import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_REJECTED;

public class AuthorRealNamePresenter extends BasePresenter<AuthorRealNameContract.View> implements AuthorRealNameContract.Presenter{

    public AuthorRealNamePresenter(AuthorRealNameContract.View rootView) {
        super(rootView);
    }

    public AuthorRealNamePresenter() {
    }

    /**
     * 是否认证
     */
    @Override
    public boolean isAuthor(String status) {
        return !(TextUtils.isEmpty(status) || status.equals("none"));
    }

    @Override
    public boolean isRefuse(String status){
        return AUTHOR_TYPE_REJECTED.equals(status);
    }

    @Override
    public int getTvStatus(String status) {
        int strStatus;
        if (AUTHOR_TYPE_REJECTED.equals(status)) {
            strStatus = R.string.account_author_fail;
        } else if (AUTHOR_TYPE_APPLIED.equals(status)) {
            strStatus = R.string.account_author_loading;
        }else if (AUTHOR_TYPE_ACCEPTED.equals(status)) {
            strStatus = R.string.account_author_success_pass;
        }else{
            strStatus = R.string.account_author_not;
        }
        return strStatus;
    }

    @Override
    public void individual(IndividualReq req) {
        ApiClient.getInstance().create(AccountServer.class).individual(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.onIndividualResult();
                    }
                });
    }

    @Override
    public void uploadFile(List<String> list) {
        Disposable disposable = new UploadTask()
                .subscribe(list)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<UploadBean>>() {
                    @Override
                    public void accept(List<UploadBean> list) throws Exception {
                        List<String> urls = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            UploadBean bean = list.get(i);
                            if (bean != null) {
                                urls.add(bean.getUrl());
                            }

                        }
                        mRootView.resultUploadFile(urls);
                    }
                });
        addDispose(disposable);
    }

    @Override
    public void queryIndividual() {
        ApiClient.getInstance().create(AccountServer.class).queryIndividual()
                .compose(RxSchedulers.<AuthorBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<AuthorBean>(this) {
                    @Override
                    public void onNext(AuthorBean object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        if (object == null) {
                            object = new AuthorBean();
                        }
                        mRootView.resultQueryIndividual(object.getIndividualApplication());
                    }
                });
    }

    @Override
    public int getIvStatus(String status) {
        int strStatus;
        if (AUTHOR_TYPE_REJECTED.equals(status)) {
            strStatus = R.drawable.ic_shshibai;
        } else if (AUTHOR_TYPE_APPLIED.equals(status)) {
            strStatus = R.drawable.ic_shenghe;
        }else if (AUTHOR_TYPE_ACCEPTED.equals(status)) {
            strStatus = R.drawable.ic_succ;
        }else{
            strStatus = R.drawable.ic_shenghe;
        }
        return strStatus;
    }
}
