package com.itplh.projects.user.configuration.microprofile.config.converter;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:30
 */
public class StringToFloatConverter extends AbstractConverter<Float> {

    @Override
    protected Float doConvert(String value) {
        return Float.parseFloat(value);
    }

}
