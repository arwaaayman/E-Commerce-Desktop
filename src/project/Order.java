/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Abdelrahman Yasser
 */
public class Order {

    private int id, user_id;
    private Date buying_date;
    private String status;
    private ArrayList<Product> products;

    public Order() {
        products = new ArrayList<Product>();
    }

    public void calcel_order(int order_id) {
        try {
            // TODO delete category.
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("delete from users_order where id = '" + order_id + "'");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void add_order(ArrayList<Product> products, float total) {
        Cart c = new Cart();
        User u = User.getInstance();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        int max_id = -1;
        try {
            DBHelper dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select max(id) from designpattern.users_order");
            dBHelper.setMyresultset(dBHelper.getMystatement().executeQuery(dBHelper.getQuery()));
            dBHelper.getMyresultset().next();
            max_id = dBHelper.getMyresultset().getInt(1) + 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        int user_balance = -1;
        try {
            DBHelper dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select * from designpattern.card where card_number = '" + u.getCard().getCard_number() + "'");
            dBHelper.setMyresultset(dBHelper.getMystatement().executeQuery(dBHelper.getQuery()));
            dBHelper.getMyresultset().next();
            user_balance = dBHelper.getMyresultset().getInt(2);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        
        if (user_balance >= total) {
            for (int i = 0; i < products.size(); i++) {
                try {

                    DBHelper dBHelper = DBHelper.getInstance();
                    dBHelper.setQuery("insert into users_order (id, product_id, product_name, product_price, user_id, buying_date, amount, status)" + " values (?, ?, ?, ?, ?, ?, ?, ?)");
                    PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
                    preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
                    preparedStmt.setInt(1, max_id);
                    preparedStmt.setInt(2, products.get(i).getId());
                    preparedStmt.setString(3, products.get(i).getName());
                    preparedStmt.setFloat(4, products.get(i).getPrice());
                    preparedStmt.setInt(5, u.getId());
                    preparedStmt.setString(6, dtf.format(now));
                    preparedStmt.setInt(7, products.get(i).getAmount());
                    preparedStmt.setString(8, "done");

                    preparedStmt.execute();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                try {
                    user_balance -= total;
                    DBHelper dBHelper = DBHelper.getInstance();
                    dBHelper.setQuery("UPDATE designpattern.card SET balance = ? WHERE card_number = ?");

                    PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
                    preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
                    preparedStmt.setFloat(1, user_balance);
                    preparedStmt.setInt(2, u.getCard().getCard_number());
                    preparedStmt.execute();
                } catch (Exception e) {
                }
            }
            c.delete_cart();
        } else {
            CartForm form = new CartForm();
            JOptionPane.showMessageDialog(form, "Balance not enough");
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getBuying_date() {
        return buying_date;
    }

    public void setBuying_date(Date buying_date) {
        this.buying_date = buying_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
