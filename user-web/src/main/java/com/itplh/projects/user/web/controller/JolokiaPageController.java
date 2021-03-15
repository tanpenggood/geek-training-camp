package com.itplh.projects.user.web.controller;

import com.itplh.web.mvc.controller.PageController;

import javax.ws.rs.Path;

/**
 * @author: tanpenggood
 * @date: 2021-03-15 23:02
 */
@Path("/my-jolokia")
public class JolokiaPageController implements PageController {

    @Path("/index")
    public String index() {
        return "jolokia.jsp";
    }

}
