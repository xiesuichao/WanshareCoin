package com.wanshare.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.wscomponent.websocket.ErrorResponse;
import com.wanshare.wscomponent.websocket.IWebSocketPage;
import com.wanshare.wscomponent.websocket.Response;
import com.wanshare.wscomponent.websocket.WebSocketServiceConnectManager;

public abstract class WebSocketBaseFragment<P extends IPresenter> extends BaseFragment<P> implements IWebSocketPage {

    private WebSocketServiceConnectManager mConnectManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mConnectManager = new WebSocketServiceConnectManager(mActivity, this);
        mConnectManager.onCreate();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onServiceBindSuccess() {

    }

    @Override
    public void sendText(String text) {
        mConnectManager.sendText(text);
    }

    @Override
    public void reconnect() {
        mConnectManager.reconnect();
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectError(Throwable cause) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onDestroyView() {
        mConnectManager.onDestroy();
        super.onDestroyView();
    }
}
