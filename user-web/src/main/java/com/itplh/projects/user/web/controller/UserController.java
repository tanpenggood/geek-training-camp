package com.itplh.projects.user.web.controller;


import com.itplh.projects.user.domain.User;
import com.itplh.projects.user.service.UserService;
import com.itplh.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author: tanpenggood
 * @date: 2021-03-01 01:10
 */
@Path("/user")
public class UserController implements PageController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "bean/UserService")
    private UserService userService;

    @Path("/register-page")
    public String registerPage(HttpServletRequest request, HttpServletResponse response) {
        return "register.jsp";
    }

    @Path("/register")
    public String register(HttpServletRequest request, HttpServletResponse response, @Valid User user) {
        if (user == null) {
            logger.warning(String.format("user is required."));
            return "redirect:register-fail";
        }

        // 重复性校验
        User dbUser = userService.queryUserByName(user.getName());
        Optional.ofNullable(dbUser).ifPresent(u -> logger.info(String.format("dbUser[%s]", u.toString())));
        if (dbUser != null) {
            logger.warning(String.format("user[%s] 用户名重复", user.toString()));
            return "redirect:register-fail";
        }

        boolean isRegisterSuccess = userService.register(user);
        logger.info(String.format("user[%s] isRegisterSuccess[%s]", user.toString(), isRegisterSuccess));
        return isRegisterSuccess ? "redirect:register-success" : "redirect:register-fail";
    }

    @Path("/register-success")
    public String registerSuccess(HttpServletRequest request, HttpServletResponse response) {
        return "register-success.jsp";
    }

    @Path("/register-fail")
    public String registerFail(HttpServletRequest request, HttpServletResponse response) {
        return "register-fail.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return null;
    }

}
