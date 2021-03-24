package com.itplh.projects.user.configuration.microprofile.config.converter;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:30
 */
public class StringToLongConverter extends AbstractConverter<Long> {

    @Override
    protected Long doConvert(String value) {
        return Long.parseLong(value);
    }

}
