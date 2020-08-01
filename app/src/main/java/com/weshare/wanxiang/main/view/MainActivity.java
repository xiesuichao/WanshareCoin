package com.weshare.wanxiang.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.biz.account.model.LoginEvent;
import com.wanshare.common.biz.app.AppArouterConstant;
import com.wanshare.common.biz.app.MainTabChangeEvent;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.common.widget.ViewPagerFixed;
import com.wanshare.common.widget.viewpager.CommonFragmentPagerAdapter;
import com.wanshare.crush.exchange.view.fragment.ExchangeFragment;
import com.wanshare.crush.favorite.view.fragment.FavoriteFragment;
import com.wanshare.crush.market.order.view.EntrustsOrderFragment;
import com.wanshare.wscomponent.gfw.core.AppProxyManager;
import com.wanshare.wscomponent.gfw.core.LocalVpnService;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.update.dialog.BaseDialog;
import com.wanshare.wscomponent.update.dialog.IDialogListener;
import com.wanshare.wscomponent.update.dialog.IUpdateDialog;
import com.wanshare.wscomponent.update.model.VersionEntity;
import com.wanshare.wscomponent.update.update.UpdateCheckManager;
import com.weshare.wanxiang.R;
import com.weshare.wanxiang.main.contract.MainContract;
import com.weshare.wanxiang.main.presenter.MainActivityPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppArouterConstant.APP_MAIN)
public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    private static final String KEY_POSITION = "key_position";
    @BindView(R.id.vp_pages)
    ViewPagerFixed mViewPagerPages;

    private static final int POSITION_FAVORITE = 1;
    private static final int POSITION_ENTRUST = 3;
    private static final int POSITION_MINE = 4;

    private FragmentPagerAdapter mPagerAdapter;

    private SparseArray<Integer> idPositionMap = new SparseArray<>();

    List<View> mImageViewList = new ArrayList<>();
    List<View> mTextViewList = new ArrayList<>();

    private static final int START_VPN_SERVICE_REQUEST_CODE = 2000;

    private int currentPosition = 0;
    private long mLastPressBackTime;
    private UpdateCheckManager mUpdateCheckManager;

    @Override
    protected MainContract.Presenter getPresenter() {
        return new MainActivityPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_POSITION, 0);
        }
        super.onCreate(savedInstanceState);
        LogUtil.d("leon= MainActivity onCreate");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void initView() {

//        initOverGFW();

        initEvent();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomepageFragment.newInstance());
        fragments.add(FavoriteFragment.newInstance());
        fragments.add(new ExchangeFragment());
        fragments.add(EntrustsOrderFragment.newInstance());
        fragments.add(MinepageFragment.newInstance());
        mPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPagerPages.setAdapter(mPagerAdapter);
        mViewPagerPages.setOffscreenPageLimit(mPagerAdapter.getCount());

        Integer[] ids = new Integer[]{R.id.rl_home_tab, R.id.rl_favorite_tab, R.id.rl_exchange_tab, R.id.rl_entrust_tab, R.id.rl_mine_tab};
        Integer[] ivIds = new Integer[]{R.id.iv_home_tab, R.id.iv_favorite_tab, R.id.iv_exchange_tab, R.id.iv_entrust_tab, R.id.iv_mine_tab};
        Integer[] tvIds = new Integer[]{R.id.tv_home_tab, R.id.tv_favorite_tab, R.id.tv_exchange_tab, R.id.tv_entrust_tab, R.id.tv_mine_tab};
        idPositionMap.clear();
        for (int i = 0; i < ids.length; i++) {
            idPositionMap.put(ids[i], i);
        }
        mImageViewList.clear();
        for (int i = 0; i < ivIds.length; i++) {
            mImageViewList.add(findViewById(ivIds[i]));
        }
        mTextViewList.clear();
        for (int i = 0; i < tvIds.length; i++) {
            mTextViewList.add(findViewById(tvIds[i]));
        }
        selectTab(currentPosition);
    }

    @OnClick({R.id.rl_home_tab, R.id.rl_favorite_tab, R.id.rl_exchange_tab, R.id.rl_entrust_tab, R.id.rl_mine_tab})
    public void onViewClicked(View v) {
        int id = v.getId();

        Integer position = idPositionMap.get(id);
        if (needLogin(position) && !UserInfoManager.getInstance().isLogin()) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(getContext());
            return;
        }
        changeTab(position);
    }

    private void changeTab(Integer position) {
        if (position != null && currentPosition != position) {
            unselectTab(currentPosition);
            currentPosition = position;
            selectTab(currentPosition);
        }
    }

    private void unselectTab(Integer position) {
        mImageViewList.get(position).setSelected(false);
        mTextViewList.get(position).setSelected(false);
    }

    private void selectTab(Integer position) {
        mImageViewList.get(position).setSelected(true);
        mTextViewList.get(position).setSelected(true);
        mViewPagerPages.setCurrentItem(position, false);
    }

    private boolean needLogin(Integer position) {
        return position == POSITION_FAVORITE || position == POSITION_ENTRUST;
    }

    @Override
    protected void initData() {
        mPresenter.initData();
        initUpdate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MainTabChangeEvent event) {
        if (event != null && mPagerAdapter != null && mViewPagerPages != null && event.getPosition() >= 0 && event.getPosition() <= mPagerAdapter.getCount()) {
            changeTab(event.getPosition());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent event) {
        if (mPagerAdapter != null && event.isLoginSuccess()) {
            mPagerAdapter.getItem(POSITION_MINE).onActivityResult(IntentConstant.REQUEST_CODE_MINE, RESULT_OK, null);
            mPresenter.getUserInfo();
        }
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == START_VPN_SERVICE_REQUEST_CODE && resultCode == RESULT_OK) {
            startVPNService();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 启动翻墻
     */
    private void initOverGFW() {

        Intent intent = LocalVpnService.prepare(this);

        if (intent == null) {
            startVPNService();
        } else {
            startActivityForResult(intent, START_VPN_SERVICE_REQUEST_CODE);
        }


    }

    private void startVPNService() {

        if (AppProxyManager.isLollipopOrAbove) {
            new AppProxyManager(this);
        }

        LocalVpnService.ProxyUrl = "ss://YWVzLTI1Ni1jZmI6OU1MU3BQbU50QDEzLjI1MC45LjE1MTozNTUyNw==";
        startService(new Intent(this, LocalVpnService.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_POSITION, currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastPressBackTime > 1000) {
            showShortToast(getString(R.string.app_back_press_hint));
            mLastPressBackTime = System.currentTimeMillis();
            return;
        }

        super.onBackPressed();
    }

    private void initUpdate(){
        mUpdateCheckManager = new UpdateCheckManager.Builder()
                .setNotifyContent("")
                .setNotifyIcon(R.mipmap.app_ic_launcher)
                .setNotifyTitle(getString(R.string.app_name))
                .setNotifyComplete(getString(R.string.common_download_finish))
                .setNotifyCompleteInstall(getString(R.string.common_download_install))
                .build(mContext);
        mUpdateCheckManager.setIUpdateDialog(new UpdateDialogImp());
        mUpdateCheckManager.autoUpdate(new VersionEntity("1.0.2", "http://192.168.83.72:8080/job/android.wanshare.release.deploy/ws/app/build/outputs/apk/debug/Wanshare_v3.0.0_2018-10-05_300D_jiagu_sign.apk", "false", "1.全新配色更改，页面优化设计，权限ui设计\n" +
                "2.全新配色更改，页面优化设计，权限ui设计\n" +
                "3.全新配色更改，页面优化设计，权限ui设计", "18946638"));
    }

    private class UpdateDialogImp extends IUpdateDialog.SubUpdateDialog {
        private UpdateDialog mUpdateTipsDialog;

        @Override
        public BaseDialog getProgressDialog(int progress, VersionEntity entity) {
            initUpdateDialog(entity);
            if (progress > 0) {
                mUpdateTipsDialog.setPbUpdateProgress(progress);
            }
            return mUpdateTipsDialog;
        }

        @Override
        public BaseDialog getUpdateTipsDialog(VersionEntity entity) {
            initUpdateDialog(entity);
            return mUpdateTipsDialog;
        }

        private void initUpdateDialog(VersionEntity entity){
            if (mUpdateTipsDialog == null) {
                mUpdateTipsDialog = getUpdateDialog(entity);
            }
        }

    }

    private UpdateDialog getUpdateDialog(VersionEntity entity) {
        return new UpdateDialog.Builder()
                .setContent(entity.getDescribe())
                .setForcibly(entity.getIsCompel())
                .setVersion("1.0.4")
                .setIDialogListener(new IDialogListener.SubDialogListener() {
                    @Override
                    public void confirm() {
                        mUpdateCheckManager.startUpdate();
                    }
                })
                .create(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUpdateCheckManager != null) {
            mUpdateCheckManager.destroy();
        }
    }
}