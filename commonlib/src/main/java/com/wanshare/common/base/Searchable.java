package com.wanshare.common.base;

/**
 * 搜索接口
 * </br>
 * Date: 2018/9/3 15:19
 *
 * @author hemin
 */
public interface Searchable {
    /**
     * 当输入的关键词变化时回调
     * @param keyword
     */
    void onSearch(String keyword);
}
