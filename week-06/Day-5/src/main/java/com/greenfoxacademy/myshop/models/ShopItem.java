package com.greenfoxacademy.myshop.models;

public class ShopItem implements Comparable<ShopItem>{

    public String name;
    private double price;
    private String description;
    private ItemType type;
    private int stock;

    public ShopItem(String name){
        this.name = name;
        this.description ="";
        this.price=0;
        this.stock=0;
        this.type=ItemType.undefined;
    }

    public ShopItem(String name, String description, double price, int stock, ItemType type){
        this.name = name;
        this.description = description;
        this.price=price;
        this.stock= stock;
        this.type=type;
    }

    public String getTypeLabel() {
        return type.label;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public int compareTo(ShopItem s) {
        return (getPrice()-s.getPrice()<0)?-1:1;
    }

    public ItemType getType() {
        return type;
    }
}
