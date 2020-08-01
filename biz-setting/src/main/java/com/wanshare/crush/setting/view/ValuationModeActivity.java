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
import com.wanshare.crush.setting.model.bean.ValuationBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2018/8/23.
 * <p>
 * 设置计价方式
 */

@Route(path = SettingArouterConstant.SETTING_VALUATION)
public class ValuationModeActivity extends BaseListActivity {

    private int pageSize = 10;

    @Override
    protected int getContentView() {
        return R.layout.activity_valuation_mode;
    }

    @Override
    protected void initView() {
        super.initView();
        mMyToolbar.setTitle(getString(R.string.setting_valuation_mode));
        setDown(false);
        setIsUp(false);

        final CommonAdapter<ValuationBean> adapter = new CommonAdapter<ValuationBean>(this, R.layout.setting_item_valuation_chose, new ArrayList<ValuationBean>()) {
            @Override
            protected void convert(ViewHolder holder, ValuationBean valuationBean, int position) {
                holder.setText(R.id.tv_valuation, valuationBean.getValuation()+" "+valuationBean.getShortening());

                holder.setVisible(R.id.iv_chose, valuationBean.isChoseValuation() ? View.VISIBLE : View.INVISIBLE);
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

                List<ValuationBean> datas = adapter.getDatas();
                ArrayList<ValuationBean> valuationList = setValuation(datas, position);
                updateData(valuationList, pageSize);
                choseValuationSuccess(datas.get(position));
            }
        });
    }


    @Override
    protected void initData() {
        ArrayList<ValuationBean> data = new ArrayList<>();
        data.add(new ValuationBean("人民币", "（CNY）", true));
        data.add(new ValuationBean("美元", "（USD）", false));
        data.add(new ValuationBean("韩元", "（KRW）", false));
        data.add(new ValuationBean("日元", "（JPY）", false));
        data.add(new ValuationBean("欧元", "（EUR）", false));
        data.add(new ValuationBean("卢布", "（RUB）", false));
        data.add(new ValuationBean("英镑", "（GBP）", false));

        updateData(data, 10);
    }

    /**
     * 选择计价模式成功后的操作
     * @param valuationBean
     */
    private void choseValuationSuccess(ValuationBean valuationBean) {
        EventBus.getDefault().post(valuationBean);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    /**
     * 设置计价模式
     * @param datas
     * @param position
     * @return
     */
    private ArrayList<ValuationBean> setValuation(List<ValuationBean> datas, int position) {
        ArrayList<ValuationBean> valuationList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (position == i) {
                ValuationBean choseValuation = datas.get(i);
                choseValuation.setChoseValuation(true);
                valuationList.add(choseValuation);
            } else {
                ValuationBean otherValuation = datas.get(i);
                otherValuation.setChoseValuation(false);
                valuationList.add(otherValuation);
            }
        }
        return valuationList;
    }
}
