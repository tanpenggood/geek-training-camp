package com.itplh.projects.user.service;

import com.itplh.projects.user.domain.User;
import com.itplh.projects.user.repository.UserRepository;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 用户服务
 */
public class UserServiceImpl implements UserService {

    @Resource(name = "bean/JpaUserRepository")
    private UserRepository jpaUserRepository;

    @Resource(name = "bean/DatabaseUserRepository")
    private UserRepository databaseUserRepository;

    @Override
    public boolean register(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return databaseUserRepository.deleteById(user.getId());
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
        return databaseUserRepository.getByName(name);
    }

    @Override
    public Collection<User> queryAll() {
        return databaseUserRepository.getAll();
    }
}
