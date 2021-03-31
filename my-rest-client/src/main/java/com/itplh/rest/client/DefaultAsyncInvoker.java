package com.itplh.rest.client;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author: tanpenggood
 * @date: 2021-04-01 04:58
 */
public class DefaultAsyncInvoker implements AsyncInvoker {

    private DefaultInvocationBuilder defaultInvocationBuilder;

    public DefaultAsyncInvoker(DefaultInvocationBuilder defaultInvocationBuilder) {
        this.defaultInvocationBuilder = defaultInvocationBuilder;
    }

    @Override
    public Future<Response> get() {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.buildGet().invoke());
    }

    @Override
    public <T> Future<T> get(Class<T> responseType) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.buildGet().invoke(responseType));
    }

    @Override
    public <T> Future<T> get(GenericType<T> responseType) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.buildGet().invoke(responseType));
    }

    @Override
    public <T> Future<T> get(InvocationCallback<T> callback) {
        return null;
    }

    @Override
    public Future<Response> put(Entity<?> entity) {
        return null;
    }

    @Override
    public <T> Future<T> put(Entity<?> entity, Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> put(Entity<?> entity, GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> put(Entity<?> entity, InvocationCallback<T> callback) {
        return null;
    }

    @Override
    public Future<Response> post(Entity<?> entity) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.buildPost(entity).invoke());
    }

    @Override
    public <T> Future<T> post(Entity<?> entity, Class<T> responseType) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.buildPost(entity).invoke(responseType));
    }

    @Override
    public <T> Future<T> post(Entity<?> entity, GenericType<T> responseType) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.buildPost(entity).invoke(responseType));
    }

    @Override
    public <T> Future<T> post(Entity<?> entity, InvocationCallback<T> callback) {
        return null;
    }

    @Override
    public Future<Response> delete() {
        return null;
    }

    @Override
    public <T> Future<T> delete(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> delete(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> delete(InvocationCallback<T> callback) {
        return null;
    }

    @Override
    public Future<Response> head() {
        return null;
    }

    @Override
    public Future<Response> head(InvocationCallback<Response> callback) {
        return null;
    }

    @Override
    public Future<Response> options() {
        return null;
    }

    @Override
    public <T> Future<T> options(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> options(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> options(InvocationCallback<T> callback) {
        return null;
    }

    @Override
    public Future<Response> trace() {
        return null;
    }

    @Override
    public <T> Future<T> trace(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> trace(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> trace(InvocationCallback<T> callback) {
        return null;
    }

    @Override
    public Future<Response> method(String name) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.method(name));
    }

    @Override
    public <T> Future<T> method(String name, Class<T> responseType) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.method(name, responseType));
    }

    @Override
    public <T> Future<T> method(String name, GenericType<T> responseType) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.method(name, responseType));
    }

    @Override
    public <T> Future<T> method(String name, InvocationCallback<T> callback) {
        return null;
    }

    @Override
    public Future<Response> method(String name, Entity<?> entity) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.method(name, entity));
    }

    @Override
    public <T> Future<T> method(String name, Entity<?> entity, Class<T> responseType) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.method(name, entity, responseType));
    }

    @Override
    public <T> Future<T> method(String name, Entity<?> entity, GenericType<T> responseType) {
        return CompletableFuture.supplyAsync(() -> defaultInvocationBuilder.method(name, entity, responseType));
    }

    @Override
    public <T> Future<T> method(String name, Entity<?> entity, InvocationCallback<T> callback) {
        return null;
    }
}
