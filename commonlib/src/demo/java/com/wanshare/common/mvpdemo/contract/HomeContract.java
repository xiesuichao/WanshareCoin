package com.wanshare.common.mvpdemo.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;

import java.util.List;

/**
 * 首页
 * </br>
 * Date: 2018/7/21 17:46
 *
 * @author hemin
 */
public class HomeContract {

    public interface View extends IView{

        void showNoticeList(List<String> notices);
    }

    public interface Presenter extends IPresenter {
         void getNoticeList();
    }

}
