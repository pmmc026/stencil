package com.exemplo.demo3;

public class Character {
    
    private String name;
    private String skinPath;
    private int xpPoints;
    private int id;

    public Character(int id, String name, String skinPath, int xpPoints) {
        this.id = id;
        this.name = name;
        this.skinPath = skinPath;
        this.xpPoints = xpPoints;
    }

    public Character(String name, String skinPath, int xpPoints) {
        this.name = name;
        this.skinPath = skinPath;
        this.xpPoints = xpPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getSkinPath() {
        return skinPath;
    }
    public int getXpPoints() {
        return xpPoints;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }
    public void setXpPoints(int xpPoints) {
        this.xpPoints = xpPoints;
    }
    public int getLevel() {
        return 1 + xpPoints / 100;
    }

}
