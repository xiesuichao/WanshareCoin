package com.weshare.wanxiang.main.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;

/**
 * MainActivity 对应 Contract
 * </br>
 * Date: 2018/9/12 15:38
 *
 * @author hemin
 */
public class MainContract {

    public interface View extends IView {

    }

    public interface Presenter extends IPresenter {
        /**
         * 初始化数据，获取用户信息等
         */
      void initData();

        /**
         * 获取用户信息
         */
      void getUserInfo();
    }

}
