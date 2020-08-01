package com.wanshare.crush.account.contract;

import com.wanshare.crush.account.model.bean.BindPhoneReq;
import com.wanshare.common.biz.account.model.SecondVerifyParams;

public class PhoneBindingContract {

    public interface View extends BaseCodeContract.View {

        void onBindPhoneResult();

        void resultBindSecond(SecondVerifyParams params);
    }

    public interface Presenter extends BaseCodeContract.Presenter {

        void bindPhone(BindPhoneReq req);
    }


}
