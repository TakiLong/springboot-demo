package com.wtl.base;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * mapper基础通用类
 *
 * @author wangtianlong 2019/3/19 16:14
 * @version 1.0
 */
public interface Mapper<T> extends BaseMapper<T>, ConditionMapper<T>,
        IdsMapper<T>, InsertListMapper<T> {

}
