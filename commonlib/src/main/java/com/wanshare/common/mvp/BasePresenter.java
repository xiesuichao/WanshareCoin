package com.wanshare.common.mvp;


import android.app.Activity;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;

import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.wscomponent.utils.Preconditions;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * MVP Presenter 基类
 * </br>
 * Date: 2018/7/21 11:36
 *
 * @author hemin
 */
public class BasePresenter<V extends IView> implements IPresenter, ResponseErrorListener {
    private static final String TAG = BasePresenter.class.getSimpleName();
    protected V mRootView;

    protected CompositeDisposable mCompositeDisposable;

    public BasePresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        unDispose();
        this.mRootView = null;
        mCompositeDisposable = null;
        Log.d(TAG, "onDestroy: ");
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {@link Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     *
     * @param disposable disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            Log.d(TAG, "unDispose: " + mCompositeDisposable);
            Log.d(TAG, "unDispose: on thread:" + Thread.currentThread().getName());
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

    @Override
    public void handleResponseError(Throwable e) {
        if (e instanceof ProtocolException) {
            ProtocolException exception = (ProtocolException) e;
            if (ProtocolException.CODE_403 == exception.getErrorCode()) {
                // token异常，访问禁止
                UserInfoManager.getInstance().clearUserInfo();
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation();
                return;
            }else if(ProtocolException.CODE_401 == exception.getErrorCode()){
                // TODO TASK: 2018/9/12 跳转到二次验证
                return;
            }
        }

        if (mRootView == null) {
            return;
        }

        Log.d(TAG, "onError: " + e.getMessage());
        Log.d(TAG, "onError: on thread:" + Thread.currentThread().getName());
        // 错误提示
        mRootView.showHintMessage(e.getMessage());
    }
}
