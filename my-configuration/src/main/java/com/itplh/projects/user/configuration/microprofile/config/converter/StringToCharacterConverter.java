package com.itplh.projects.user.configuration.microprofile.config.converter;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 22:30
 */
public class StringToCharacterConverter extends AbstractConverter<Character> {

    @Override
    protected Character doConvert(String value) {
        return Character.valueOf(value.charAt(0));
    }

}
