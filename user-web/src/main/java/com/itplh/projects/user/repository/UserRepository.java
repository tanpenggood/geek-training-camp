package com.itplh.projects.user.repository;


import com.itplh.projects.user.domain.User;

import java.util.Collection;

/**
 * 用户存储仓库
 *
 * @since 1.0
 */
public interface UserRepository {

    boolean save(User user);

    boolean deleteById(Long userId);

    boolean update(User user);

    User getById(Long userId);

    User getByNameAndPassword(String name, String password);

    User getByName(String name);

    Collection<User> getAll();

}
