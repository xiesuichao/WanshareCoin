package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.SetAssetPasswordReq;

/**
 * Created by Richard on 2018/8/25
 */
public class EditAssetPasswordContract {
    public interface View extends IView {
        void showToast(int msgId);
        void onEditAssetPasswordSuccess();
    }

    public interface Presenter extends IPresenter {

        boolean checkout(String loginPassword, String assetPassword, String confirmAssetPassword);
        void editAssetPassword(SetAssetPasswordReq setAssetPasswordReq);
    }
}
