package com.itplh.projects.user.web.controller;

import com.itplh.projects.user.domain.Result;
import com.itplh.web.mvc.controller.RestController;
import org.apache.commons.lang.StringUtils;
import org.jolokia.client.J4pClient;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pResponse;
import org.jolokia.client.request.J4pWriteRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.util.UUID;

/**
 * @author: tanpenggood
 * @date: 2021-03-15 21:06
 */
@Path("/my-jolokia")
public class JolokiaRestController implements RestController {

    @Path("/memory")
    public Result memory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        J4pReadRequest req = new J4pReadRequest("java.lang:type=Memory", "HeapMemoryUsage");
        J4pClient j4pClient = new J4pClient(getJolokiaUrl(request));
        J4pReadResponse resp = j4pClient.execute(req);
        return Result.ok(Result.SUCCESS, resp);
    }

    @Path("/read-user-email")
    public Result readUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        J4pReadRequest req = new J4pReadRequest("com.itplh.projects.user.management:type=User", "Email");
        J4pClient j4pClient = new J4pClient(getJolokiaUrl(request));
        J4pResponse<J4pReadRequest> resp = j4pClient.execute(req);
        return Result.ok(Result.SUCCESS, resp);
    }

    @Path("/write-user-email")
    public Result writeUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 注意pAttribute需要首字母大写 https://jolokia.org/reference/html/protocol.html
        J4pWriteRequest req = new J4pWriteRequest("com.itplh.projects.user.management:type=User",
                "Email", UUID.randomUUID().toString().concat("@itplh.com"));
        J4pClient j4pClient = new J4pClient(getJolokiaUrl(request));
        J4pResponse<J4pWriteRequest> resp = j4pClient.execute(req);
        return Result.ok(Result.SUCCESS, resp);
    }

    private String getJolokiaUrl(HttpServletRequest request) {
        StringBuilder buildUrl = new StringBuilder("http://localhost:").append(request.getLocalPort());
        if (StringUtils.isNotBlank(request.getContextPath())) {
            buildUrl.append(request.getContextPath());
        }
        buildUrl.append("/jolokia");
        return buildUrl.toString();
    }

}
