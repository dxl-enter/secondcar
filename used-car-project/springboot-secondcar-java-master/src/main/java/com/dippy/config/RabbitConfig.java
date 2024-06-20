package com.dippy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列
 */
@Configuration
public class RabbitConfig {

    public final static String ES_QUEUE = "es_queue";
    public final static String ES_EXCHANGE = "es_exchange";
    public final static String ES_BIND_KEY = "es_exchange";

    @Bean
    public Queue exQueue() {
        return new Queue(ES_QUEUE);
    }

    /**
     * 交换机
     * @return
     */
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(ES_EXCHANGE);
    }

    /**
     * 绑定
     * @param exQueue
     * @param exchange
     * @return
     */
    @Bean
    Binding binding(Queue exQueue, DirectExchange exchange) {
        return BindingBuilder.bind(exQueue).to(exchange).with(ES_BIND_KEY);
    }

}
