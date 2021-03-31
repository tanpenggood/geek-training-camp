package com.itplh.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.LongAdder;

/**
 * 用于做背压，客户端限流
 * Subscription 与 Subscriber 是一一对应
 *
 * @author: tanpenggood
 * @date: 2021-03-31 21:24
 */
public class DefaultSubscription implements Subscription {

    /**
     * 限流阀值
     */
    private static final long LIMIT = 2;

    /**
     * 计数器
     */
    private LongAdder count = new LongAdder();

    private boolean canceled = false;

    private final Subscriber subscriber;

    public DefaultSubscription(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        for (int i = 0; i < n; i++) {
            // 背压计算
            count.increment();
            if (count.longValue() > LIMIT) {
                this.cancel();
                return;
            }
        }
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }
}
