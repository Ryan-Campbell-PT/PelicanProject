package UtilityClasses;

import java.awt.*;
import java.util.List;

/**
 * created as abstract because it allows us to do basic things like shared methods that
 * an interface doesnt allow
 */
public class Product
{
    //Product_inventory schema
    //p_id (int), p_name (String), p_size (String), color (String), p_detail (String),
    //price (double), admin_cost (double), stock (int), catalog_number (int), p_desc (String), p_imagePath (String)

    private List <String> productDetails;

    private int p_id;
    public int getId() { return p_id; }
    public void setId (int id) {this.p_id = id;}

    private Image image;
    protected void setImage(Image i) { this.image = i; }

    private String image_path;
    public String getImagePath(){return image_path;}
    public void setImagePath(String s){this.image_path = s;}

    private String p_name;
    public String getName() {return p_name;}
    public void setName(String n) {this.p_name = n;}

    private String p_size;
    public String getSize() {return p_size;}
    public void setSize(String size) {this.p_size = size;}

    private String color;
    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}

    private String p_detail;
    public String getDetail () {return p_detail;}
    public void setDetail (String detail) {this.p_detail = detail;}

    private Double price;
    public Double getPrice () {return price;}
    public void setPrice (Double price) {this.price = price;}

    private Double admin_cost;
    public Double getCost() { return admin_cost;}
    public void setCost(Double cost){this.admin_cost = cost;}

    private int stock;
    public int getStock() {return stock;}
    public void setStock (int stock) {this.stock = stock;}

    private int catalog_number;
    public int getCatalog() {return catalog_number;}
    public void setCatalog(int catalog) {this.catalog_number = catalog;}

    private String desc;
    public String getDesc () {return desc;}
    public void setDesc (String desc) {this.desc = desc;}

    public Product getProduct(){
        return this;
    }

    public List<String> getProductDetails(){
        productDetails.add (0, Integer.toString(p_id));
        productDetails.add(1, p_name);
        productDetails.add(2, p_size);
        productDetails.add(3, color);
        productDetails.add(4, p_detail);
        productDetails.add(5, Double.toString(price));
        productDetails.add(6, Double.toString(admin_cost));
        productDetails.add(7, Integer.toString(stock));
        productDetails.add(8, Integer.toString(catalog_number));
        productDetails.add(9, desc);
        productDetails.add(10, image_path);
        return productDetails;
    }
}
