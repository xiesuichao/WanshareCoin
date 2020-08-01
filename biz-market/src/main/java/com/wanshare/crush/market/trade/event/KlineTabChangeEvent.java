package com.wanshare.crush.market.trade.event;

/**
 * k线tab变化
 * </br>
 * Date: 2018/9/18 18:24
 *
 * @author hemin
 */
public class KlineTabChangeEvent {
    int type;

    public KlineTabChangeEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
