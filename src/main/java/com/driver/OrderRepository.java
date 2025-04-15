package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        orderMap.put(order.getId(), order);
        // your code here
    }

    public void savePartner(String partnerId){
        DeliveryPartner partner=new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,partner);
        partnerToOrderMap.put(partnerId,new HashSet<>());
        // your code here
        // create a new partner with given partnerId and save it
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            partnerToOrderMap.get(partnerId).add(orderId); //many_to_one
            orderToPartnerMap.put(orderId, partnerId); //one_to_one

            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders() + 1);
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
        }
    }

    public Order findOrderById(String orderId){
         return orderMap.get(orderId);
        // your code here
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return partnerMap.get(partnerId);

    }

    public Integer findOrderCountByPartnerId(String partnerId){
        return partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).size();
        // your code here
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        return new ArrayList<>(partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()));
        // your code here
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        HashSet<String> orders=partnerToOrderMap.getOrDefault(partnerId,new HashSet<>());
        for(String x:orders){
            orderToPartnerMap.remove(x);
        }
        partnerToOrderMap.remove(partnerId);
        partnerMap.remove(partnerId);

    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID

            if(orderToPartnerMap.containsKey(orderId)){
                String partnerID = orderToPartnerMap.get(orderId);
                HashSet<String> orders = partnerToOrderMap.getOrDefault(partnerID, new HashSet<>());
                orders.remove(orderId);

                DeliveryPartner partner = partnerMap.get(partnerID);
                if(partner != null){
                    partner.setNumberOfOrders(partner.getNumberOfOrders() - 1);
                }

                orderToPartnerMap.remove(orderId);
            }
            orderMap.remove(orderId);


    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        return orderMap.size()-orderToPartnerMap.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int givenTime=Integer.parseInt(timeString.split(":")[0])*60+Integer.parseInt(timeString.split(":")[1]);
        int cnt=0;
        for(String x:partnerToOrderMap.getOrDefault(partnerId,new HashSet<>())){
            Order order=orderMap.get(x);
            int deliveryTime=order.getDeliveryTime();
            if(deliveryTime>givenTime){
                cnt++;
            }
        }
        return cnt;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        int maxTime=0;

        for(String x:partnerToOrderMap.getOrDefault(partnerId,new HashSet<>())){
            Order order=orderMap.get(x);
            int deliveryTime=order.getDeliveryTime();
            if(deliveryTime>maxTime){
                maxTime=deliveryTime;
            }
        }
        int hours=maxTime/60;
        int mins=maxTime%60;

        return String.format("%02d:%02d",hours,mins);
    }
}