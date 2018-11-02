package com.dqcer.jtm.controller;

import com.dqcer.jtm.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dongQin
 * @Date: 2018/11/2 9:38
 * @Description: 系统要负载均衡的接口
 */

@RestController
public class SysController {

    @Autowired
    private SysService sysService;

    @GetMapping("/hi")
    public String hi(){
        return sysService.testServer();
    }


}
