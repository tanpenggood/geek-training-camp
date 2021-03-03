package com.itplh.projects.user.service;

import com.itplh.projects.user.domain.User;
import com.itplh.projects.user.repository.DatabaseUserRepository;
import com.itplh.projects.user.repository.UserRepository;

/**
 * 用户服务
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new DatabaseUserRepository();

    @Override
    public boolean register(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public User queryUserByName(String name) {
        return userRepository.getByName(name);
    }
}
