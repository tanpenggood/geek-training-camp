package com.itplh.serialize;

import com.alibaba.fastjson.JSON;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: tanpenggood
 * @date: 2021-04-14 00:50
 */
public class JsonSerializer {

    public String serialize(Object o) {
        return toJson(o);
    }

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
            return json;
        }

        if (json.startsWith("{") && json.endsWith("}")) {
            return JSON.parseObject(json, targetClass);
        } else {
            throw new SerializeException("do not deserialize to " + targetClass.getName());
        }
    }

    private boolean isWrapperClass(Class<?> clazz) {
        return wrapperClassMapping.contains(clazz);
    }

    private boolean isPrimitiveClass(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    private boolean isStringClass(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    private static Set<Class> wrapperClassMapping = new HashSet<>();

    static {
        wrapperClassMapping.add(Byte.class);
        wrapperClassMapping.add(Short.class);
        wrapperClassMapping.add(Integer.class);
        wrapperClassMapping.add(Long.class);
        wrapperClassMapping.add(Float.class);
        wrapperClassMapping.add(Double.class);
        wrapperClassMapping.add(Boolean.class);
        wrapperClassMapping.add(Character.class);
    }

    private void assertNull(Object o, String message) {
        if (o == null) {
            throw new SerializeException(message);
        }
    }

}
