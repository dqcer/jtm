package com.dqcer.jtm.core.designpatterns.factory;

public class CircleEntity implements Shape {
    @Override
    public void draw() {
        System.out.println("CircleEntity draw() method");
    }
}
