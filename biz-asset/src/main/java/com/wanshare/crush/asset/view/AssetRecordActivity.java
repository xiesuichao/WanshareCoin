package com.wanshare.crush.asset.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.mvp.BaseMVPFragment;
import com.wanshare.common.widget.viewpager.ViewPagerIndicator;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.view.fragment.RechargeRecordFragment;
import com.wanshare.crush.asset.view.fragment.WithdrawRecordFragment;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * 财务记录入口页
 *
 * @author wangdunwei
 */
@Route(path = AssetArouterConstant.ASSET_RECORD)
public class AssetRecordActivity extends BaseActivity {
    @BindView(R2.id.vpi_asset)
    ViewPagerIndicator vpiAsset;
    @BindView(R2.id.vp_asset)
    ViewPager vpAsset;

    private BaseMVPFragment fragments[] =
            {
                    new RechargeRecordFragment(),
                    new WithdrawRecordFragment()
            };

    @BindArray(R2.array.asset_record_titles)
    String[] assetRecordTitles;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.asset_record);

        vpAsset.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return assetRecordTitles[position];
            }
        });
        vpiAsset.setTitles(assetRecordTitles, vpAsset);
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_record_activity;
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }
}
