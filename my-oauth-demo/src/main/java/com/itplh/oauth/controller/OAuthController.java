package com.itplh.oauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itplh.oauth.domain.GiteeUserInfo;
import com.itplh.oauth.domain.ThirdType;
import com.itplh.oauth.domain.User;
import com.itplh.oauth.domain.UserLogin;
import com.itplh.oauth.service.UserService;
import com.itplh.oauth.service.impl.GiteeOAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Controller
public class OAuthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String CURRENT_USER = "CURRENT_USER";
    public static final String INDEX_URI = "http://localhost:8080/resource/index";
    public static final String LOGIN_URI = "http://localhost:8080/login";

    @Autowired
    private GiteeOAuthService giteeOAuthService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 表单登录
     *
     * @param userLogin
     * @param session
     * @return
     */
    @PostMapping(value = "/form/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postLogin(UserLogin userLogin,
                            HttpSession session) {
        User user = userService.login(userLogin);
        logger.info("userLogin {}", userLogin);
        logger.info("user {}", user);
        if (Objects.nonNull(user)) {
            session.setAttribute(USER_TOKEN, UUID.randomUUID().toString());
            session.setAttribute(CURRENT_USER, user);
            return "redirect:" + INDEX_URI;
        }
        return "login";
    }

    /**
     * OAuth授权登录
     *
     * @param type
     * @param response
     * @throws IOException
     */
    @GetMapping("/oauth/login")
    public void login(@RequestParam("type") String type,
                      HttpServletResponse response) throws IOException {
        String redirectURL = null;
        if (Objects.equals(ThirdType.GITEE.toString(), type.toUpperCase())) {
            redirectURL = giteeOAuthService.authorizeURL();
        } else {
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            PrintWriter writer = response.getWriter();
            writer.write("暂未实现该登录方式！");
            writer.flush();
            return;
        }
        response.sendRedirect(redirectURL);
    }

    /**
     * OAuth授权登录回调
     *
     * @param type
     * @param code
     * @param session
     * @return
     * @throws IOException
     */
    @GetMapping("/{type}/callback")
    public String callback(@PathVariable("type") String type,
                           @RequestParam("code") String code,
                           HttpSession session) throws IOException {
        User user = null;
        String access_token = null;
        if (Objects.equals(ThirdType.GITEE.toString(), type.toUpperCase())) {
            access_token = giteeOAuthService.getAccessTokenEntity(code).getAccess_token();
            String userInfoJson = giteeOAuthService.getUserInfo(access_token);
            GiteeUserInfo giteeUserInfo = objectMapper.readValue(userInfoJson, GiteeUserInfo.class);
            logger.info("giteeUserInfo {}", giteeUserInfo);
            user = new User(giteeUserInfo, ThirdType.GITEE);
        } else {
            return "redirect:" + LOGIN_URI;
        }
        user = userService.save(user);
        logger.info("user {}", user);
        session.setAttribute(USER_TOKEN, UUID.randomUUID().toString());
        session.setAttribute(CURRENT_USER, user);
        return "redirect:" + INDEX_URI;
    }

    /**
     * 退出
     *
     * @param session
     * @param response
     * @throws IOException
     */
    @GetMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.removeAttribute(USER_TOKEN);
        session.removeAttribute(CURRENT_USER);
        response.sendRedirect(LOGIN_URI);
    }

}

