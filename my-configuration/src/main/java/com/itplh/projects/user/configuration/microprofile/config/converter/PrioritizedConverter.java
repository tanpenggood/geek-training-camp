package com.itplh.projects.user.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * 可设置优先级的Converter
 */
public class PrioritizedConverter<T> implements Converter<T>, Comparable<PrioritizedConverter<T>> {

    private final Converter<T> converter;

    private final int priority;

    public PrioritizedConverter(Converter<T> converter, int priority) {
        this.converter = converter;
        this.priority = priority;
    }

    @Override
    public T convert(String value) throws IllegalArgumentException, NullPointerException {
        return converter.convert(value);
    }

    public int getPriority() {
        return priority;
    }

    public Converter<T> getConverter() {
        return converter;
    }

    @Override
    public int compareTo(PrioritizedConverter<T> other) {
        return Integer.compare(other.getPriority(), this.getPriority());
    }
}
