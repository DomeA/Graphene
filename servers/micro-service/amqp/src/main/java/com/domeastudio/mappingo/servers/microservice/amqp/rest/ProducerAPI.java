package com.domeastudio.mappingo.servers.microservice.amqp.rest;

import com.domeastudio.mappingo.servers.microservice.amqp.producer.TestSender;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/rabbit")
public class ProducerAPI {
    @Autowired
    private TestSender testSender;


    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public void hello() {
        testSender.sendMsg("userQueue","user dddd");
    }
}
