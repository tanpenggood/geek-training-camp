package com.itplh.projects.user.configuration.microprofile.config.converter;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:30
 */
public class StringToStringConverter extends AbstractConverter<String> {

    @Override
    protected String doConvert(String value) {
        return value;
    }

}
