package com.wtl.base;

import org.springframework.aop.framework.AopContext;

/**
 * 获取代理对象本身
 * 遇到的问题：如果你想在你的代理方法中以 this 调用当前接口的另一个方法，另一个方法的事务是不会起作用的
 * 如何使用：Service接口只需要继承该接口，T为接口本身即可，就可以通过self()获取自身的代理对象了
 * 使用注意：在类上添加 @EnableAspectJAutoProxy(exproseProxy=true)
 *
 * @author wangtianlong 2019/3/20 8:58
 * @version 1.0
 */
public interface ProxySelf<T> {
    /**
     * 取得当前对象的代理.
     *
     * @return 代理对象,如果未被代理,则抛出 IllegalStateException
     */
    @SuppressWarnings("unchecked")
    default T self() {
        return (T) AopContext.currentProxy();
    }
}
