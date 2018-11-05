package com.dqcer.jtm.core.web;

import com.dqcer.jtm.core.util.Result;

public class BaseController {

    private Result result;

    private Result success(){
        return new Result("操作成功！");
    }
}
