package com.wanshare.common.mvp;

import android.util.Log;

import com.wanshare.wscomponent.logger.LogUtil;

import org.json.JSONObject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * SingleObserver订阅
 *
 * @author wangdunwei
 */
public abstract class ErrorHandleSingleObserver<T> implements SingleObserver<T> {
    private static final String TAG = "log";
    private BasePresenter mPresenter;

    public ErrorHandleSingleObserver(BasePresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if(mPresenter != null) {
            // 在订阅时必须调用这个方法,不然Activity退出时可能内存泄漏
            mPresenter.addDispose(d);
            Log.d(TAG, "onSubscribe: " + d);
            Log.d(TAG, "onSubscribe: on thread:" + Thread.currentThread().getName());
        }
    }

    @Override
    public void onError(Throwable e) {
        Throwable protocolException = e;
        try {
            if(e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                int code = httpException.code();
                if(code == ProtocolException.CODE_401) {
                    String bodyStr = httpException.response().errorBody().string();
                    protocolException = new ProtocolException(code, bodyStr);
                } else {
                    String bodyStr = httpException.response().errorBody().string();
                    String message = new JSONObject(bodyStr).getString("message");
                    protocolException = new ProtocolException(code, message);
                }
            }
        } catch(Exception ex) {
            LogUtil.ex(ex);
        }

        if(mPresenter != null) {
            mPresenter.handleResponseError(protocolException);
        }
    }
}
