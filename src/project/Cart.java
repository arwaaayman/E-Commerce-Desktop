/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdelrahman Yasser
 */
public class Cart {

    private int user_id;
    private float total_price;
    private ArrayList<Product> products;

    public Cart() {
        products = new ArrayList<Product>();
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void delete_product(Product product) {
        try {
            User user = User.getInstance();
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("delete from cart where product_id = '" + product.getId() + "' and user_id = '" + user.getId() + "'");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.execute();

            dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select * from designpattern.product where id = " + product.getId());
            dBHelper.setMyresultset(dBHelper.getMystatement().executeQuery(dBHelper.getQuery()));
            dBHelper.getMyresultset().next();

            int amount = dBHelper.getMyresultset().getInt("amount");

            amount += product.getAmount();

            try {
                dBHelper = DBHelper.getInstance();

                dBHelper.setQuery("update product set amount = '" + amount + "' where id = '" + product.getId() + "'");
                preparedStmt = dBHelper.getPreparedStmt();
                preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
                preparedStmt.execute();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete_cart() {
        try {
            User user = User.getInstance();
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("delete from cart where user_id = '" + user.getId() + "'");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void get_cart() {

        try {
            User user = User.getInstance();
            DBHelper dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select * from designpattern.cart where user_id = " + user.getId());

            dBHelper.setMyresultset(dBHelper.getMystatement().executeQuery(dBHelper.getQuery()));

            total_price = 0;
            while (dBHelper.getMyresultset().next()) {
                products.add(new Product(dBHelper.getMyresultset().getInt("product_id"),
                        dBHelper.getMyresultset().getFloat("product_price"),
                        dBHelper.getMyresultset().getInt("product_amount"),
                        dBHelper.getMyresultset().getString("product_name")));
                total_price += (dBHelper.getMyresultset().getInt("product_amount") * dBHelper.getMyresultset().getInt("product_price"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProxyUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
