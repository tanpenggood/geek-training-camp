package com.itplh.projects.user.configuration.microprofile.config.converter;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:30
 */
public class StringToShortConverter extends AbstractConverter<Short> {

    @Override
    protected Short doConvert(String value) {
        return Short.parseShort(value);
    }

}
