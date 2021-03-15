package com.itplh.projects.user.web.controller;


import com.itplh.web.mvc.controller.PageController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

@Path("/jndi")
public class JndiController implements PageController {

    @Resource(name = "bean/HelloWorldController")
    private HelloWorldController helloWorldController;

    @PostConstruct
    public void init() {
        System.out.println(String.format("%s 依赖 %s", this, helloWorldController));
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "jndi-test.jsp";
    }

}
