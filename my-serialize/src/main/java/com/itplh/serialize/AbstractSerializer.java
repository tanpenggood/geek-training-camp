package com.itplh.serialize;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: tanpenggood
 * @date: 2021-04-15 02:36
 */
public abstract class AbstractSerializer {

    public abstract String serialize(Object value) throws SerializeException;

    public abstract Object deserialize(String value, Class<?> targetClass) throws SerializeException;

    protected final boolean isWrapperClass(Class<?> clazz) {
        return wrapperClassMapping.contains(clazz);
    }

    protected final boolean isPrimitiveClass(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    protected final boolean isStringClass(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    protected static final Set<Class> wrapperClassMapping = new HashSet<>();

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

    protected final void assertNull(Object o, String message) {
        if (o == null) {
            throw new SerializeException(message);
        }
    }

    protected final void assertEmpty(byte[] bytes, String message) {
        if (bytes == null || bytes.length == 0) {
            throw new SerializeException(message);
        }
    }

    protected final void assertEmpty(String str, String message) {
        if (str == null || str.length() == 0) {
            throw new SerializeException(message);
        }
    }

}
