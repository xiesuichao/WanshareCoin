package com.wanshare.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.wanshare.common.R;
import com.wanshare.common.mvp.BaseMVPFragment;
import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.wscomponent.dialog.CommonProgressDialog;
import com.wanshare.wscomponent.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * fragment基类，所有fragment必须继承此fragment
 */

public abstract class BaseFragment<P extends IPresenter> extends BaseMVPFragment<P> implements IView {
    private Unbinder mUnbinder;
    protected BaseActivity mActivity;
    /**
     * fragment生命周期标志位，表示fragment是否已经执行onViewCreated()方法
     */
    protected boolean isViewCreated = false;
    public View parentView;
    private CommonProgressDialog mProgressDialog;

    /**
     * 获取宿主Activity
     *
     * @return
     */
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        super.onCreate(savedInstanceState);
        initArguments(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        parentView = inflater.inflate(getContentView(), container, false);
        mUnbinder = ButterKnife.bind(this, parentView);
        initView();
        return parentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


    @Override
    public void showLoading(String tips) {
        if (null == mProgressDialog) {
            mProgressDialog = new CommonProgressDialog(mActivity, R.style.my_progress_dialog_theme);
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMsg(tips);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showHintMessage(@NonNull String message) {
        ToastUtil.showShort(mActivity, message);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    protected P getPresenter() {
        return null;
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected void initArguments(Bundle savedInstanceState) {

    }

}
