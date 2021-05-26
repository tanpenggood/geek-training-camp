package com.itplh.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itplh.mybatis.domain.User;
import com.itplh.mybatis.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: tanpenggood
 * @date: 2021-05-27 01:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EnableMyBatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectById() throws Exception {
        System.out.println(userMapper.selectById(3));
    }

    @Test
    public void testSelectAll() {
        userMapper.selectAll().forEach(System.out::println);
    }

    @Test
    public void testSelectPage() throws Exception {
        PageHelper.startPage(1, 2);
        PageInfo<User> page = new PageInfo<>(userMapper.selectAll());
        page.getList().forEach(System.out::println);
    }

}
