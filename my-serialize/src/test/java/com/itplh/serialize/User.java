package com.itplh.serialize;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: tanpenggood
 * @date: 2021-04-14 01:26
 */
public class User implements Serializable {

    private boolean flag;
    private int age;
    private String name;

    public User() {
    }

    public User(boolean flag, int age, String name) {
        this.flag = flag;
        this.age = age;
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "flag=" + flag +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return flag == user.flag &&
                age == user.age &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flag, age, name);
    }
}
