package com.itplh.projects.user.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @author: tanpenggood
 * @date: 2021-03-24 21:18
 */
public abstract class AbstractConverter<T> implements Converter<T> {

    @Override
    public T convert(String value) throws IllegalArgumentException, NullPointerException {
        if (value == null) {
            throw new NullPointerException("The value must be not null!");
        }
        return doConvert(value);
    }

    protected abstract T doConvert(String value);
    
}
