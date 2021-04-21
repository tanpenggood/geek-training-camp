package com.itplh.oauth.service.impl;

import com.itplh.oauth.service.BaseOAuthURL;
import org.springframework.stereotype.Service;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 03:53
 */
@Service
public class GiteeOAuthURL implements BaseOAuthURL {

    private static final String CLIENT_ID = "837679565d2860685877aaad905fbdc6c66a2f107c2f3e5bb8cd550fedf4b5dc";
    private static final String CLIENT_SECRET = "215e066c57a980eab858082fae88a6d1e1cd78aa8017131092cfdc5c719d6179";

    private static final String REDIRECT_URI = "http://localhost:8080/gitee/callback";

    @Override
    public String authorizeURL() {
        return "https://gitee.com/oauth/authorize?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI;
    }

    @Override
    public String accessTokenURL(String code) {
        return "https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&client_secret=" + CLIENT_SECRET;
    }

    @Override
    public String userInfoURL(String accessToken) {
        return "https://gitee.com/api/v5/user?access_token=" + accessToken;
    }
}
