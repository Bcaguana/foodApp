package com.example.foodapp;

public class FoodItemCard {

    private String foodName;
    private String expirationDate;
    private String isGlutenFree;

    public FoodItemCard(String t1, String t2, String t3) {

        foodName = t1;
        expirationDate = t2;
        isGlutenFree = t3;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getIsGlutenFree() {return isGlutenFree; }
}

