package com.kenick.web;

import com.kenick.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
public class HelloController {
    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient client;

    @Value("${from}")
    private String from;

    @RequestMapping("/hello")
    public String hello(@RequestParam String name){
        logger.info("hello.HelloController.hello in");
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/hello,host:{},service_id:{},port:{}", instance.getHost(), instance.getServiceId(),instance.getPort());
        return "Hello " + name;
    }

    @RequestMapping("/getUserById")
    public User getUserById(Long id){
        User user = new User();
        user.setId(id);
        user.setAge(id.intValue());
        user.setName("name"+id);
        return user;
    }

    @RequestMapping(value ="/postUserSubmit",method = RequestMethod.POST)
    public String postUserSubmit(@RequestBody User user){
        System.out.println("提交的user为:" + user.toString());
        return user.toString()+"已提交!";
    }

    @RequestMapping("/from")
    public String from(){
        return this.from;
    }
}
