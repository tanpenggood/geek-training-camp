package com.itplh.oauth.filter;

import com.itplh.oauth.controller.OAuthController;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Order(Integer.MIN_VALUE)
@Component
public class OAuthFilter extends OncePerRequestFilter {

    public static final List SKIP_URIs = Arrays.asList("/login", "/form/login",
            "/oauth/login", "/gitee/callback");
    public static final String STATIC_JS_URI_PREFIX = "/static/js";
    public static final String STATIC_CSS_URI_PREFIX = "/static/css";
    public static final String RESOURCE_URI_PREFIX = "/resource";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // static resource green light
        if (request.getRequestURI().startsWith(STATIC_JS_URI_PREFIX)
                || request.getRequestURI().startsWith(STATIC_CSS_URI_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        boolean auth = false;
        // match access token
        if (hasAccessToken(request)) {
            auth = true;
        }
        if (auth) {
            if (SKIP_URIs.contains(request.getRequestURI())) {
                response.sendRedirect(OAuthController.INDEX_URI);
                return;
            }
        }
        if (!auth && !SKIP_URIs.contains(request.getRequestURI())) {
            response.sendRedirect(OAuthController.LOGIN_URI);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean hasAccessToken(HttpServletRequest request) {
        Object oAuthEntity = request.getSession().getAttribute(OAuthController.USER_TOKEN);
        return Objects.nonNull(oAuthEntity);
    }
}
