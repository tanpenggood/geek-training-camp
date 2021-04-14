package com.itplh.serialize;

import com.alibaba.fastjson.JSON;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.itplh.serialize.Assert.assertNull;

/**
 * @author: tanpenggood
 * @date: 2021-04-14 00:50
 */
public class JsonSerializer extends AbstractSerializer {

    @Override
    public String serialize(Object o) {
        return toJson(o);
    }

    @Override
    public Object deserialize(String json, Class<?> targetClass) {
        return parseJson(json, targetClass);
    }

    private String toJson(Object obj) throws SerializeException {
        if (obj == null) {
            return "";
        }
        if (isWrapperClass(obj.getClass()) || isStringClass(obj.getClass())) {
            return obj.toString();
        }
        StringBuilder jsonBuilder = new StringBuilder("{");
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                // splice key
                String key = propertyDescriptor.getName();
                jsonBuilder.append(String.format("\"%s\":", key));
                // splice value
                Method readMethod = propertyDescriptor.getReadMethod();
                Class<?> returnType = readMethod.getReturnType();
                if (isPrimitiveClass(returnType) || isWrapperClass(returnType)) {
                    jsonBuilder.append(String.format("%s,", readMethod.invoke(obj)));
                } else if (isStringClass(returnType)) {
                    jsonBuilder.append(String.format("\"%s\",", readMethod.invoke(obj)));
                } else {
                    jsonBuilder.append(String.format("%s,", toJson(readMethod.invoke(obj))));
                }
            }
            String json = jsonBuilder.toString();
            if (json.endsWith(",")) {
                jsonBuilder.delete(json.length() - 1, json.length());
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new SerializeException(e);
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private Object parseJson(String json, Class<?> targetClass) throws SerializeException {
        assertNull(targetClass, "targetClass must be not null.");
        if (json == null) {
            return null;
        }
        if (json.length() == 0 || json.trim().length() == 0) {
            return json;
        }

        if (isStringClass(targetClass)
                || isWrapperClass(targetClass)
                || isPrimitiveClass(targetClass)) {
            return Converter.converterMap.get(targetClass).apply(json);
        }

        if (json.startsWith("{") && json.endsWith("}")) {
            return JSON.parseObject(json, targetClass);
        } else {
            throw new SerializeException("do not deserialize to " + targetClass.getName());
        }
    }

}
