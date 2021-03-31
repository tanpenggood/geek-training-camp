package com.itplh.rest.demo;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * @author: tanpenggood
 * @date: 2021-03-31 23:15
 */
public class JdkHttpURLConnectionDemo {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("http://localhost:8080/hello/world");
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try (InputStream inputStream = connection.getInputStream()) {
            System.out.println(IOUtils.toString(inputStream, Charsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
