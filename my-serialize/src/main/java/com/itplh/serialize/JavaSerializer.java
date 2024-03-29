package com.itplh.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;

import static com.itplh.serialize.Assert.assertEmpty;

/**
 * @author: tanpenggood
 * @date: 2021-04-15 02:24
 */
public class JavaSerializer extends AbstractSerializer {

    private Pattern deserializeStringPattern = Pattern.compile("[0-9|,|-]*");

    /**
     * 序列化基本类型时，因为该方法入参时Object，所以会进行自动装箱
     *
     * @param value
     * @return
     * @throws SerializeException
     */
    @Override
    public String serialize(Object value) throws SerializeException {
        if (value == null) {
            return null;
        }
        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(value);
            byte[] bytes = outputStream.toByteArray();
            return converterToString(bytes);
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

    /**
     * @param value
     * @param ignore Java反序列化的对象类型本身就是确定的，所以这里不需要指定转换的目标类型，调用API时请忽略该参数。
     * @return
     * @throws SerializeException
     */
    @Override
    public Object deserialize(String value, Class<?> ignore) throws SerializeException {
        if (value == null) {
            return null;
        }
        if (value.length() == 0 || value.trim().length() == 0) {
            throw new SerializeException("deserialize string must not be ''.");
        }
        if (!deserializeStringPattern.matcher(value).matches()) {
            throw new SerializeException("deserialize string is invalid.");
        }
        try (
                ByteArrayInputStream inputStream = new ByteArrayInputStream(converterToByteArray(value));
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            return objectInputStream.readObject();
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    private String converterToString(byte[] bytes) {
        assertEmpty(bytes, "bytes must not be null or [].");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            builder.append(bytes[i]).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private byte[] converterToByteArray(String str) {
        assertEmpty(str, "str must not be null or ''.");
        String[] strings = str.split(",");
        byte[] bytes = new byte[strings.length];
        for (int i = 0; i < strings.length; i++) {
            bytes[i] = Byte.parseByte(strings[i]);
        }
        return bytes;
    }
}
