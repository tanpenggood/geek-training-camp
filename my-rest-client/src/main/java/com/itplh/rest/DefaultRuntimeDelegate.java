package com.itplh.rest;

import com.itplh.rest.client.DefaultVariantListBuilder;
import com.itplh.rest.core.DefaultResponseBuilder;
import com.itplh.rest.core.DefaultUriBuilder;
import com.itplh.rest.core.MediaTypeHeaderDelegate;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.RuntimeDelegate;

public class DefaultRuntimeDelegate extends RuntimeDelegate {

    @Override
    public UriBuilder createUriBuilder() {
        return new DefaultUriBuilder();
    }

    @Override
    public Response.ResponseBuilder createResponseBuilder() {
        return new DefaultResponseBuilder();
    }

    @Override
    public Variant.VariantListBuilder createVariantListBuilder() {
        return new DefaultVariantListBuilder();
    }

    @Override
    public <T> T createEndpoint(Application application, Class<T> endpointType) throws IllegalArgumentException, UnsupportedOperationException {
        return null;
    }

    @Override
    public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("type must be not null.");
        }
        if (MediaType.class.isAssignableFrom(type)) {
            return new MediaTypeHeaderDelegate();
        }
        return null;
    }

    @Override
    public Link.Builder createLinkBuilder() {
        return null;
    }
}
