package com.dqcer.jtm.core.designpatterns.factory;

public class SquareEntity implements Shape {
    @Override
    public void draw() {
        System.out.println("SquareEntity draw() method");
    }
}
