package com.rplanx.besstoo.getset;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by soumya on 24/2/17.
 */
public class Model2  implements Parcelable {

    private  String string;
    private int quantity;
    private  String ruppee;
    private  String no_of_item;
    private  String description;
  //  private  String name;
    private  String str_food_name;
    String menu_id;
    String from_kitchen;
    String id;
    String date;
    String forDay;

    public Model2(Parcel in) {
        string = in.readString();
        quantity = in.readInt();
        ruppee = in.readString();
        no_of_item = in.readString();
        description = in.readString();
        id = in.readString();
        from_kitchen=in.readString();
        date=in.readString();
        menu_id=in.readString();
        forDay=in.readString();
    }

   /* public  void  setNames(String name){
        this.name=name;
    }

    public  String getNames(){
        return  name;
    }*/




    public static final Creator<Model2> CREATOR = new Creator<Model2>() {
        @Override
        public Model2 createFromParcel(Parcel in) {
            return new Model2(in);
        }

        @Override
        public Model2[] newArray(int size) {
            return new Model2[size];
        }
    };

    public Model2() {

    }

    public  void  setForDay(String forDay){
        this.forDay=forDay;
    }

    public  String getForDay(){
        return  forDay;
    }

    public  void setDate(String date){
        this.date=date;
    }
    public  String getDate(){
        return  date;
    }

    public  void  setMenu_id(String menu_id){
        this.menu_id=menu_id;
    }
    public  String getMenu_id(){
        return  menu_id;
    }

    public  void  setFrom_kitchen(String from_kitchen){
        this.from_kitchen=from_kitchen;
    }

    public  String getFrom_kitchen(){
        return from_kitchen;
    }

    public  void  setId(String id){
        this.id=id;
    }

    public  String getId(){
        return  id;
    }
    public  void  setStr_food_name(String food_name){
        this.str_food_name=food_name;
    }

    public String getStr_food_name(){
        return  str_food_name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(string);
        parcel.writeInt(quantity);
        parcel.writeString(ruppee);
        parcel.writeString(no_of_item);
        parcel.writeString(description);
        parcel.writeString(id);
        parcel.writeString(forDay);
    }
}
