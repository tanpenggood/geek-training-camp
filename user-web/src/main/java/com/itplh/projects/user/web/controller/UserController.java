package com.itplh.projects.user.web.controller;


import com.itplh.projects.user.domain.User;
import com.itplh.projects.user.service.UserService;
import com.itplh.projects.user.service.UserServiceImpl;
import com.itplh.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author: tanpenggood
 * @date: 2021-03-01 01:10
 */
@Path("/user")
public class UserController implements PageController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private UserService userService = new UserServiceImpl();

    @Path("/register-page")
    public String registerPage(HttpServletRequest request, HttpServletResponse response) {
        return "register.jsp";
    }

    @Path("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        stringCharsetConvert(request.getParameter("name")).ifPresent(user::setName);
        stringCharsetConvert(request.getParameter("password")).ifPresent(user::setPassword);
        stringCharsetConvert(request.getParameter("email")).ifPresent(user::setEmail);
        stringCharsetConvert(request.getParameter("phoneNumber")).ifPresent(user::setPhoneNumber);

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

    /**
     * 字符集转换，解决浏览器传输过来的中文乱码
     *
     * @param param
     * @return
     */
    private Optional<String> stringCharsetConvert(String param) {
        return Optional.ofNullable(param)
                .map(name -> new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
    }

}
