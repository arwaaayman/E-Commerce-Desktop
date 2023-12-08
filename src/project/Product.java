/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdelrahman Yasser
 */
public class Product {

    private int id, category_id, amount;
    private float price_after_sale, price;
    private String name;
    private ArrayList<Feedback> feedbacks;
    private String image1, image2;

    public Product() {
        feedbacks = new ArrayList<Feedback>();
    }

    public Product(int id, float price, int category_id, int amount, float price_after_sale, String name, ArrayList<Feedback> feedbacks, String image1, String image2) {
        this.id = id;
        this.price = price;
        this.category_id = category_id;
        this.amount = amount;
        this.price_after_sale = price_after_sale;
        this.name = name;
        this.feedbacks = feedbacks;
        this.image1 = image1;
        this.image2 = image2;
    }

    public Product(int id, float price, int category_id, int amount, float price_after_sale, String name, String image1, String image2) {
        this.id = id;
        this.price = price;
        this.category_id = category_id;
        this.amount = amount;
        this.price_after_sale = price_after_sale;
        this.name = name;
        this.image1 = image1;
        this.image2 = image2;
    }
    
    public Product(int id, float price, int category_id, int amount, String name, String image1, String image2) {
        this.id = id;
        this.price = price;
        this.category_id = category_id;
        this.amount = amount;
        this.name = name;
        this.image1 = image1;
        this.image2 = image2;
    }
    
    public Product(int id, float price, int amount, String name) {
        this.id = id;
        this.price = price;
        this.amount = amount;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(ArrayList<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public float getPrice_after_sale() {
        return price_after_sale;
    }

    public void setPrice_after_sale(float price_after_sale) {
        this.price_after_sale = price_after_sale;
    }

    public ArrayList<Product> get_products(int cat_id) {

        ArrayList<Product> products = new ArrayList<>();
        DBHelper dBHelper;
        try {
            dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select * from product where (category_id)=(?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setInt(1, cat_id);
            dBHelper.setMyresultset(preparedStmt.executeQuery());

            dBHelper.setMyresultSetMetaData(dBHelper.getMyresultset().getMetaData());
            int columnCount = dBHelper.getMyresultSetMetaData().getColumnCount();

            while (dBHelper.getMyresultset().next()) {
                products.add(new Product(dBHelper.getMyresultset().getInt("id"), 
                        dBHelper.getMyresultset().getFloat("price"), 
                        dBHelper.getMyresultset().getInt("category_id"), 
                        dBHelper.getMyresultset().getInt("amount"), 
                        dBHelper.getMyresultset().getFloat("price_after_sale"),
                        dBHelper.getMyresultset().getString("name"),
                        dBHelper.getMyresultset().getString("image1"), 
                        dBHelper.getMyresultset().getString("image2")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }
    
    public ArrayList<Product> get_products() {

        ArrayList<Product> products = new ArrayList<>();
        DBHelper dBHelper;
        try {
            dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select * from product");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            dBHelper.setMyresultset(preparedStmt.executeQuery());

            dBHelper.setMyresultSetMetaData(dBHelper.getMyresultset().getMetaData());
            int columnCount = dBHelper.getMyresultSetMetaData().getColumnCount();

            while (dBHelper.getMyresultset().next()) {
                products.add(new Product(dBHelper.getMyresultset().getInt("id"), 
                        dBHelper.getMyresultset().getFloat("price"), 
                        dBHelper.getMyresultset().getInt("category_id"), 
                        dBHelper.getMyresultset().getInt("amount"), 
                        dBHelper.getMyresultset().getFloat("price_after_sale"),
                        dBHelper.getMyresultset().getString("name"),
                        dBHelper.getMyresultset().getString("image1"), 
                        dBHelper.getMyresultset().getString("image2")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }

        return products;
//    }
}
    
    public ArrayList<Feedback> get_feedbacks(int product_id){
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        DBHelper dBHelper;
        try {
            dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select * from feedback where (product_id)=(?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setInt(1, product_id);
            dBHelper.setMyresultset(preparedStmt.executeQuery());

            while (dBHelper.getMyresultset().next()) {
                feedbacks.add(new Feedback(dBHelper.getMyresultset().getInt("user_id"), 
                        dBHelper.getMyresultset().getString("text")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return feedbacks;
    }
    
    public void add_feedback(Feedback feedback){
        try {
            // TODO add product to cart.
            DBHelper dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("insert into feedback (user_id, product_id, text)"+ " values (?, ?, ?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setInt(1, feedback.getUser_id());
            preparedStmt.setInt(2, feedback.getProduct_id());
            preparedStmt.setString(3, feedback.getText());
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
