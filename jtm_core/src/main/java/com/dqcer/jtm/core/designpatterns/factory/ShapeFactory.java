package com.dqcer.jtm.core.designpatterns.factory;

public class ShapeFactory {

    public Shape getShape(String shapeType){
        if (null == shapeType){
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLEENTITY")){
            return new CircleEntity();
        } else if (shapeType.equalsIgnoreCase("RECTANGLEENTITY")){
            return new RectangleEntity();
        }else if (shapeType.equalsIgnoreCase("SQUAREENTITY")){
            return new SquareEntity();
        }
        return null;
    }
}
