package com.itplh.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itplh.rest.core.DefaultResponse;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * @author: tanpenggood
 * @date: 2021-03-31 23:57
 */
public class HttpPostInvocation implements Invocation {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final URI uri;

    private final URL url;

    private final MultivaluedMap<String, Object> headers;

    private final Entity<?> entity;

    public HttpPostInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> entity) {
        this.uri = uri;
        this.headers = headers;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
        this.entity = entity;
        // 设置请求头的Content-Type
        this.headers.addAll("Content-Type", entity.getMediaType().toString());
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
            setRequestHeaders(connection);
            // TODO Set the cookies
            // 发送请求体
            Object requestBody = entity.getEntity();
            if (Objects.nonNull(requestBody)) {
                // 设置是否向HttpURLConnection输出
                connection.setDoOutput(true);
                try (OutputStream request = connection.getOutputStream()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    IOUtils.write(objectMapper.writeValueAsString(requestBody), request);
                }
            }

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

    private void setRequestHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(headerName, headerValue.toString());
            }
        }
    }

    @Override
    public <T> T invoke(Class<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public <T> T invoke(GenericType<T> genericType) {
        Response response = invoke();
        return response.readEntity(genericType);
    }

    @Override
    public Future<Response> submit() {
        return CompletableFuture.supplyAsync(() -> invoke());
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        return CompletableFuture.supplyAsync(() -> invoke(responseType));
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        return CompletableFuture.supplyAsync(() -> invoke(responseType));
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> invocationCallback) {
        return null;
    }
}
