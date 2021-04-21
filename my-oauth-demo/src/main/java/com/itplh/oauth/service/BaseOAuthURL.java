package com.itplh.oauth.service;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 03:51
 */
public interface BaseOAuthURL {
    /**
     * 授权地址
     *
     * @return 授权地址
     */
    String authorizeURL();

    /**
     * 获取accessToken地址
     *
     * @param code 请求编码
     * @return accessToken
     */
    String accessTokenURL(String code);

    /**
     * 获取用户信息地址
     *
     * @param accessToken token
     * @return user
     */
    String userInfoURL(String accessToken);
}
