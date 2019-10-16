package com.wtl.base;

/**
 * 基础枚举接口
 *
 * @version 1.0
 * @author wangtianlong 2019-03-19
 */
public interface BaseEnum<K, V> {

    /**
     * 获取编码
     *
     * @return 编码
     */
    K code();

    /**
     * 获取描述
     *
     * @return 描述
     */
    V desc();

}
