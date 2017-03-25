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
    private  String name;
    private  String str_food_name;
    String id;

    public Model2(Parcel in) {
        string = in.readString();
        quantity = in.readInt();
        ruppee = in.readString();
        no_of_item = in.readString();
        description = in.readString();
        id = in.readString();
    }

    public  void  setNames(String name){
        this.name=name;
    }

    public  String getNames(){
        return  name;
    }

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
    }
}
