package com.wanshare.common.base;

import android.os.Bundle;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.wscomponent.websocket.IWebSocketPage;
import com.wanshare.wscomponent.websocket.WebSocketServiceConnectManager;

public abstract class WebSocketBaseActivity<P extends IPresenter> extends BaseActivity<P> implements IWebSocketPage {

    private WebSocketServiceConnectManager mConnectManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConnectManager = new WebSocketServiceConnectManager(this, this);
        mConnectManager.onCreate();
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
    protected void onDestroy() {
        mConnectManager.onDestroy();
        super.onDestroy();
    }
}
