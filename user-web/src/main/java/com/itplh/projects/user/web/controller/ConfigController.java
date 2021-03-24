package com.itplh.projects.user.web.controller;

import com.itplh.projects.user.domain.Result;
import com.itplh.web.mvc.controller.RestController;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.annotation.Resource;
import javax.ws.rs.Path;

/**
 * @author: tanpenggood
 * @date: 2021-03-18 00:03
 */
@Path("/config")
public class ConfigController implements RestController {

    @Resource(name = "bean/ConfigProviderResolver")
    private ConfigProviderResolver configProviderResolver;

    @Path("/application-name")
    public Result applicationName() {
        Config config = configProviderResolver.getConfig();
        String applicationName = config.getValue("application.name", String.class);
        return Result.ok(Result.SUCCESS, applicationName);
    }

}
