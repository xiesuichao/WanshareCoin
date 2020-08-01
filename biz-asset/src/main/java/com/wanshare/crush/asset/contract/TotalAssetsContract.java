package com.wanshare.crush.asset.contract;

import android.os.Parcelable;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;

import java.util.List;

/**
 * Created by Richard on 2018/8/27
 */
public class TotalAssetsContract {


    public interface View extends IView {

        void onRefreshResult(List<Parcelable> list, boolean hasMore);

        void onLoadMoreResult(List<Parcelable> list, boolean hasMore);

        void showToast(int msgId);

        void showToast(String msg);

    }

    public interface Presenter extends IPresenter {

        void refresh();

        void loadMore();
    }

}
