package com.wanshare.common.biz.exchange;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * @author admin
 */
public class MarketFactory {

    String marketDialogImplName="com.wanshare.crush.exchange.view.dialog.MarketDialog";

    public MarketFactory(){

    }

    public void setMarketDialogName(String name){
        this.marketDialogImplName = name;
    }

    public  IMarketDialog createMarketDialog(Context context){
        return (IMarketDialog) Fragment.instantiate(context,marketDialogImplName);
    }

}
