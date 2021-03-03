package com.itplh.projects.user.web.controller;


import com.itplh.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 输出 “Hello,World” Controller
 */
@Path("/hello")
public class HelloWorldController implements PageController {

    @GET
    @POST
    @Path("/world") // /hello/world -> HelloWorldController
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "index.jsp";
    }

    @Path("/world2")
    public String world2(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "index.jsp";
    }
}
