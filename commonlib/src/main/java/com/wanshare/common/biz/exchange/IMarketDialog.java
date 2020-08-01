package com.wanshare.common.biz.exchange;

import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * @author admin
 */
public interface IMarketDialog {
    void showMarketDialog(FragmentManager manager, View view, String tag);
    void hideMarketDialog();
    void setOnItemClick(OnMarketItemClickListener onItemClickListener);
}
