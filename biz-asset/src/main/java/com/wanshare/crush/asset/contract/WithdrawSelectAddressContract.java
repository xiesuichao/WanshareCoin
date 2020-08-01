package com.wanshare.crush.asset.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.crush.asset.model.bean.Coin;
import com.wanshare.crush.asset.model.bean.GetWithdrawTotalBean;
import com.wanshare.crush.asset.model.bean.WithdrawAddress;

import java.util.List;

/**
 * 提币选择地址
 * </br>
 * Date: 2018/8/27 19:45
 *
 * @author hemin
 */
public class WithdrawSelectAddressContract {
    public interface Presenter extends IPresenter {

        /**
         * 获取提币地址列表
         * @param coinId
         */
        void getWithdrawAddresses(String coinId,int page);
    }

    public interface View extends IView {

        /**
         * 显示地址列表
         * @param result
         */
        void showWithdrawAddresses(WithdrawAddress result);
    }
}
