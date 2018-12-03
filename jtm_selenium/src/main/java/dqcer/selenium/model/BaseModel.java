package dqcer.selenium.model;

import lombok.Data;

import java.util.LinkedList;

/**
 * @Author: dongQin
 * @Date: 2018/11/26 15:34
 * @Description: 基本通用信息
 */
@Data
public class BaseModel {

    private String exepath;

    private Long millis = 1000L;

    private String url;

    private String username;

    private String password;

    private LinkedList<Object> linkedList;

}
