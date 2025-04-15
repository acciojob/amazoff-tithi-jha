package com.driver;


import java.util.UUID;

public class Order {

    private String id;
    private int deliveryTime;
    private static int nextavailibilty=0;


    public Order(String id, String deliveryTime,int nextavailibilty) {
        this.id= UUID.randomUUID().toString();

        // Parse HH:MM to minutes
        String[] parts = deliveryTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int timeInMinutes = hours * 60 + minutes;

        if(nextavailibilty>= 24 * 60){
           throw new RuntimeException("All delivery times are booked for the day.");
        }
        this.deliveryTime=timeInMinutes;
        nextavailibilty++;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
