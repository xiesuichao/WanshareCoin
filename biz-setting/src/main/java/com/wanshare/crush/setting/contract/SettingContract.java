package com.wanshare.crush.setting.contract;

import com.google.gson.JsonObject;
import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import java.util.List;

/**
 * 退出登录
 */
public class SettingContract {

    public interface View extends IView {
        void onLoginOutSuccess(JsonObject success);
        void onLoginOutError();
    }

    public interface Presenter extends IPresenter {
         void loginOut();
    }

}
