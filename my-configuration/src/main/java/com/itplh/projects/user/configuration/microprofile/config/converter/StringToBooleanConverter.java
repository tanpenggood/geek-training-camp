package com.itplh.projects.user.configuration.microprofile.config.converter;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:30
 */
public class StringToBooleanConverter extends AbstractConverter<Boolean> {

    @Override
    protected Boolean doConvert(String value) {
        return Boolean.parseBoolean(value);
    }

}
