package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.contract.BaseCodeContract;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;

public class PhoneStartContract {

    public interface View extends BaseCodeContract.View{
        void resultOpenPhone();
    }

    public interface Presenter extends BaseCodeContract.Presenter{

        void openPhone(OperationVerifyReq req);

    }

}
