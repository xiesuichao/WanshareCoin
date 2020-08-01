package com.wanshare.crush.asset.presenter;

import com.google.gson.Gson;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.ErrorHandleSingleObserver;
import com.wanshare.crush.asset.contract.AddWithdrawAddressContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.AssetReq;
import com.wanshare.crush.asset.model.bean.CoinInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * @author wangdunwei
 * @date 2018/8/21
 */
public class AddWithdrawAddressPresenter extends BasePresenter<AddWithdrawAddressContract.View>
        implements AddWithdrawAddressContract.Presenter {
    private AssetServer assetServer;

    public AddWithdrawAddressPresenter(AddWithdrawAddressContract.View view) {
        super(view);
        assetServer = ApiClient.getInstance().create(AssetServer.class);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void addWithdrawAddress(String coinId, String remark, String address) {
        RequestBody requestBody = RequestBody
                .create(MediaType.parse("application/json"), new Gson().toJson(new AssetReq(coinId, remark, address)));
        assetServer.addWithdrawAddress(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object coins) {
                        super.onNext(coins);
                        mRootView.addSucceed();
                    }
                });
    }

    @Override
    public void requestAvailableWithdrawCoinList() {
        assetServer.getAvailableWithdrawCoinList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<List<CoinInfo>>(this) {
                    @Override
                    public void onNext(List<CoinInfo> coinInfos) {
                        super.onNext(coinInfos);
                        getCoinNames(coinInfos);
                    }
                });
    }

    private void getCoinNames(final List<CoinInfo> coinInfoList) {
        Observable.fromIterable(coinInfoList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CoinInfo, String>() {
                    @Override
                    public String apply(CoinInfo coinInfo) {
                        return coinInfo.getShortName();
                    }
                })
                .toList()
                .subscribe(new ErrorHandleSingleObserver<List<String>>(this) {
                    @Override
                    public void onSuccess(List<String> strings) {
                        mRootView.onGotCoinNames(coinInfoList, strings);
                    }
                });
    }

}
