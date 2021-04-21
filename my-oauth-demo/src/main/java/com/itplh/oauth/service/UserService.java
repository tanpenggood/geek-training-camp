package com.itplh.oauth.service;

import com.itplh.oauth.domain.ThirdType;
import com.itplh.oauth.domain.ThirdUserInfo;
import com.itplh.oauth.domain.User;
import com.itplh.oauth.domain.UserLogin;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 20:56
 */
public interface UserService {

    User login(UserLogin userLogin);

    User register(User user);

    User save(User user);

    User selectByUsername(String username);

    int count();

    ThirdUserInfo saveForThirdPartUser(ThirdUserInfo thirdUserInfo, ThirdType thirdType);
}
