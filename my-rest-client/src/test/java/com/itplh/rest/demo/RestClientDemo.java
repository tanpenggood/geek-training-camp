package com.itplh.rest.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class RestClientDemo {

    private String getUri;
    private String postUri;
    private long start;
    private Map<String, String> requestBody;

    @Before
    public void before() {
        getUri = "http://127.0.0.1:8080/user-web/hello/get";
        postUri = "http://127.0.0.1:8080/user-web/hello/post";
        start = System.currentTimeMillis();

        requestBody = new HashMap<>();
        requestBody.put("name", "张三");
        requestBody.put("age", "18");
        requestBody.put("sex", "男");
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - start;
        System.out.println(String.format("cost: %s ms", cost));
    }

    @Test
    public void testGetRequest() {
        System.out.println(getRequest().readEntity(String.class));
    }

    @Test
    public void testAsyncGetRequest() throws Exception {
        System.out.println(asyncGetRequest().get().readEntity(String.class));
    }

    @Test
    public void testPostRequest() {
        List<Response> responses = new ArrayList<>();
        responses.add(postRequest());
        responses.add(postRequest(requestBody));
        responses.add(postRequest());
        responses.stream()
                .map(response -> response.readEntity(HashMap.class))
                .forEach(System.out::println);
    }

    @Test
    public void testAsyncPostRequest() {
        List<CompletableFuture<Response>> postRequests = new ArrayList<>();
        postRequests.add((CompletableFuture) asyncPostRequest());
        postRequests.add((CompletableFuture) asyncPostRequest(requestBody));
        postRequests.add((CompletableFuture) asyncPostRequest());
        for (CompletableFuture<Response> postRequest : postRequests) {
            postRequest.whenComplete((response, throwable) -> System.out.println(response.readEntity(HashMap.class)));
        }
        CompletableFuture<Void> all = CompletableFuture.allOf(postRequests.toArray(new CompletableFuture[0]));
        all.join();
    }

    @Test
    public void testAsyncPostRequestConcurrent() {
        int count = 20;
        List<CompletableFuture<Response>> postRequests = new ArrayList<>();
        IntStream.range(0, count / 2).forEach(i -> {
            postRequests.add((CompletableFuture) asyncPostRequest());
            postRequests.add((CompletableFuture) asyncPostRequest(requestBody));
        });
        for (CompletableFuture<Response> postRequest : postRequests) {
            postRequest.whenComplete((response, throwable) -> System.out.println(response.readEntity(HashMap.class)));
        }
        CompletableFuture<Void> all = CompletableFuture.allOf(postRequests.toArray(new CompletableFuture[0]));
        all.join();
    }

    private Response getRequest() {
        return builderRequest(getUri).get(); //  Response
    }

    private Future<Response> asyncGetRequest() {
        return builderRequest(getUri)
                .async() // AsyncInvoker
                .get(); //  Response
    }

    private Response postRequest(Map requestBody) {
        return builderRequest(postUri)
                .post(Entity.json(requestBody)); //  Response
    }

    private Response postRequest() {
        return postRequest(null);
    }

    private Future<Response> asyncPostRequest(Map requestBody) {
        return builderRequest(postUri)
                .async() // AsyncInvoker
                .post(Entity.json(requestBody)); //  Response
    }

    private Future<Response> asyncPostRequest() {
        return asyncPostRequest(null);
    }

    private Invocation.Builder builderRequest(String uri) {
        Client client = ClientBuilder.newClient();
        return client
                .target(uri) // WebTarget
                .request(); // Invocation.Builder
    }

}
