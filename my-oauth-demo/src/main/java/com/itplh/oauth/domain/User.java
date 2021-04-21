package com.itplh.oauth.domain;

import java.util.Date;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 20:57
 */
public class User {

    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String avatar_url;
    private Date created_at;
    private Date updated_at;
    private ThirdUserInfo thirdUserInfo;
    private ThirdType thirdType;

    public User(ThirdUserInfo thirdUserInfo, ThirdType thirdType) {
        this.thirdUserInfo = thirdUserInfo;
        this.thirdType = thirdType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public ThirdUserInfo getThirdUserInfo() {
        return thirdUserInfo;
    }

    public void setThirdUserInfo(ThirdUserInfo thirdUserInfo) {
        this.thirdUserInfo = thirdUserInfo;
    }

    public ThirdType getThirdType() {
        return thirdType;
    }

    public void setThirdType(ThirdType thirdType) {
        this.thirdType = thirdType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", thirdUserInfo=" + thirdUserInfo +
                ", thirdType=" + thirdType +
                '}';
    }
}
