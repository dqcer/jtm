package com.dqcer.jtm.core.designpatterns.factory;

public class RectangleEntity implements Shape {
    @Override
    public void draw() {
        System.out.println("RectangleEntity draw() method");
    }
}
