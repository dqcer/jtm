package com.dqcer.jtm.constants;

public enum  SysConstants {

    TEST("http://JTM-SYS");

    private String value;

    SysConstants(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
