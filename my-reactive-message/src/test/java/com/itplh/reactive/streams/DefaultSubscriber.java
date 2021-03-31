package com.itplh.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Consumer;

/**
 * 消费型接口，{@link Subscriber} 类比 {@link Consumer}
 * Subscriber 与 Subscription 是一一对应
 *
 * @author: tanpenggood
 * @date: 2021-03-31 21:21
 */
public class DefaultSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(T t) {
        System.out.println("收到数据：" + t);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("遇到异常：" + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("收到数据完成");
    }

}
