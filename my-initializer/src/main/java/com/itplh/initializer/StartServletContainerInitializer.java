package com.itplh.initializer;

import com.itplh.initializer.annotation.Order;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author: tanpenggood
 * @date: 2021-03-24 00:57
 */
@HandlesTypes(MyApplicationInitializer.class)
public class StartServletContainerInitializer implements ServletContainerInitializer {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void onStartup(Set<Class<?>> initializerSet, ServletContext servletContext) throws ServletException {
        if (Objects.nonNull(initializerSet) && !initializerSet.isEmpty()) {
            List<Class<?>> initializers = initializerSet.stream()
                    .filter(c -> {
                        // 是否为{@link MyApplicationInitializer}的普通实现类（非接口，非抽象类）
                        boolean isCommonImplementsClass = MyApplicationInitializer.class.isAssignableFrom(c) && !c.isInterface() && !Modifier.isAbstract(c.getModifiers());
                        return isCommonImplementsClass;
                    })
                    // 按照{@link Order}注解排序，value值越小越先执行，越大越后执行
                    .sorted(orderComparator)
                    .collect(Collectors.toList());
            // 按顺序调用{@link MyApplicationInitializer}实现类的onStartup方法
            initializers.forEach(c -> {
                try {
                    MyApplicationInitializer webApplication = (MyApplicationInitializer) c.newInstance();
                    webApplication.onStartup(servletContext);
                } catch (InstantiationException | IllegalAccessException | ServletException e) {
                    logger.log(Level.SEVERE, String.format("%s invoke onStartup fail.", c.getName()), e);
                }
            });
        }
    }

    private Comparator<Class> orderComparator = Comparator.comparingInt(this::getOrderValue);

    private Integer getOrderValue(Class<?> initializer) {
        Order annotation = initializer.getAnnotation(Order.class);
        return Objects.isNull(annotation) ? 0 : annotation.value();
    }
}
