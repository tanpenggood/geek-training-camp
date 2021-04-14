package com.itplh.serialize;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author: tanpenggood
 * @date: 2021-04-14 20:45
 */
public class JsonSerializerTest {

    @Test
    public void serialize() {
        JsonSerializer jsonSerializer = new JsonSerializer();
        User user = new User(true, 18, "TPG");
        UserDetail userDetail = new UserDetail(true, 18, "TPG", new Address("重庆"));
        String jsonUser = "{\"age\":18,\"flag\":true,\"name\":\"TPG\"}";
        String jsonUserDetail = "{\"address\":{\"city\":\"重庆\"},\"age\":18,\"flag\":true,\"name\":\"TPG\"}";

        // 序列化
        Assert.assertEquals(jsonUser, jsonSerializer.serialize(user));
        Assert.assertEquals(jsonUserDetail, jsonSerializer.serialize(userDetail));
        Assert.assertEquals("123", jsonSerializer.serialize("123"));
        Assert.assertEquals("123", jsonSerializer.serialize(123));
        Assert.assertEquals("123", jsonSerializer.serialize(new Integer(123)));
        Assert.assertEquals("true", jsonSerializer.serialize(true));
        Assert.assertEquals("", jsonSerializer.serialize(""));
        Assert.assertEquals("", jsonSerializer.serialize(null));

        // 反序列化
        Assert.assertEquals(user, jsonSerializer.deserialize(jsonUser, User.class));
        Assert.assertEquals(userDetail, jsonSerializer.deserialize(jsonUserDetail, UserDetail.class));
        Assert.assertEquals("123", jsonSerializer.deserialize("123", String.class));
        Assert.assertEquals("123", jsonSerializer.deserialize("123", int.class));
        Assert.assertEquals("false", jsonSerializer.deserialize("false", Boolean.class));
        Assert.assertEquals(null, jsonSerializer.deserialize(null, String.class));
        Assert.assertThrows(SerializeException.class, () -> jsonSerializer.deserialize("123", User.class));
    }

}
