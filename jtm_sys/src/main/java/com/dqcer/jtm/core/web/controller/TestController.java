package com.dqcer.jtm.core.web.controller;

import com.dqcer.jtm.core.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hi")
    public Result test(){
        return new Result("good job!");
    }
}
