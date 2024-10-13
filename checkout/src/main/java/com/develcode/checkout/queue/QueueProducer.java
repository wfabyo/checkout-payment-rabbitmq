package com.develcode.checkout.queue;

public interface QueueProducer {
    void produceErrorNotification();

    void produceOrderStatusNotification();
}
