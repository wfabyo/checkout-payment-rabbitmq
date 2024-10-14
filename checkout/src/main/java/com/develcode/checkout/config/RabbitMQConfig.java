package com.develcode.checkout.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue paymentResultQueue() {
        return new Queue("payment-result-queue", true);
    }

    @Bean
    public Queue internalErrorQueue() {
        return new Queue("internal-error-queue", true);
    }

    @Bean
    public Queue noticeCustomerQueue() {
        return new Queue("notice-customer-queue", true);
    }

    @Bean
    public TopicExchange internalErrorExchange() {
        return new TopicExchange("internal-error-exchange");
    }

    @Bean
    public TopicExchange noticeCustomerExchange() {
        return new TopicExchange("notice-customer-exchange");
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange("payment-exchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(paymentResultQueue()).to(paymentExchange()).with("payment.result");
    }

    @Bean
    public Binding internalErrorBinding() {
        return BindingBuilder.bind(internalErrorQueue()).to(internalErrorExchange()).with("internal.error.notice");
    }

    @Bean
    public Binding noticeCustomerBinding() {
        return BindingBuilder.bind(noticeCustomerQueue()).to(noticeCustomerExchange()).with("customer.notice");
    }
} 
