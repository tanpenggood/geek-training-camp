package com.itplh.web.context;

import java.util.List;

/**
 * 组件上下文
 *
 * @author: tanpenggood
 * @date: 2021-03-31 18:28
 */
public interface ComponentContext {

    /**
     * 上下文初始化
     * 生命周期方法
     */
    void init();

    /**
     * 上下文销毁方法
     * 生命周期方法
     */
    void destroy();

    /**
     * 通过名称查找组件对象
     * 组件操作方法
     *
     * @param name 组件名称
     * @param <C>  组件对象类型
     * @return 如果找不到返回, <code>null</code>
     */
    <C> C getComponent(String name);

    /**
     * 获取所有的组件名称
     * 组件操作方法
     *
     * @return
     */
    List<String> getComponentNames();

}
