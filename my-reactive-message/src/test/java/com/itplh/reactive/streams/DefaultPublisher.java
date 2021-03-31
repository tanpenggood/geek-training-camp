package com.itplh.reactive.streams;

import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * 生产型接口，{@link Publisher} 类比 {@link Supplier}
 * 处理型接口，{@link Processor} 类比 {@link Function}
 * 消费型接口，{@link Subscriber} 类比 {@link Consumer}
 *
 * @author: tanpenggood
 * @date: 2021-03-31 21:18
 */
public class DefaultPublisher<T> implements Publisher<T> {

    private List<SubscriberWrapper> subscriberList = new LinkedList<>();

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        DefaultSubscription subscription = new DefaultSubscription(subscriber);
        // 使subscriber与subscription建立关系
        subscriber.onSubscribe(subscription);
        subscriberList.add(new SubscriberWrapper(subscriber, subscription));
    }

    /**
     * 给所有订阅者广播数据
     *
     * @param data
     */
    public void publish(T data) {
        subscriberList.forEach(subscriberWrapper -> {
            // 背压检测
            DefaultSubscription subscription = subscriberWrapper.getSubscription();
            subscription.request(1);
            if (subscription.isCanceled()) {
                System.err.println("本次数据发布已忽略，数据为：" + data);
                return;
            }
            // 发送数据
            Subscriber subscriber = subscriberWrapper.getSubscriber();
            subscriber.onNext(data);
        });
    }

    public static void main(String[] args) {
        DefaultPublisher<Object> publisher = new DefaultPublisher<>();
        publisher.subscribe(new DefaultSubscriber<>());

        // 发送数据
        IntStream.range(1, 6).forEach(n -> publisher.publish(n));
    }

}
