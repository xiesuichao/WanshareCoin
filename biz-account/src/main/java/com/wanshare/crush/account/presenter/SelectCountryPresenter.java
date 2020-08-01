package com.wanshare.crush.account.presenter;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.account.contract.SelectContryContract;
import com.wanshare.crush.account.model.bean.Country;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author huyuqi
 * @date 2018/8/27
 */
public class SelectCountryPresenter extends BasePresenter<SelectContryContract.View> implements SelectContryContract.Presenter {

    public SelectCountryPresenter(SelectContryContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getCountryList(final AssetManager am) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                byte[] bytes = new byte[0];
                InputStream inputStream = null;
                try {
                    inputStream = am.open("country");
                    bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            emitter.onError(e);
                        }
                    }
                }
                emitter.onNext(new String(bytes));
            }
        }).map(new Function<String, List<Country>>() {
            @Override
            public List<Country> apply(String s) {
                return new Gson().fromJson(s, new TypeToken<List<Country>>() {
                }.getType());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleObserver<List<Country>>(this) {
                    @Override
                    public void onNext(final List<Country> countries) {
                        super.onNext(countries);
                        if (mRootView != null) {
                            mRootView.showCountryList(countries);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mRootView != null) {
                            mRootView.getCountryListFailed();
                        }
                    }
                });
    }
}
