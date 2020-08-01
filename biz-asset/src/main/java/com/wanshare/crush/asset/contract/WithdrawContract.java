package com.wanshare.crush.asset.contract;

import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.model.bean.GetWithdrawTotalBean;
import com.wanshare.crush.asset.model.bean.RechargeAddressBean;
import com.wanshare.crush.asset.model.bean.WithdrawReqBean;

/**
 * 提币
 * </br>
 * Date: 2018/8/25 18:03
 *
 * @author hemin
 */
public class WithdrawContract {
    public interface Presenter extends IPresenter {
        /**
         * 获取对应币种的资产信息
         *
         * @param coinId 币种id
         */
        void getAssetsInfo(String coinId);

        /**
         * 获取当日提币总数
         * @param coinId
         */
        void getWithdrawTotalToday(String coinId);

        /**
         *  币种详情信息
         * @param coinId
         */
        void getCoinInfo(String coinId);

        /**
         * 提币
         * @param reqBean
         */
        void doWithdraw(WithdrawReqBean reqBean);
    }

    public interface View extends IView {
        /**
         * 显示资产信息
         *
         * @param result
         */
        void showAssetInfo(AssetByCoinBean result);

        /**
         * 显示已提币数量
         * @param result
         */
        void showWithdrawTotalToday(GetWithdrawTotalBean result);

        /**
         * 提币成功
         */
        void onWithdrawSuccess();

        /**
         * 显示币种详情
         * @param coinInfo
         */
        void showCoinInfo(CoinInfo coinInfo);

        /**
         * 二次验证
         * @param
         */
        void onSecondVerify(SecondVerifyParams secondVerifyParams);
    }
}
