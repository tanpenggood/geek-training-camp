package com.itplh.mybatis.factory;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class MyObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> T create(Class<T> type) {
        Class<?> classToCreate = resolveInterface(type);
        System.out.println(classToCreate.getSimpleName() + "正在被创建...");
        return (T) super.create(classToCreate);
    }
}
