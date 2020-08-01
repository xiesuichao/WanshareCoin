package com.wanshare.crush.asset.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.CoinInfo;

import java.util.List;

/**
 * 选择币种
 * </br>
 * Date: 2018/8/25 11:49
 *
 * @author hemin
 */
public class SelectCoinContract {
    public interface Presenter extends IPresenter {
        /**
         * 获取可充币列表
         */
        void getCoinLists();
    }

    public interface View extends IView {
        void showCoinList(List<CoinInfo> resultList);
    }
}
