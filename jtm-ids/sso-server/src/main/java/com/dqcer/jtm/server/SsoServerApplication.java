package com.dqcer.jtm.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: dongQin
 * @Date: 2018/12/8 19:35
 * @Description: 单点登录入口
 */
@SpringBootApplication
public class SsoServerApplication {

	public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
	}

}