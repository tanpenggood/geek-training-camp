package com.itplh.projects.user.web.controller;


import com.itplh.projects.user.domain.Result;
import com.itplh.projects.user.domain.User;
import com.itplh.projects.user.service.UserService;
import com.itplh.projects.user.service.UserServiceImpl;
import com.itplh.web.mvc.controller.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.util.Objects;

/**
 * @author: tanpenggood
 * @date: 2021-03-01 01:10
 */
@Path("/user")
public class UserRestController implements RestController {

    private UserService userService = new UserServiceImpl();

    @Path("/all")
    public Result all(HttpServletRequest request, HttpServletResponse response) {
        return Result.ok("操作成功", userService.queryAll());
    }

    @Path("/delete")
    public Result delete(HttpServletRequest request, HttpServletResponse response) {
        Long userId = stringCharsetConvert(request.getParameter("id"))
                .map(id -> Long.valueOf(id))
                .orElse(null);
        if (Objects.isNull(userId)) {
            return Result.error();
        }
        User user = new User();
        user.setId(userId);
        boolean deregister = userService.deregister(user);
        return deregister ? Result.ok() : Result.error();
    }

}
