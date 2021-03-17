package com.itplh.projects.user.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.util.Optional;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:30
 */
public class StringToLongConverter implements Converter<Long> {
    @Override
    public Long convert(String value) throws IllegalArgumentException, NullPointerException {
        return Optional.ofNullable(value).map(v -> Long.valueOf(v)).orElse(null);
    }
}
