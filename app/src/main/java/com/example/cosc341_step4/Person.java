// Person.java
package com.example.cosc341_step4;

public class Person {
    private String name;
    private String color;  // Changed from Color to String

    public Person(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}