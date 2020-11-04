package com.joe.controller;

import com.joe.commons.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Joe
 * @Description: 测试接口
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/r1",produces = "application/json; charset=utf-8")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Result test1() {
        return new Result("200","请求成功","订单1");
    }

    @GetMapping(value = "/r2",produces = "application/json; charset=utf-8")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Result test2() {
        return new Result("200","请求成功","订单2");
    }

    //无需登录
    @GetMapping("/noauth")
    public Result noauth() {
        return new Result("200","请求成功","noauth");
    }

    @RequestMapping("/name")
    public String name(String name){
        return "My name is " + name;
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello！I'm a. port：" + port;
    }

    @RequestMapping("/age")
    public String age(String age){
        return "I am " + age + " years old this year";
    }

    @RequestMapping("/routeAll")
    public String routeAll(String pass) {
        return "Can I pass? " + pass + "! port：" + port;
    }
}