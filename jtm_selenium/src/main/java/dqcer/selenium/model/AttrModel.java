package dqcer.selenium.model;

import lombok.Data;

/**
 * @Author: dongQin
 * @Date: 2018/11/26 15:37
 * @Description: 属性信息
 */
@Data
public class AttrModel {

    private String xpath;

    private String method;

    private Long time = 0L;

    private String value = "自动添加内容";

}
