package com.wanshare.crush.asset.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.model.bean.RechargeAddressBean;

/**
 * 充币
 * </br>
 * Date: 2018/8/25 14:54
 *
 * @author hemin
 */
public class RechargeContract {
   public interface Presenter extends IPresenter {
        /**
         * 获取对应币种的充币地址
         * @param coinId 币种id
         */
        void getRechargeAddress(String coinId);

       /**
        *  币种详情信息
        * @param coinId
        */
        void getCoinInfo(String coinId);
    }

   public interface View extends IView {
        /**
         * 显示充币地址
         * @param result
         */
        void showRechargeAddress(RechargeAddressBean result);

       /**
        * 显示币种详情
        * @param coinInfo
        */
       void showCoinInfo(CoinInfo coinInfo);
    }
}
