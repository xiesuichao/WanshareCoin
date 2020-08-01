package com.wanshare.common.mvpdemo.view;

import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.sunfusheng.marqueeview.MarqueeView;
import com.wanshare.common.R;
import com.wanshare.common.R2;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.mvpdemo.contract.HomeContract;
import com.wanshare.common.mvpdemo.presenter.HomePresenter;

import java.util.List;

import butterknife.BindView;

/**
 * MVP demo 首页 ，View层实现
 * </br>
 * Date: 2018/7/21 17:45
 *
 * @author hemin
 */
public class HomeActivity extends BaseActivity<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R2.id.marqueeView)
    MarqueeView marqueeView;

    private String[] bigArray;

    @Override
    protected HomeContract.Presenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        fillNoticeList();

        //用来测试内存回收情况 ，无其他意义
        bigArray = new String[1024*100];
        for(int i=0;i<bigArray.length;i++){
            bigArray[i]="str"+i;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    private void fillNoticeList() {
        if (mPresenter != null) {
            mPresenter.getNoticeList();
        }
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    public void showNoticeList(final List<String> list) {

        marqueeView.startWithList(list);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(getApplicationContext(), list.get(position) + " at position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
