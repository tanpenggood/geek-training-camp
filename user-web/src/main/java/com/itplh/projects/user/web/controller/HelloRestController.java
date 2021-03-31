package com.itplh.projects.user.web.controller;

import com.alibaba.fastjson.JSON;
import com.itplh.projects.user.domain.Result;
import com.itplh.web.mvc.controller.RestController;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: tanpenggood
 * @date: 2021-04-01 06:26
 */
@Path("/hello")
public class HelloRestController implements RestController {

    @GET
    @Path("/get")
    public String get() {
        return "Hello GET.";
    }

    @POST
    @Path("/post")
    public Result post(HttpServletRequest request) throws Exception {
        Map requestBody;
        try (ServletInputStream inputStream = request.getInputStream()) {
            requestBody = JSON.parseObject(IOUtils.toString(inputStream), HashMap.class);
        }
        if (Objects.isNull(requestBody) || requestBody.isEmpty()) {
            requestBody = new HashMap<>();
            requestBody.put("name", "李四");
            requestBody.put("age", "22");
            requestBody.put("sex", "男");
        }
        TimeUnit.SECONDS.sleep(2);
        return Result.ok(Result.SUCCESS, requestBody);
    }

}
