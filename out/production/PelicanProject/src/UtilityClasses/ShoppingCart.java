package UtilityClasses;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCart
{
    private String uniqueCartId; //every cart will have their own id to recognize it in the db
    private String uniqueCustomerId; //every cart will have the customer id associated with their customer
    private List < Pair <Product, Integer> > cart = new ArrayList<>();

    ShoppingCart(String cartId, String customerId)
    {
        this.uniqueCartId = cartId;
        this.uniqueCustomerId = customerId;
    }

    public boolean addToCart(Product p, Integer i){
        try {
            this.cart.add(new Pair<>(p, i));
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List < Pair <Product, Integer>  > getCart(){
        return this.cart;
    }

    public boolean emptyCart(){
        try {
            this.cart = null;
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
