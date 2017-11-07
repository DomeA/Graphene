package com.domeastudio.mappingo.servers.microservice.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@ConfigurationProperties("spring.rabbitmq")
public class AmqpConfig {
    private Map<String,String[]> exchangesAndQueues;
    private Map<String,String> exchangesAndRoutingKeys;
    private Map<String,Boolean> queueNames;
    private Map<String,Boolean[]> topicExchanges;
    private Map<String,Boolean[]> fanoutExchanges;
    private Map<String,Boolean[]> directExchanges;
    private Map<String,Boolean[]> headersExchanges;

    public Map<String, String[]> getExchangesAndQueues() {
        return exchangesAndQueues;
    }

    public void setExchangesAndQueues(Map<String, String[]> exchangesAndQueues) {
        this.exchangesAndQueues = exchangesAndQueues;
    }

    public Map<String, String> getExchangesAndRoutingKeys() {
        return exchangesAndRoutingKeys;
    }

    public void setExchangesAndRoutingKeys(Map<String, String> exchangesAndRoutingKeys) {
        this.exchangesAndRoutingKeys = exchangesAndRoutingKeys;
    }

    public Map<String,Boolean> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(Map<String,Boolean> queueNames) {
        this.queueNames = queueNames;
    }

    public Map<String, Boolean[]> getTopicExchanges() {
        return topicExchanges;
    }

    public void setTopicExchanges(Map<String, Boolean[]> topicExchanges) {
        this.topicExchanges = topicExchanges;
    }

    public Map<String, Boolean[]> getFanoutExchanges() {
        return fanoutExchanges;
    }

    public void setFanoutExchanges(Map<String, Boolean[]> fanoutExchanges) {
        this.fanoutExchanges = fanoutExchanges;
    }

    public Map<String, Boolean[]> getDirectExchanges() {
        return directExchanges;
    }

    public void setDirectExchanges(Map<String, Boolean[]> directExchanges) {
        this.directExchanges = directExchanges;
    }

    public Map<String, Boolean[]> getHeadersExchanges() {
        return headersExchanges;
    }

    public void setHeadersExchanges(Map<String, Boolean[]> headersExchanges) {
        this.headersExchanges = headersExchanges;
    }

    @Bean
    public List<Queue> queues(){
        List<Queue> queueList=new ArrayList<>();
        Iterator<Map.Entry<String,Boolean>> iterator = queueNames.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Boolean> entry= iterator.next();
            Queue queue=new Queue(entry.getKey().replace("-","."),entry.getValue());
            queueList.add(queue);
        }
        return queueList;
    }

    @Bean
    public List<TopicExchange> topicExchanges() {
        List<TopicExchange> topicExchangeList=new ArrayList<>();
        Iterator<Map.Entry<String,Boolean[]>> iterator =topicExchanges.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Boolean[]> entry= iterator.next();
            TopicExchange topicExchange=new TopicExchange(entry.getKey().replace("-","."),entry.getValue()[0],entry.getValue()[1]);
            topicExchangeList.add(topicExchange);
        }
        return topicExchangeList;
    }

    @Bean
    public List<FanoutExchange> fanoutExchanges() {
        List<FanoutExchange> fanoutExchangeList=new ArrayList<>();
        Iterator<Map.Entry<String,Boolean[]>> iterator =fanoutExchanges.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Boolean[]> entry= iterator.next();
            FanoutExchange fanoutExchange=new FanoutExchange(entry.getKey().replace("-","."),entry.getValue()[0],entry.getValue()[1]);
            fanoutExchangeList.add(fanoutExchange);
        }
        return fanoutExchangeList;
    }

    @Bean
    public List<HeadersExchange> headersExchanges() {
        List<HeadersExchange> headersExchangeList=new ArrayList<>();
        Iterator<Map.Entry<String,Boolean[]>> iterator =headersExchanges.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Boolean[]> entry= iterator.next();
            HeadersExchange headersExchange=new HeadersExchange(entry.getKey().replace("-","."),entry.getValue()[0],entry.getValue()[1]);
            headersExchangeList.add(headersExchange);
        }
        return headersExchangeList;
    }

    @Bean
    public List<DirectExchange> directExchanges() {
        List<DirectExchange> directExchangeList=new ArrayList<>();
        Iterator<Map.Entry<String,Boolean[]>> iterator =directExchanges.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Boolean[]> entry= iterator.next();
            DirectExchange directExchange=new DirectExchange(entry.getKey().replace("-","."),entry.getValue()[0],entry.getValue()[1]);
            directExchangeList.add(directExchange);
        }
        return directExchangeList;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */

    /**
     * 将队列fanout.message与exchange绑定
     * @param queues
     * @param fanoutExchanges
     * @return
     */
    @Bean
    public List<Binding> bindingFanoutExchange(List<Queue> queues, List<FanoutExchange> fanoutExchanges) {
        List<Binding> bindingList=new ArrayList<>();
        for (Queue queue:queues) {
            for (FanoutExchange fanoutExchange: fanoutExchanges) {
                if(exchangesAndQueues.get(fanoutExchange.getName().replace(".","-"))!=null){
                    for (String queueName:exchangesAndQueues.get(fanoutExchange.getName().replace(".","-"))) {
                        if(queueName.equals(queue.getName())&&exchangesAndRoutingKeys.get(fanoutExchange.getName().replace(".","-"))!=null){
                            Binding binding=BindingBuilder.bind(queue).to(fanoutExchange);
                            bindingList.add(binding);
                        }
                    }
                }

            }
        }
        return bindingList;
    }

    /**
     * 将队列header.message与exchange绑定
     * @param queues
     * @param headersExchanges
     * @return
     */
    @Bean
    public List<Binding> bindingHeadersExchange(List<Queue> queues, List<HeadersExchange> headersExchanges) {
        List<Binding> bindingList=new ArrayList<>();
        for (Queue queue:queues) {
            for (HeadersExchange headersExchange: headersExchanges) {
                if(exchangesAndQueues.get(headersExchange.getName().replace(".","-"))!=null){
                    for (String queueName:exchangesAndQueues.get(headersExchange.getName().replace(".","-"))) {
                        if(queueName.equals(queue.getName())&&exchangesAndRoutingKeys.get(headersExchange.getName().replace(".","-"))!=null){
                            Binding binding=BindingBuilder.bind(queue).to(headersExchange).where(exchangesAndRoutingKeys.get(headersExchange.getName().replace(".","-"))).exists();
                            bindingList.add(binding);
                        }
                    }
                }
            }
        }
        return bindingList;
    }

    /**
     * 将队列direct.message与exchange绑定
     * @param queues
     * @param directExchanges
     * @return
     */
    @Bean
    public List<Binding> bindingDirectExchange(List<Queue> queues, List<DirectExchange> directExchanges) {
        List<Binding> bindingList=new ArrayList<>();
        for (Queue queue:queues) {
            for (DirectExchange directExchange: directExchanges) {
                if(exchangesAndQueues.get(directExchange.getName().replace(".","-"))!=null) {
                    for (String queueName : exchangesAndQueues.get(directExchange.getName().replace(".", "-"))) {
                        if (queueName.equals(queue.getName())&&exchangesAndRoutingKeys.get(directExchange.getName().replace(".","-"))!=null) {
                            Binding binding = BindingBuilder.bind(queue).to(directExchange).with(exchangesAndRoutingKeys.get(directExchange.getName().replace(".", "-")));
                            bindingList.add(binding);
                        }
                    }
                }
            }
        }
        return bindingList;
    }

    /**
     * 将队列topic.message与exchange绑定
     * @param queues
     * @param topicExchanges
     * @return
     */
    @Bean
    public List<Binding> bindingTopicExchange(List<Queue> queues, List<TopicExchange> topicExchanges) {
        List<Binding> bindingList=new ArrayList<>();
        for (Queue queue:queues) {
            for (TopicExchange topicExchange: topicExchanges) {
                if(exchangesAndQueues.get(topicExchange.getName().replace(".", "-"))!=null) {
                    for (String queueName : exchangesAndQueues.get(topicExchange.getName().replace(".", "-"))) {
                        if (queueName.equals(queue.getName())&&exchangesAndRoutingKeys.get(topicExchange.getName().replace(".","-"))!=null) {
                            Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(exchangesAndRoutingKeys.get(topicExchange.getName().replace(".", "-")));
                            bindingList.add(binding);
                        }
                    }
                }
            }
        }
        return bindingList;
    }
}
