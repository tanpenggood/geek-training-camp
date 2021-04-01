package com.itplh.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author: tanpenggood
 * @date: 2021-04-01 12:40
 */
public abstract class AbstractInvocation implements Invocation {

    protected final URI uri;

    protected final URL url;

    protected final MultivaluedMap<String, Object> headers;

    protected final Entity<?> entity;

    public AbstractInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> entity) {
        this.uri = uri;
        this.headers = headers;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
        if (entity == null) {
            this.entity = null;
        } else {
            this.entity = entity;
            // 设置请求头的Content-Type
            this.headers.addAll(HttpHeaders.CONTENT_TYPE, entity.getMediaType().toString());
        }
    }

    @Override
    public abstract Invocation property(String name, Object value);

    @Override
    public abstract Response invoke();

    @Override
    public <T> T invoke(Class<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) {
        return invoke(responseType);
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
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        return null;
    }

    /**
     * 设置请求头
     *
     * @param connection
     */
    protected final void setRequestHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(headerName, headerValue.toString());
            }
        }
    }

    /**
     * 发送请求体
     *
     * @param connection
     * @throws IOException
     */
    protected final void sendRequestBodyIfNecessary(HttpURLConnection connection) {
        Object requestBody = entity.getEntity();
        if (Objects.nonNull(requestBody)) {
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(true);
            String contentType = Optional.ofNullable(this.headers.getFirst(HttpHeaders.CONTENT_TYPE))
                    .map(c -> (String) c)
                    .orElse(null);
            switch (contentType) {
                case MediaType.APPLICATION_JSON:
                    ObjectMapper objectMapper = new ObjectMapper();
                    try (OutputStream outputStream = connection.getOutputStream()) {
                        IOUtils.write(objectMapper.writeValueAsString(requestBody), outputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
