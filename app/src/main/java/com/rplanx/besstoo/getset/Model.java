package com.rplanx.besstoo.getset;

/**
 * Created by soumya on 14/3/17.
 */
public class Model {
    private  String string;
    private int quantity;
    private  String ruppee;
    private  String no_of_item;
    private  String description;
    private  String name;


    public  void  setNames(String name){
        this.name=name;
    }

    public  String getNames(){
        return  name;
    }

    public  void  setImage(String image){
        this.string=image;
    }
    public  String getImage(){
        return string;
    }
    public  void setQuantity(int quantity){
        this.quantity=quantity;
    }
    public  int getQuantity(){
        return quantity;
    }

    public  void setRuppee(String ruppee){
        this.ruppee=ruppee;
    }
    public  String getRuppee(){
        return  ruppee;
    }

    public void  setNo_of_item(String no_of_item){
        this.no_of_item=no_of_item;
    }

    public String getNo_of_item(){
        return no_of_item;
    }

    public  void  setDescription(String description){
        this.description=description;
    }
    public  String getDescription(){
        return description;
    }

}
