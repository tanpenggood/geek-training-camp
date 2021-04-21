package com.itplh.oauth.service;

import com.itplh.oauth.domain.User;
import com.itplh.oauth.domain.UserLogin;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 20:56
 */
public interface UserService {

    User save(User user);

    User login(UserLogin userLogin);
}
