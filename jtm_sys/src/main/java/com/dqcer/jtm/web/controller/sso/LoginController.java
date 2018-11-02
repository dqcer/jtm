package com.dqcer.jtm.web.controller.sso;

import com.dqcer.jtm.web.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dongQin
 * @Date: 2018/11/2 10:04
 * @Description: 登录入口
 */

@RestController
@RequestMapping("/sso")
public class LoginController extends BaseController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }

}
