package com.rplanx.besstoo.getset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soumya on 10/3/17.
 */
public class ModelCart {

    private List<Model2> cartProducts = new ArrayList<>();

    public   void setCartProducts(List<Model2> cartProducts){
        this.cartProducts=cartProducts;
    }

    public Model2 getProducts_pos(int pPosition) {

        return cartProducts.get(pPosition);
    }


    public  List<Model2>getCartProducts(){
        return cartProducts;
    }

    public int getCartSize() {

        return cartProducts.size();

    }

    public boolean checkProductInCart(Model2 aProduct) {

        return cartProducts.contains(aProduct);

    }

}
