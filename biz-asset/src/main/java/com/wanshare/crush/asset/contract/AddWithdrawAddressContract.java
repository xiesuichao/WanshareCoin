package com.wanshare.crush.asset.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.CoinInfo;

import java.util.List;

/**
 * @author wangdunwei
 * @date 2018/8/21
 */
public class AddWithdrawAddressContract {
    public interface Presenter extends IPresenter {
        /**
         * 添加提币地址
         *
         * @param coinId  币id
         * @param remark  备注
         * @param address 币地址
         */
        void addWithdrawAddress(String coinId, String remark, String address);

        /**
         * 获取可提币列表
         */
        void requestAvailableWithdrawCoinList();
    }

    public interface View extends IView {
        /**
         * 修改提币信息成功
         */
        void alterSucceed();

        /**
         * 添加提币地址成功
         */
        void addSucceed();

        /**
         * 获取到币名称列表的回调
         *
         * @param coinInfoList 币信息列表
         * @param strings      币名称列表
         */
        void onGotCoinNames(List<CoinInfo> coinInfoList, List<String> strings);
    }
}
