package com.itplh.oauth.service.impl;

import cn.hutool.core.lang.Assert;
import com.itplh.oauth.domain.GiteeUserInfo;
import com.itplh.oauth.domain.User;
import com.itplh.oauth.domain.UserLogin;
import com.itplh.oauth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 21:50
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final ConcurrentHashMap<String, User> allUsers = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, GiteeUserInfo> giteeUsers = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        Assert.notNull(user);
        Assert.notNull(user.getThirdType());
        Assert.notNull(user.getThirdUserInfo());

        switch (user.getThirdType()) {
            case GITEE:
                GiteeUserInfo giteeUserInfo = (GiteeUserInfo) user.getThirdUserInfo();
                giteeUsers.put(giteeUserInfo.getLogin(), giteeUserInfo);
                // unique
                user.setUsername(user.getThirdType() + "_" + giteeUserInfo.getLogin());
                user.setNickname(giteeUserInfo.getName());
                user.setEmail(giteeUserInfo.getEmail());
                user.setAvatar_url(giteeUserInfo.getAvatar_url());
                break;
            default:
                break;
        }
        // 已注册
        User cacheUser = allUsers.get(user.getUsername());
        if (Objects.nonNull(cacheUser)) {
            return cacheUser;
        }
        // 未注册
        user.setId(System.currentTimeMillis());
        user.setPassword("123456");
        user.setCreated_at(new Date());
        allUsers.put(user.getUsername(), user);
        logger.info("allUsers {}", allUsers.size());
        return user;
    }

    @Override
    public User login(UserLogin userLogin) {
        Assert.notNull(userLogin);
        Assert.notNull(userLogin.getUsername());
        Assert.notNull(userLogin.getPassword());

        User user = allUsers.get(userLogin.getUsername());
        if (Objects.isNull(user)) {
            return null;
        }
        boolean passwordEquals = user.getPassword().equals(userLogin.getPassword());
        return passwordEquals ? user : null;
    }

}
