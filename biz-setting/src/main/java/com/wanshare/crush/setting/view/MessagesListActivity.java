package com.wanshare.crush.setting.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.biz.setting.SettingArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.setting.R;
import com.wanshare.crush.setting.adapter.MessagesAdapter;

import java.util.ArrayList;

/**
 * Created by Jason on 2018/9/25.
 *
 * 消息列表界面
 */
@Route(path = SettingArouterConstant.SETTING_MESSAGES_LIST)
public class MessagesListActivity extends BaseListActivity {

    private MessagesAdapter mMessagesAdapter;
    private String mMessageTitle;

    @Override
    protected int getContentView() {
        return R.layout.layout_list;
    }

    @Override
    protected void initIntent() {
        super.initIntent();
        Intent intent = getIntent();
        if (null != intent) {
            mMessageTitle = intent.getStringExtra(IntentConstant.EXTRA_MESSAGES_TITLE);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mMyToolbar.setTitle(mMessageTitle);

        mMessagesAdapter = new MessagesAdapter(mContext);
        setAdapter(mMessagesAdapter);
        mMessagesAdapter.setOnMessagesListener(new MessagesAdapter.OnMessagesListener() {
            @Override
            public void onMessagesClick(View view,int postion) {
                showShortToast("加载消息详情HTML页面");

            }
        });
    }

    @Override
    protected void initData() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }
        updateData(list, 20);
    }
}
