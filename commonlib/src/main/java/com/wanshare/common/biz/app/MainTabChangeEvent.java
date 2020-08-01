package com.wanshare.common.biz.app;

/**
 * MainActivity中tab切换的event
 * </br>
 * Date: 2018/9/5 10:16
 *
 * @author hemin
 */
public class MainTabChangeEvent {
    public static final int POSITON_HOME = 0;
    public static final int POSITON_FAVORITE = 1;
    public static final int POSITON_EXCHANGE = 2;
    public static final int POSITON_ORDER = 3;
    public static final int POSITON_MINE = 4;

    private int mPosition;

    public int getPosition() {
        return mPosition;
    }

    public MainTabChangeEvent(int position) {
        this.mPosition = position;
    }

}
