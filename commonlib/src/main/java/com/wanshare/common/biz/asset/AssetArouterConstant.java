package com.wanshare.common.biz.asset;

/**
 * 资产模块， 路由跳转的常量类
 * </br>
 * Date: 2018/8/18 15:57
 *
 * @author hemin
 */
public class AssetArouterConstant {
    private static final String BIZ_GROUP = "/asset/";

    public static final String WITHDRAW_ADDRESS_LIST = BIZ_GROUP + "withdrawal";
    public static final String WITHDRAW_ADDRESS_ADD = BIZ_GROUP + "wa_add";
    public static final String SCAN = BIZ_GROUP + "scan";
    public static final String ASSET_RECORD = BIZ_GROUP + "record";
    /**
     * 选择币种
     */
    public static final String ASSET_SELECT_COIN = BIZ_GROUP + "select_coin";
    /**
     * 充币选择币种
     */
    public static final String ASSET_RECHARGE_SELECT_COIN = BIZ_GROUP + "recharge/select_coin";
    /**
     * 提币选择币种
     */
    public static final String ASSET_WITHDRAW_SELECT_COIN = BIZ_GROUP + "withdraw/select_coin";

    /**
     * 充币
     */
    public static final String ASSET_RECHARGE_COIN = BIZ_GROUP + "recharge_coin";
    /**
     * 提币
     */
    public static final String ASSET_WITHDRAW_COIN = BIZ_GROUP + "withdraw_coin";
    /**
     * 提币选择地址
     */
    public static final String WITHDRAW_SELECT_ADDRESS = BIZ_GROUP + "withdraw_select_address";
    public static final String ASSET_RECORD_DETAIL = BIZ_GROUP + "record_detail";

    public static final String ASSET_TOTAL_ASSETS = BIZ_GROUP + "total_assets";

    public static final String ASSET_ASSETS_DETAIL = BIZ_GROUP + "assets_detail";
}
