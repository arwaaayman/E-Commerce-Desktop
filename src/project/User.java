/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdelrahman Yasser
 */
public class User extends Account {

    private int id, ssn;
    private String address;
    private CreditCard card;
    private static User instance = null;

    private User() {
    }

    @Override
    public void add_product(Product product) {
        try {
            // TODO add product to cart.
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("insert into cart (product_id, product_amount, product_name, product_price, user_id)" + " values (?, ?, ?, ?, ?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setInt(1, product.getId());
            preparedStmt.setInt(2, product.getAmount());
            preparedStmt.setString(3, product.getName());
            if (product.getPrice() == product.getPrice_after_sale()) {
                preparedStmt.setFloat(4, product.getPrice());
            } else {
                preparedStmt.setFloat(4, product.getPrice_after_sale());
            }
            preparedStmt.setInt(5, this.id);

            preparedStmt.execute();

//            dBHelper.closeConnection(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public void setCardType() {
        this.card.setType();
    }

    public void setCardNumber(int card_number) {
        this.card.setCard_number(card_number);
    }

    public Boolean checkUsername(String name) {

        try {
            // TODO add product to cart.
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("select * from designpattern.user where (name)=(?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt.setString(1, name);

            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            ResultSet rs = preparedStmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void SignUp(User u) {

        try {
            // TODO add product to cart.
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("insert into designpattern.user (ssn,name, password, address,card_number)" + " values (?, ?, ?, ?,?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();

            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setInt(1, u.getSsn());
            preparedStmt.setString(2, u.getName());
            preparedStmt.setString(3, u.getPassword());
            preparedStmt.setString(4, u.getAddress());
            preparedStmt.setInt(5, u.getCard().getCard_number());

            dBHelper.setQuery("insert into designpattern.card (card_number,balance, cart_type)" + " values (?, ?, ?)");
            preparedStmt = dBHelper.getPreparedStmt();

            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setInt(1, u.getCard().getCard_number());
            preparedStmt.setInt(5, 2000);
            preparedStmt.setString(2, u.getCard().getType());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
