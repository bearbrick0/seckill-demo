//package com.yun.seckilldemo.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.amqp.core.Queue;
//
///**
// * @author wanglufei
// * @title: RabbitConfig
// * @projectName seckill-demo
// * @description: TODO
// * @date 2021/11/8/3:21 下午
// */
//@Configuration
//public class RabbitConfigFault {
//
//    private static final String  QUEUE01 ="queue_fanout01";
//    private static final String  QUEUE02 ="queue_fanout02";
//    private static final String  EXCHANGE ="fanoutExchange";
//    @Bean
//    public Queue queue(){
//        return new Queue("queue",true);
//    }
//    @Bean
//    public Queue queue01(){
//        return new Queue(QUEUE01);
//    }
//    @Bean
//    public Queue queue02(){
//        return new Queue(QUEUE02);
//    }
//    @Bean
//    public FanoutExchange fanoutExchange(){
//        return new FanoutExchange(EXCHANGE);
//    }
//
//    @Bean
//    public Binding binding01(){
//        return BindingBuilder.bind(queue01()).to(fanoutExchange());
//    }
//    @Bean
//    public Binding binding02(){
//        return BindingBuilder.bind(queue02()).to(fanoutExchange());
//    }
//}
