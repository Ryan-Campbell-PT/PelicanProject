package UtilityClasses;

import sun.util.resources.cldr.ml.CalendarData_ml_IN;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Random;

/**
 * created as abstract because it allows us to do basic things like shared methods that
 * an interface doesnt allow
 */
public class Product
{
    //Product_inventory schema
    //p_id (int), p_name (String), p_size (String), color (String), p_detail (String),
    //price (double), admin_cost (double), stock (int), catalog_number (int), p_desc (String), p_imagePath (String)

    private int Id;
    public int getId() { return Id; }
    public void setId (int id) {this.Id = id;}

    private Image image;
    protected void setImage(Image i) { this.image = i; }

    private String imagePath;
    public String getImagePath(){return imagePath;}
    public void setImagePath(String s){this.imagePath = s;}

    private String name;
    public String getName() {return name;}
    public void setName(String n) {this.name = n;}

    private String size;
    public String getSize() {return size;}
    public void setSize(String size) {this.size = size;}

    private String color;
    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}

    private String detail;
    public String getDetail () {return detail;}
    public void setDetail (String detail) {this.detail = detail;}

    private Double price;
    public Double getPrice () {return price;}
    public void setPrice (Double price) {this.price = price;}

    private Double cost;
    public Double getCost() { return cost;}
    public void setCost(Double cost){this.cost = cost;}

    private int stock;
    public int getStock() {return stock;}
    public void setStock (int stock) {this.stock = stock;}

    private int catalog;
    public int getCatalog() {return catalog;}
    public void setCatalog(int catalog) {this.catalog = catalog;}

    private String desc;
    public String getDesc () {return desc;}
    public void setDesc (String desc) {this.desc = desc;}

    public updateProduct();
}
