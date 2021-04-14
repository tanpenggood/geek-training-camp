package com.itplh.serialize;

import java.util.Objects;

/**
 * @author: tanpenggood
 * @date: 2021-04-14 01:26
 */
public class UserDetail {
    private boolean flag;
    private int age;
    private String name;
    private Address address;

    public UserDetail() {
    }

    public UserDetail(boolean flag, int age, String name, Address address) {
        this.flag = flag;
        this.age = age;
        this.name = name;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "flag=" + flag +
                "age=" + age +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetail that = (UserDetail) o;
        return flag == that.flag &&
                age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flag, age, name, address);
    }
}
