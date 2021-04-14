package com.itplh.serialize;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author: tanpenggood
 * @date: 2021-04-15 02:48
 */
public class Converter {

    public static final Map<Class, Function> converterMap = new HashMap<>(32);

    static {
        converterMap.put(byte.class, s -> Byte.parseByte(s.toString()));
        converterMap.put(short.class, s -> Short.parseShort(s.toString()));
        converterMap.put(int.class, s -> Integer.parseInt(s.toString()));
        converterMap.put(long.class, s -> Long.parseLong(s.toString()));
        converterMap.put(float.class, s -> Float.parseFloat(s.toString()));
        converterMap.put(double.class, s -> Double.parseDouble(s.toString()));
        converterMap.put(boolean.class, s -> Boolean.parseBoolean(s.toString()));
        converterMap.put(char.class, s -> Character.valueOf((s.toString()).charAt(0)));

        converterMap.put(Byte.class, s -> Byte.parseByte(s.toString()));
        converterMap.put(Short.class, s -> Short.parseShort(s.toString()));
        converterMap.put(Integer.class, s -> Integer.parseInt(s.toString()));
        converterMap.put(Long.class, s -> Long.parseLong(s.toString()));
        converterMap.put(Float.class, s -> Float.parseFloat(s.toString()));
        converterMap.put(Double.class, s -> Double.parseDouble(s.toString()));
        converterMap.put(Boolean.class, s -> Boolean.parseBoolean(s.toString()));
        converterMap.put(Character.class, s -> Character.valueOf((s.toString()).charAt(0)));

        converterMap.put(String.class, s -> s.toString());
    }

}
