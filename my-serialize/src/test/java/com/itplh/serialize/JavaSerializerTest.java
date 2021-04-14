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
        Assert.assertEquals(null, javaSerializer.deserialize(null, String.class));
        Assert.assertThrows(SerializeException.class, () -> javaSerializer.deserialize("", String.class));
    }

}
