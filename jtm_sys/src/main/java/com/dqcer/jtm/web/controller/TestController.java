package com.dqcer.jtm.web.controller;

import com.dqcer.jtm.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hi")
    public Result test(){
        return new Result("good job!");
    }
}
