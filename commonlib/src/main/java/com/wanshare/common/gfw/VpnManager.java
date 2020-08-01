package com.wanshare.common.gfw;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;

public class VpnManager extends BasePresenter{

    //获取vps地址信息的url
    private static final String mAddressUrl = "https://gist.githubusercontent.com/wanshare8888/a57d10b67f0c4036446a138d1f80506e/raw/a08dd267ff1ae6de6326fa9707cea7f3627b8a43/iplist.json";

    private static VpnManager mInstance;

    public static VpnManager getInstance(){
        if (mInstance == null)
            mInstance = new VpnManager();

        return mInstance;
    }


    public void getAddress(){
        ApiClient.getInstance().create(VpnServer.class).getVpnAddress(mAddressUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<VpnAddress>(this) {
                    @Override
                    public void onNext(VpnAddress entrustsOrder) {
                        super.onNext(entrustsOrder);

                    }
                });
    }

    public void putAddress(){

        ReqAddress reqAddress = new ReqAddress();
        reqAddress.setSuccess("192.168.83.68,192.168.83.66");
        reqAddress.setFailure("192.168.83.61,192.168.83.60");
        ApiClient.getInstance().create(VpnServer.class).putVpsAddress(BaseRequestBody.create(reqAddress))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);

                    }
                });
    }
}
