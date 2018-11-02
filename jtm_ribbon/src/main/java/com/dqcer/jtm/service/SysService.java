package com.dqcer.jtm.service;

import com.dqcer.jtm.constants.SysConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SysService {

    @Autowired
    RestTemplate restTemplate;

    public String testServer(){
        return restTemplate.getForObject(SysConstants.TEST.getValue(),String.class);

    }
}
