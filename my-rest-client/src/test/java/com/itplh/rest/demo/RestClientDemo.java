package com.itplh.rest.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RestClientDemo {

    private long start = System.currentTimeMillis();
    private Map<String, String> requestBody = new HashMap<>();

    @Before
    public void before() {
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
        List<Response> responses = new ArrayList<>(Arrays.asList(postRequest(), postRequest(), postRequest()));
        responses.stream()
                .map(response -> response.readEntity(HashMap.class))
                .forEach(System.out::println);
    }

    @Test
    public void testAsyncPostRequest() throws Exception {
        List<Future<Response>> futures = new ArrayList<>(Arrays.asList(asyncPostRequest(), asyncPostRequest(), asyncPostRequest()));
        CountDownLatch countDownLatch = new CountDownLatch(futures.size());
        while (countDownLatch.getCount() > 0) {
            for (Future<Response> future : futures) {
                if (future.isDone()) {
                    countDownLatch.countDown();
                    System.out.println(future.get().readEntity(HashMap.class));
                }
            }
            TimeUnit.MILLISECONDS.sleep(200);
        }
        countDownLatch.await();
    }

    private Response getRequest() {
        Client client = ClientBuilder.newClient();
        return client
                .target("http://127.0.0.1:8080/hello/world") // WebTarget
                .request() // Invocation.Builder
                .get(); //  Response
    }

    private Future<Response> asyncGetRequest() throws Exception {
        Client client = ClientBuilder.newClient();
        return client
                .target("http://127.0.0.1:8080/hello/world") // WebTarget
                .request() // Invocation.Builder
                .async() // AsyncInvoker
                .get(); //  Response
    }

    private Response postRequest() {
        Client client = ClientBuilder.newClient();
        return client
                .target("http://127.0.0.1:8080/hello/geek") // WebTarget
                .request() // Invocation.Builder
                .post(Entity.json(requestBody)); //  Response
    }

    private Future<Response> asyncPostRequest() throws Exception {
        Client client = ClientBuilder.newClient();
        return client
                .target("http://127.0.0.1:8080/hello/geek") // WebTarget
                .request() // Invocation.Builder
                .async() // AsyncInvoker
                .post(Entity.json(requestBody)); //  Response
    }

}
