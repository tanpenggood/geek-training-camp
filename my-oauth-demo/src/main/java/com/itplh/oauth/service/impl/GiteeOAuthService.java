package com.itplh.oauth.service.impl;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itplh.oauth.domain.OAuthResponseEntity;
import com.itplh.oauth.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 03:59
 */
@Service
public class GiteeOAuthService implements OAuthService {

    @Autowired
    private GiteeOAuthURL giteeOauthURL;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String authorizeURL() {
        return giteeOauthURL.authorizeURL();
    }

    @Override
    public OAuthResponseEntity getAccessTokenEntity(String code) throws IOException {
        String accessTokenURL = giteeOauthURL.accessTokenURL(code);
        String responseBody = HttpUtil.createPost(accessTokenURL).execute().body();
        return objectMapper.readValue(responseBody, OAuthResponseEntity.class);
    }

    @Override
    public String getAccessTokenByCache() {
        return null;
    }

    @Override
    public String getUserInfo(String accessToken) {
        String userInfoURL = giteeOauthURL.userInfoURL(accessToken);
        return HttpUtil.createGet(userInfoURL).execute().body();
    }

}
