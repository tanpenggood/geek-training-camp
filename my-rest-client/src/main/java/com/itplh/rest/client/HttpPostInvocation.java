package com.itplh.rest.client;

import com.itplh.rest.core.DefaultResponse;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.logging.Logger;

/**
 * @author: tanpenggood
 * @date: 2021-03-31 23:57
 */
public class HttpPostInvocation extends AbstractInvocation {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public HttpPostInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> entity) {
        super(uri, headers, entity);
    }

    @Override
    public Invocation property(String name, Object value) {
        return this;
    }

    @Override
    public Response invoke() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.POST);
            connection.setDefaultUseCaches(false);
            connection.setUseCaches(false);
            setRequestHeaders(connection);
            // TODO Set the cookies
            // 发送请求体
            sendRequestBodyIfNecessary(connection);

            DefaultResponse response = new DefaultResponse();
            response.setConnection(connection);
            // 设置响应状态码
            response.setStatus(connection.getResponseCode());
            // 设置响应头
            MultivaluedMap responseHeaders = new MultivaluedHashMap();
            connection.getHeaderFields().forEach((k, v) -> {
                responseHeaders.addAll(k, v);
            });
            response.setHeaders(responseHeaders);
            logger.info(String.format("[%s] POST SUCCESS %s", Thread.currentThread().getName(), this.uri.toString()));
            return response;
        } catch (IOException e) {
            // TODO Error handler
        }
        return null;
    }

}
