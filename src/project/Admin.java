/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdelrahman Yasser
 */
public class Admin extends Account {

    @Override
    public void add_product(Product product) {
        // TODO add product to products.
        // TODO test add image.
        try {

            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("insert into product (id, name, amount, price, category_id, image1, image2, price_after_sale)" + " values (?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setInt(1, product.getId());
            preparedStmt.setString(2, product.getName());
            preparedStmt.setInt(3, product.getAmount());
            preparedStmt.setFloat(4, product.getPrice());
            preparedStmt.setInt(5, product.getCategory_id());
            preparedStmt.setString(6, product.getImage1());
            preparedStmt.setString(7, product.getImage2());
            preparedStmt.setFloat(8, (float) product.getPrice());

            preparedStmt.execute();

//            dBHelper.closeConnection(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void add_category(Category category) {
        try {
            // TODO add category
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("insert into category (id, name)" + " values (?, ?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setInt(1, category.getId());
            preparedStmt.setString(2, category.getName());
            preparedStmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void edit_category(Category category) {
        try {
            // TODO add product to cart.
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("update category set id = '" + category.getId() + "' ,name = '" + category.getName() + "' where id = '" + category.getId() + "'");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete_category(int category_id) {
        try {
            // TODO delete category.
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("delete from category where id = '" + category_id + "'");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete_user(int user_id) {
        try {
            // TODO delete user.
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("delete from user where id = '" + user_id + "'");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void add_sale() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String dayWeekText = new SimpleDateFormat("EEEE").format(date);

        try {
            DBHelper dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select id, price from designpattern.product");

            dBHelper.setMyresultset(dBHelper.getMystatement().executeQuery(dBHelper.getQuery()));

//        0     Sunday
//        1	Monday
//        2	Tuesday
//        3	Wednesday
//        4	Thursday
//        5	Friday
//        6	Saturday
//            final int x = dBHelper.rowCount();
            int id[] = new int[1000];
            float price[] = new float[1000];
            int j = 0;
            while (dBHelper.getMyresultset().next()) {
                id[j] = dBHelper.getMyresultset().getInt("id");
                price[j] = dBHelper.getMyresultset().getFloat("price");
                j++;
            }

            for (int i = 0; i < id.length; i++) {
                Sale sale;
                if (dayWeekText.equalsIgnoreCase("Friday")) {
                    sale = new HighSale();
                    price[i] = sale.getSale(price[i]);
                } else if (dayWeekText.equalsIgnoreCase("Saturday")) {
                    sale = new LowSale();
                    price[i] = sale.getSale(price[i]);
                } else {
                    sale = new NormalSale();
                    price[i] = sale.getSale(price[i]);
                }

                dBHelper.setQuery("update product set price_after_sale = '" + price[i] + "' where id = '" + id[i] + "'");

                PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
                preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
                preparedStmt.execute();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProxyUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public Object[][] getOrder(String date) {
        Object[][] obj = new Object[20][4];
        try {
            DBHelper dBHelper = DBHelper.getInstance();

            dBHelper.setQuery("select id,amount,product_price,status from designpattern.users_order where (buying_date)=(?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();

            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setString(1, date);
            ResultSet rs = preparedStmt.executeQuery();
            int row = 0;
            while (rs.next()) {
                obj[row][0] = rs.getString(1);
                obj[row][1] = rs.getString(2);
                obj[row][2] = rs.getString(3);
                obj[row][3] = rs.getString(4);
                row++;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return obj;
    }
}
