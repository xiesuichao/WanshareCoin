package com.wanshare.crush.asset.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.WithdrawAddress;

/**
 * @author wangdunwei
 * @date 2018/8/20
 */
public class WithdrawAddressListContract {

    public interface Presenter extends IPresenter {
        /**
         * 获取提现地址
         *
         * @param coinId 币id
         * @param page   当前页
         */
        void requestWithdrawAddressList(String coinId, int page);

        /**
         * 删除提币地址
         *
         * @param id 币id
         */
        void deleteWithdrawAddress(String id);
    }

    public interface View extends IView {
        /**
         * 显示提币地址列表
         *
         * @param withdrawAddress 提币地址信息
         */
        void showWithdrawAddressList(WithdrawAddress withdrawAddress);
        void onDeleteAddressSucceed();
    }
}
