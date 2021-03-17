package com.itplh.projects.user.web.controller;

import com.itplh.projects.user.domain.Result;
import com.itplh.web.mvc.controller.RestController;
import org.eclipse.microprofile.config.Config;

import javax.annotation.Resource;
import javax.ws.rs.Path;

/**
 * @author: tanpenggood
 * @date: 2021-03-18 00:03
 */
@Path("/config")
public class ConfigController implements RestController {

    @Resource(name = "bean/Config")
    private Config config;

    @Path("/application-name")
    public Result applicationName() {
        String applicationName = config.getValue("application.name", String.class);
        return Result.ok(Result.SUCCESS, applicationName);
    }
}
