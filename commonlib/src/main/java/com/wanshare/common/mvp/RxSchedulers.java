package com.wanshare.common.mvp;

import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装常用的Rx线程切换处理
 * </br>
 * Date: 2018/7/23 16:29
 *
 * @author hemin
 */
public class RxSchedulers {

    private static final String TAG= "log";

    /**
     * 封装线程切换和loading显示
     * @param mRootView IView
     * @param <T> 返回数据
     * @return ObservableTransformer
     */
    @NonNull
    public static <T> ObservableTransformer<T, T> applySchedulers(final IView mRootView) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                Log.d(TAG, "doOnSubscribe disposable: " + disposable);
                                Log.d(TAG, "accept: on thread:"+Thread.currentThread().getName());

                                // 显示loading
                                if (mRootView != null) {
                                    mRootView.showLoading("");
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread()) // 设置doOnSubscribe对应的线程为主线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                // 隐藏loading
                                if (mRootView != null) {
                                    mRootView.hideLoading();
                                }
                            }
                        });
            }
        };
    }

}
