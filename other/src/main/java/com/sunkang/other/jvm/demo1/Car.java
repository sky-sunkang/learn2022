package com.sunkang.other.jvm.demo1;

import java.awt.*;

public class Car {
    public static void main(String[] args) throws AWTException {
        Car car=new Car();
        System.out.println(car.getClass().getClassLoader());
        System.out.println(car.getClass().getClassLoader().getParent());
        System.out.println(car.getClass().getClassLoader().getParent().getParent());
        Robot robot = new Robot();
        robot.mouseMove(200,200);
    }
}
