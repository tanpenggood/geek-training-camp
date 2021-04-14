package com.itplh.serialize;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author: tanpenggood
 * @date: 2021-04-14 20:45
 */
public class JavaSerializerTest {

    @Test
    public void serialize() {
        JavaSerializer javaSerializer = new JavaSerializer();
        User user = new User(true, 18, "TPG");
        UserDetail userDetail = new UserDetail(true, 18, "TPG", new Address("重庆"));

        // 序列化
        String userSerialize = javaSerializer.serialize(user);
        String userDetailSerialize = javaSerializer.serialize(userDetail);
        Assert.assertEquals(null, javaSerializer.serialize(null));

        // 反序列化
        Assert.assertEquals(user, javaSerializer.deserialize(userSerialize, User.class));
        Assert.assertEquals(userDetail, javaSerializer.deserialize(userDetailSerialize, UserDetail.class));
        Assert.assertEquals(user, javaSerializer.deserialize(userSerialize, null));
        Assert.assertEquals(userDetail, javaSerializer.deserialize(userDetailSerialize, null));
        Assert.assertEquals(null, javaSerializer.deserialize(null, String.class));
        Assert.assertThrows(SerializeException.class, () -> javaSerializer.deserialize("", String.class));

        // 序列化-反序列化 基本类型
        Assert.assertEquals(123, javaSerializer.deserialize(javaSerializer.serialize(123), int.class));
        Assert.assertEquals(123D, javaSerializer.deserialize(javaSerializer.serialize(123D), double.class));
        Assert.assertEquals(123L, javaSerializer.deserialize(javaSerializer.serialize(123L), long.class));
        Assert.assertEquals(123F, javaSerializer.deserialize(javaSerializer.serialize(123F), float.class));
        Assert.assertEquals(true, javaSerializer.deserialize(javaSerializer.serialize(true), boolean.class));
        Assert.assertEquals('{', javaSerializer.deserialize(javaSerializer.serialize('{'), char.class));
    }

}
