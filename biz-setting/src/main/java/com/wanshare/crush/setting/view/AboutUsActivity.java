package com.wanshare.crush.setting.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.RecycleViewDivider;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.biz.setting.SettingArouterConstant;
import com.wanshare.common.constant.CommonArouterCanstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.setting.R;
import com.wanshare.crush.setting.R2;
import com.wanshare.crush.setting.model.bean.LanguageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/8/23.
 * <p>
 * 关于我们
 */
@Route(path = SettingArouterConstant.SETTING_ABOUT)
public class AboutUsActivity extends BaseActivity {
    @BindView(R2.id.tv_version_info)
    TextView mTvVersionInfo;
    @BindView(R2.id.rvAboutUs)
    RecyclerView mRvAboutUs;
    private CommonAdapter<String> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(getString(R.string.setting_about_us));

        mAdapter = new CommonAdapter<String>(this, R.layout.setting_item_about_us, new ArrayList<String>()) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_about_us, s);
            }

        };

        mRvAboutUs.setLayoutManager(new LinearLayoutManager(this));
        mRvAboutUs.setAdapter(mAdapter);

        //设置分割线
        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 2,
                getResources().getColor(R.color.color_gray_light1));
        mRvAboutUs.addItemDecoration(recycleViewDivider);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }
        });
    }

    @Override
    protected void initData() {

        ArrayList<String> data = new ArrayList<>();
        data.add("公司团队");
        data.add("核心特点");
        data.add("联系我们");
        data.add("费率说明");

        mAdapter.updateList(data);
    }

    @OnClick({R2.id.tv_privacy_policy, R2.id.tv_service_protocol})
    public void onViewClicked(View view) {
        int id = view.getId();
        //隐私政策
        if (id == R.id.tv_privacy_policy) {
            ARouter.getInstance().build(CommonArouterCanstant.COMMON_WEBVIEW).withString(IntentConstant.WEB_URL, "https:www.baidu.com").navigation(this);

        //服务协议
        } else if (id == R.id.tv_service_protocol) {
            ARouter.getInstance().build(CommonArouterCanstant.COMMON_WEBVIEW).withString(IntentConstant.WEB_URL, "https:www.baidu.com").navigation(this);
        }
    }
}
