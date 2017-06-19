package com.rplanx.besstoo.getset;

/**
 * Created by soumya on 21/4/17.
 */
public class Locations {
    String location_id;
    String location_name;
    String location_city;
    String location_state;
    String user_location_name;
    String user_location_details;

    public  void  setUser_location_name(String user_location_name){
        this.user_location_name=user_location_name;
    }
    public  String getUser_location_name(){
        return  user_location_name;
    }

    public void  setUser_location_details(String user_location_details){
        this.user_location_details=user_location_details;
    }
    public  String getUser_location_details(){
        return  user_location_details;
    }


    public  void  setLocation_id(String location_id){
        this.location_id=location_id;
    }

    public String getLocation_id()
    {
        return  location_id;
    }

    public void  setLocation_name(String location_name){
        this.location_name=location_name;
    }
    public  String getLocation_name(){
        return location_name;
    }

    public  void setLocation_city(String location_city){
        this.location_city=location_city;
    }

    public  String getLocation_city(){
        return  location_city;
    }

    public  void  setLocation_state(String location_state){
        this.location_state=location_state;
    }

}
