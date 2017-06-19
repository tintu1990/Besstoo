package com.rplanx.besstoo.getset;

/**
 * Created by soumya on 22/4/17.
 */
public class Party {

    String menu_name;
    String menu_id;
    String besstoo_kitchen_id;
    String amount;
    String image;
    String date;
    int quantity;
    String str_id;
    String no_of_item;

    public  void  setMenu_name(String menu_name){
        this.menu_name=menu_name;
    }
    public  String getMenu_name(){
        return  menu_name;
    }
    public  void setMenu_id(String menu_id){
      this.menu_id=menu_id;
    }
    public String getMenu_id(){
        return menu_id;
    }

    public  void  setBesstoo_kitchen_id(String besstoo_kitchen_id){
        this.besstoo_kitchen_id=besstoo_kitchen_id;
    }
    public  String getBesstoo_kitchen_id(){
        return  besstoo_kitchen_id;
    }

    public  void  setAmount(String amount){
        this.amount=amount;

    }
    public  String getAmount(){
        return  amount;
    }

    public  void  setPartyImage(String image){
        this.image=image;
    }

    public String getPartyImage(){
        return  image;
    }

    public  void  setDate(String date){
        this.date=date;
    }
    public  String getDate(){
        return date;
    }

    public void  setQuantity(int quantity){
        this.quantity=quantity;
    }
    public  int getQuantity(){
        return quantity;
    }

    public  void  setStr_id(String str_id){
        this.str_id=str_id;
    }

    public  String getStr_id(){
        return  str_id;
    }
    public void  setNo_of_item(String no_of_item){
        this.no_of_item=no_of_item;
    }

    public  String getNo_of_item(){
        return no_of_item;
    }

}
