package com.itplh.projects.user.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.ServiceLoader;

/**
 * @author: tanpenggood
 * @date: 2021-03-19 00:54
 */
public class Converters implements Iterable<Converter> {

    public static final int DEFAULT_PRIORITY = 100;

    private final Map<Class<?>, PriorityQueue<PrioritizedConverter>> typedConverters = new HashMap<>();

    private ClassLoader classLoader;

    private boolean addedDiscoveredConverters = false;

    public Converters() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public Converters(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 添加SPI配置的{@link Converter}的实现类
     */
    public void addDiscoveredConverters() {
        if (this.addedDiscoveredConverters) {
            return;
        }
        addConverters(ServiceLoader.load(Converter.class, this.getClassLoader()));
        this.addedDiscoveredConverters = true;
    }

    public void addConverters(Iterable<Converter> converters) {
        converters.forEach(this::addConverter);
    }

    /**
     * @param converters
     * @see ConfigBuilder#withConverters(org.eclipse.microprofile.config.spi.Converter[])
     */
    public void addConverters(Converter... converters) {
        addConverters(Arrays.asList(converters));
    }

    public void addConverter(Converter converter) {
        addConverter(DEFAULT_PRIORITY, converter);
    }

    public void addConverter(int priority, Converter converter) {
        Class<?> convertedType = resolveConvertedType(converter);
        addConverter(convertedType, priority, converter);
    }

    /**
     * @param converter
     * @param priority
     * @param convertedType 转换的目标类型
     * @see ConfigBuilder#withConverter(java.lang.Class, int, org.eclipse.microprofile.config.spi.Converter)
     */
    public void addConverter(Class<?> convertedType, int priority, Converter converter) {
        // computeIfAbsent key不存在才put，返回新的值
        PriorityQueue priorityQueue = this.typedConverters.computeIfAbsent(convertedType, t -> new PriorityQueue<>());
        if (Objects.nonNull(priorityQueue)) {
            // 添加一个元素并返回boolean 不抛异常
            priorityQueue.offer(new PrioritizedConverter(converter, priority));
        }
    }

    protected Class<?> resolveConvertedType(Converter<?> converter) {
        // 对接口与抽象类进行断言
        assertConverter(converter);
        Class<?> convertedType = null;
        Class<?> converterClass = converter.getClass();
        while (converterClass != null) {
            convertedType = resolveConvertedType(converterClass);
            if (convertedType != null) {
                break;
            }

            Type superType = converterClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType) {
                convertedType = resolveConvertedType(superType);
            }

            if (convertedType != null) {
                break;
            }
            // recursively
            converterClass = converterClass.getSuperclass();

        }

        return convertedType;
    }

    /**
     * 对接口与抽象类进行断言
     *
     * @param converter
     */
    private void assertConverter(Converter<?> converter) {
        Class<?> converterClass = converter.getClass();
        if (converterClass.isInterface()) {
            throw new IllegalArgumentException("The implementation class of Converter must not be an interface!");
        }
        if (Modifier.isAbstract(converterClass.getModifiers())) {
            throw new IllegalArgumentException("The implementation class of Converter must not be abstract!");
        }
    }

    private Class<?> resolveConvertedType(Class<?> converterClass) {
        Class<?> convertedType = null;
        for (Type superInterface : converterClass.getGenericInterfaces()) {
            convertedType = resolveConvertedType(superInterface);
            if (convertedType != null) {
                break;
            }
        }

        return convertedType;
    }

    private Class<?> resolveConvertedType(Type type) {
        Class<?> convertedType = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            if (pType.getRawType() instanceof Class) {
                Class<?> rawType = (Class) pType.getRawType();
                if (Converter.class.isAssignableFrom(rawType)) {
                    Type[] arguments = pType.getActualTypeArguments();
                    if (arguments.length == 1 && arguments[0] instanceof Class) {
                        convertedType = (Class) arguments[0];
                    }
                }
            }
        }
        return convertedType;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * 获取指定转换类型的Converter
     *
     * @return
     */
    public List<Converter> getConverters(Class<?> convertedType) {
        PriorityQueue<PrioritizedConverter> prioritizedConverters = this.typedConverters.get(convertedType);
        if (Objects.isNull(prioritizedConverters) || prioritizedConverters.isEmpty()) {
            return Collections.emptyList();
        }
        List<Converter> converters = new LinkedList<>();
        for (PrioritizedConverter prioritizedConverter : prioritizedConverters) {
            converters.add(prioritizedConverter.getConverter());
        }
        return converters;
    }

    @Override
    public Iterator<Converter> iterator() {
        List<Converter> allConverters = new LinkedList<>();
        for (PriorityQueue<PrioritizedConverter> converters : this.typedConverters.values()) {
            for (PrioritizedConverter converter : converters) {
                allConverters.add(converter.getConverter());
            }
        }
        return allConverters.iterator();
    }
}

