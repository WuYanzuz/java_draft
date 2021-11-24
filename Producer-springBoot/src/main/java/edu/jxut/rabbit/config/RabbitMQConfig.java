package edu.jxut.rabbit.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME="boot_topic_exchange";
    public static final String QUEUE_NAME="boot_queue";
    //1交换机
    @Bean("BootExchange")
    public Exchange BootExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //2队列
    @Bean("BootQueue")
    public Queue BootQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean("BootQueue2")
    public Queue BootQueue2(){
        return QueueBuilder.durable("boot_queue2").build();
    }
    //3绑定关系
    @Bean
    public Binding BootBinding(@Qualifier("BootQueue") Queue queue,@Qualifier("BootExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

    @Bean
    public Binding BootBinding2(@Qualifier("BootQueue2") Queue queue,@Qualifier("BootExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("redis.#").noargs();
    }

}
