package com.itplh.initializer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记类的顺序
 *
 * @author: tanpenggood
 * @date: 2021-03-24 00:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    /**
     * 指定顺序，默认为-1
     *
     * @return
     */
    int value() default -1;

}
