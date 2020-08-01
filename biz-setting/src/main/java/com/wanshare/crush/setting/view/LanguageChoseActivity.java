package com.wanshare.crush.setting.view;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.RecycleViewDivider;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.biz.setting.SettingArouterConstant;
import com.wanshare.crush.setting.R;
import com.wanshare.crush.setting.R2;
import com.wanshare.crush.setting.model.bean.LanguageBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2018/8/23.
 * <p>
 * 语言选择
 */

@Route(path = SettingArouterConstant.SETTING_LANGUAGE)
public class LanguageChoseActivity extends BaseListActivity {

    private int pageSize = 10;

    @Override
    protected void initData() {
        ArrayList<LanguageBean> data = new ArrayList<>();
        data.add(new LanguageBean("Englis", false));
        data.add(new LanguageBean("简体中文", true));
        data.add(new LanguageBean("日本語", false));

        updateData(data, pageSize);
    }

    @Override
    protected void initView() {
        super.initView();
        mMyToolbar.setTitle(getString(R.string.setting_language));
        setDown(false);
        setIsUp(false);

        final CommonAdapter<LanguageBean> adapter = new CommonAdapter<LanguageBean>(this, R.layout.setting_item_language_chose, new ArrayList<LanguageBean>()) {
            @Override
            protected void convert(ViewHolder holder, LanguageBean languageBean, int position) {
                holder.setText(R.id.tv_language, languageBean.getLanguage());

                holder.setVisible(R.id.iv_chose, languageBean.isChoseLanguage() ? View.VISIBLE : View.INVISIBLE);
            }

        };

        setAdapter(adapter);

        //设置分割线
        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 2,
                getResources().getColor(R.color.color_gray_light1));
        mLayoutRecycler.addItemDecoration(recycleViewDivider);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                List<LanguageBean> datas = adapter.getDatas();
                ArrayList<LanguageBean> languageList = setLanguage(datas, position);
                updateData(languageList, 10);
                choseLanguageSuccess(datas.get(position));
            }
        });
    }

    /**
     * 语言选择成功后的操作
     *
     * @param languageBean
     */
    private void choseLanguageSuccess(LanguageBean languageBean) {
        EventBus.getDefault().post(languageBean);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    /**
     * 语言设置
     *
     * @param datas    语言数据集合
     * @param position
     * @return
     */
    private ArrayList<LanguageBean> setLanguage(List<LanguageBean> datas, int position) {
        ArrayList<LanguageBean> languageList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (position == i) {
                LanguageBean choseLanguage = datas.get(i);
                choseLanguage.setChoseLanguage(true);
                languageList.add(choseLanguage);
            } else {
                LanguageBean otherLanguage = datas.get(i);
                otherLanguage.setChoseLanguage(false);
                languageList.add(otherLanguage);
            }
        }
        return languageList;
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_language_chose;
    }

}
