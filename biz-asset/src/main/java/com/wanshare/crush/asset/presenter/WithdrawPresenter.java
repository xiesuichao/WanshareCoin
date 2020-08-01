package com.wanshare.crush.asset.presenter;

import android.text.TextUtils;


import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.asset.contract.WithdrawContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.model.bean.GetWithdrawTotalBean;
import com.wanshare.crush.asset.model.bean.SuccessBean;
import com.wanshare.crush.asset.model.bean.WithdrawReqBean;
import com.wanshare.wscomponent.logger.LogUtil;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 提币
 * </br>
 * Date: 2018/8/25 18:05
 *
 * @author hemin
 */
public class WithdrawPresenter extends BasePresenter<WithdrawContract.View> implements WithdrawContract.Presenter {

    public WithdrawPresenter(WithdrawContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getAssetsInfo(String coinId) {
        ApiClient.getInstance().create(AssetServer.class)
                .getAssetByCoinId(coinId)
                .compose(RxSchedulers.<AssetByCoinBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<AssetByCoinBean>(this) {

                    @Override
                    public void onNext(AssetByCoinBean result) {
                        if (mRootView == null) {
                            return;
                        }

                        LogUtil.d("result:" + result);
                        mRootView.showAssetInfo(result);
                    }
                });
    }

    @Override
    public void getWithdrawTotalToday(String coinId) {
        ApiClient.getInstance().create(AssetServer.class)
                .getWithdrawTotalToday(coinId)
                .compose(RxSchedulers.<GetWithdrawTotalBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<GetWithdrawTotalBean>(this) {

                    @Override
                    public void onNext(GetWithdrawTotalBean result) {
                        if (mRootView == null) {
                            return;
                        }
                        LogUtil.d("result:" + result);
                        mRootView.showWithdrawTotalToday(result);
                    }
                });
    }

    @Override
    public void getCoinInfo(String coinId) {
        ApiClient.getInstance().create(AssetServer.class)
                .getCoinInfo(coinId)
                .compose(RxSchedulers.<CoinInfo>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<CoinInfo>(this) {

                    @Override
                    public void onNext(CoinInfo result) {
                        if (mRootView == null) {
                            return;
                        }
                        LogUtil.d("result:" + result);
                        mRootView.showCoinInfo(result);
                    }
                });
    }

    @Override
    public void doWithdraw(WithdrawReqBean reqBean) {
        ApiClient.getInstance().create(AssetServer.class)
                .withdraw(BaseRequestBody.create(reqBean))
                .compose(RxSchedulers.<SuccessBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<SuccessBean>(this) {

                    @Override
                    public void onNext(SuccessBean result) {
                        if (mRootView == null) {
                            return;
                        }
                        LogUtil.d("result:" + result);
                        mRootView.onWithdrawSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorBody = getErrorBody(e);
                        if (TextUtils.isEmpty(errorBody)) {
                            super.onError(e);
                        } else {
                            try {
                                JSONObject bodyJson = new JSONObject(errorBody);
                                String baseToken = bodyJson.optString("baseToken");
                                if (mRootView != null && !TextUtils.isEmpty(baseToken)) {
                                    SecondVerifyParams secondVerifyParams= new SecondVerifyParams();
                                    secondVerifyParams.setBaseToken(baseToken);
                                    secondVerifyParams.setVerifyType(VerifyTypeConstants.WITHDRAW);
                                    mRootView.onSecondVerify(secondVerifyParams);
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }
}
