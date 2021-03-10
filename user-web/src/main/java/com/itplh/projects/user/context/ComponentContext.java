package com.itplh.projects.user.context;

import com.itplh.projects.user.web.listener.ComponentContextInitializerListener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import java.util.NoSuchElementException;

/**
 * 组件上下文（Web 应用全局使用）
 * <p>
 * 初始化ComponentContext {@link ComponentContextInitializerListener}
 *
 * @author: tanpenggood
 * @date: 2021-03-10 01:22
 */
public class ComponentContext {

    public static final String CONTEXT_NAME = ComponentContext.class.getName();

    /**
     * 请注意
     * 假设一个 Tomcat JVM 进程，三个 Web Apps，servletContext会不会相互冲突？（不会冲突）
     * static 字段是 JVM 缓存吗？（是 ClassLoader 缓存）
     */
    private static ServletContext servletContext;

    /**
     * 获取ComponentContext
     *
     * @return
     */
    public static ComponentContext getInstance() {
        return ((ComponentContext) servletContext.getAttribute(CONTEXT_NAME));
    }

    private Context context;

    public void init(ServletContext servletContext) throws RuntimeException {
        servletContext.setAttribute(CONTEXT_NAME, this);
        ComponentContext.servletContext = servletContext;

        try {
            context = ((Context) new InitialContext().lookup("java:comp/env"));
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过名称进行依赖查找
     *
     * @param name
     * @param <C>
     * @return
     */
    public <C> C getComponent(String name) {
        C component = null;
        try {
            component = (C) context.lookup(name);
        } catch (NamingException e) {
            throw new NoSuchElementException(name);
        }
        return component;
    }

    public void destroy() {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
