package com.itplh.oauth.service;

import com.itplh.oauth.domain.OAuthResponseEntity;

import java.io.IOException;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 03:58
 */
public interface OAuthService {
    String authorizeURL();

    OAuthResponseEntity getAccessTokenEntity(String code) throws IOException;

    String getAccessTokenByCache();

    String getUserInfo(String accessToken);
}
