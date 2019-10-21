public class ShoppingCart
{
    private String uniqueCartId; //every cart will have their own id to recognize it in the db
    private String uniqueCustomerId; //every cart will have the customer id associated with their customer

    public ShoppingCart(String cartId, String customerId)
    {
        this.uniqueCartId = cartId;
        this.uniqueCustomerId = customerId;
    }
}
