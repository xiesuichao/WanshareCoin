package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.SetLoginPasswordReq;

/**
 * Created by Richard on 2018/8/24
 * 修改密码
 */
public class EditLoginPasswordContract {
    public interface View extends IView {
        void showToast(int msgId);
        void onEditLoginPasswordSuccess();
    }

    public interface Presenter extends IPresenter {

        boolean checkout(String oldPassword,String newPassword, String confirmNewPassword);

        void setPassword(SetLoginPasswordReq req);
    }


}
