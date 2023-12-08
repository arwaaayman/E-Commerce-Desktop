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

/**
 *
 * @author Abdelrahman Yasser
 */
public abstract class Account {
    
    private String name, password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public abstract void add_product(Product product);
    
    public static int Login(String name, String password) {
        try {
            DBHelper dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select * from user where (name,password)=(?,?)");
            PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
            preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, password);
            ResultSet rs = preparedStmt.executeQuery();

            if (rs.next()) {
                User user = User.getInstance();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setAddress(rs.getString(5));
                user.setSsn(rs.getInt(2));
                
                int car_number = rs.getInt(6);
                String card_type = "";
                Connection myconnection = null;
                Statement mystatement = null;
                ResultSet myresultset = null;
                PreparedStatement preparedStmt2 = null;

                String query = "select * from card where card_number = (?)";
                myconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/designpattern", "root", "");

                preparedStmt2 = myconnection.prepareStatement(query);
                preparedStmt2.setInt(1, car_number);
                ResultSet rs2 = preparedStmt2.executeQuery();

                if (rs2.next()) {
                    card_type = rs2.getString(3);
                }
                user.setCard(CardFactory.getCard(card_type));
                user.setCardNumber(car_number);
                user.setCardType();
                return Integer.parseInt(rs.getString(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
