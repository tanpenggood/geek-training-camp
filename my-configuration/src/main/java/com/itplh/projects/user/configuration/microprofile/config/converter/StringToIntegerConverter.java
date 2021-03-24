package com.itplh.projects.user.configuration.microprofile.config.converter;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:30
 */
public class StringToIntegerConverter extends AbstractConverter<Integer> {

    @Override
    protected Integer doConvert(String value) {
        return Integer.parseInt(value);
    }

}
