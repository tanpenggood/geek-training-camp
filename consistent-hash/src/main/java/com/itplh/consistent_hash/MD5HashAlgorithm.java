package com.itplh.consistent_hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: tanpenggood
 * @date: 2021-05-18 02:53
 */
public class MD5HashAlgorithm implements HashAlgorithm {

    private static ThreadLocal<MessageDigest> MD = new ThreadLocal<>();

    @Override
    public Long hash(String key) {
        return hash(getMD5(key), 0);
    }

    private long hash(byte[] digest, int number) {
        return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                | (digest[number * 4] & 0xFF))
                & 0xFFFFFFFFL;
    }

    /**
     * get md5.
     *
     * @param str input string.
     * @return MD5 byte array.
     */
    public static byte[] getMD5(String str) {
        return getMD5(str.getBytes());
    }

    /**
     * get md5.
     *
     * @param source byte array source.
     * @return MD5 byte array.
     */
    public static byte[] getMD5(byte[] source) {
        MessageDigest md = getMessageDigest();
        return md.digest(source);
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest ret = MD.get();
        if (ret == null) {
            try {
                ret = MessageDigest.getInstance("MD5");
                MD.set(ret);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return ret;
    }
}
