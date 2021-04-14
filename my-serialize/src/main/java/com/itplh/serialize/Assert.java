package com.itplh.serialize;

/**
 * @author: tanpenggood
 * @date: 2021-04-15 04:49
 */
public abstract class Assert {

    public static final void assertNull(Object o, String message) {
        if (o == null) {
            throw new SerializeException(message);
        }
    }

    public static final void assertEmpty(byte[] bytes, String message) {
        if (bytes == null || bytes.length == 0) {
            throw new SerializeException(message);
        }
    }

    public static final void assertEmpty(String str, String message) {
        if (str == null || str.length() == 0) {
            throw new SerializeException(message);
        }
    }

}
