package com.wanshare.crush.account.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.constant.ThresholdConstants;
import com.wanshare.crush.account.contract.BaseCodeContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.view.widget.AdvancedCountdownTimer;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;

public class BaseCodePresenter<V extends BaseCodeContract.View> extends BasePresenter<V> implements BaseCodeContract.Presenter {

    protected AdvancedCountdownTimer mLearnTimeOutTimer = new AdvancedCountdownTimer(ThresholdConstants.SEND_VERIFICATION_CODE_COUNTDOWN * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished, int percent) {
            mRootView.onCountDown((int) (millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            mRootView.onCountDownFinish();
        }
    };


    public BaseCodePresenter(V rootView) {
        super(rootView);

    }

    @Override
    public void startCountDown() {
        mLearnTimeOutTimer.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLearnTimeOutTimer.cancel();
    }

    @Override
    public void sendVerificationCode(AccountVerificationReq req) {
        ApiClient.getInstance().create(AccountServer.class).sendVerificationCode(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object topExchange) {
                        super.onNext(topExchange);
                        mRootView.onSendVerifyCodeSuccess();
                    }
                });
    }


}
