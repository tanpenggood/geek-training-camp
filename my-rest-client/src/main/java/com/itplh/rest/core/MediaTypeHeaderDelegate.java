package com.itplh.rest.core;

import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.RuntimeDelegate;
import java.nio.charset.UnsupportedCharsetException;
import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: tanpenggood
 * @date: 2021-04-01 02:17
 */
public class MediaTypeHeaderDelegate<T> implements RuntimeDelegate.HeaderDelegate<MediaType> {

    @Override
    public MediaType fromString(String mimeType) {
        if (StringUtils.isEmpty(mimeType)) {
            throw new InvalidParameterException("'mimeType' must not be empty");
        } else {
            int index = mimeType.indexOf(59);
            String fullType = (index >= 0 ? mimeType.substring(0, index) : mimeType).trim();
            if (fullType.isEmpty()) {
                throw new InvalidParameterException("'mimeType' must not be empty");
            } else {
                if ("*".equals(fullType)) {
                    fullType = "*/*";
                }

                int subIndex = fullType.indexOf(47);
                if (subIndex == -1) {
                    throw new InvalidParameterException("does not contain '/'");
                } else if (subIndex == fullType.length() - 1) {
                    throw new InvalidParameterException("does not contain subtype after '/'");
                } else {
                    String type = fullType.substring(0, subIndex);
                    String subtype = fullType.substring(subIndex + 1);
                    if ("*".equals(type) && !"*".equals(subtype)) {
                        throw new InvalidParameterException("wildcard type is legal only in '*/*' (all mime types)");
                    } else {
                        LinkedHashMap parameters = null;

                        int nextIndex;
                        do {
                            nextIndex = index + 1;

                            for (boolean quoted = false; nextIndex < mimeType.length(); ++nextIndex) {
                                char ch = mimeType.charAt(nextIndex);
                                if (ch == ';') {
                                    if (!quoted) {
                                        break;
                                    }
                                } else if (ch == '"') {
                                    quoted = !quoted;
                                }
                            }

                            String parameter = mimeType.substring(index + 1, nextIndex).trim();
                            if (parameter.length() > 0) {
                                if (parameters == null) {
                                    parameters = new LinkedHashMap(4);
                                }

                                int eqIndex = parameter.indexOf(61);
                                if (eqIndex >= 0) {
                                    String attribute = parameter.substring(0, eqIndex).trim();
                                    String value = parameter.substring(eqIndex + 1).trim();
                                    parameters.put(attribute, value);
                                }
                            }

                            index = nextIndex;
                        } while (nextIndex < mimeType.length());

                        try {
                            return new MediaType(type, subtype, parameters);
                        } catch (UnsupportedCharsetException var13) {
                            throw new InvalidParameterException("unsupported charset '" + var13.getCharsetName() + "'");
                        } catch (IllegalArgumentException var14) {
                            throw new InvalidParameterException(var14.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString(MediaType mimeType) {
        StringBuilder builder = new StringBuilder();
        this.appendTo(mimeType, builder);
        return builder.toString();
    }

    private void appendTo(MediaType mimeType, StringBuilder builder) {
        builder.append(mimeType.getType());
        builder.append('/');
        builder.append(mimeType.getSubtype());
        this.appendTo(mimeType.getParameters(), builder);
    }

    private void appendTo(Map<String, String> map, StringBuilder builder) {
        map.forEach((key, val) -> {
            builder.append(';');
            builder.append(key);
            builder.append('=');
            builder.append(val);
        });
    }

}
