package com.itplh.rest.demo;

import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class RestClientDemo {

    @Test
    public void testGetRequest() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.Builder
                .get();                                     //  Response

        String content = response.readEntity(String.class);
        System.out.println(content);
    }

    @Test
    public void testPostRequest() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/geek")      // WebTarget
                .request() // Invocation.Builder
                .post(null);                                     //  Response

        String content = response.readEntity(String.class);
        System.out.println(content);
    }

}
