package com.wanshare.common.mvp;

import android.support.annotation.NonNull;

/**
 * MVP中 View层的base接口
 * </br>
 * Date: 2018/7/21 11:06
 *
 * @author hemin
 */
public interface IView {
    /**
     * 显示加载中
     */
     void showLoading(String tips);

    /**
     * 隐藏加载中
     */
    void hideLoading();

    /**
     * 显示提示信息
     * @param message 消息内容, 不能为 {@code null}
     */
    void showHintMessage(@NonNull String message);

}
