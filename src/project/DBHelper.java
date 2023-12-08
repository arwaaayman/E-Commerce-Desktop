/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author Abdelrahman Yasser
 */
public class DBHelper {

    public static Connection myconnection = null;
    public static Statement mystatement = null;
    public static ResultSet myresultset = null;
    public static ResultSetMetaData myresultSetMetaData = null;
    public static PreparedStatement preparedStmt = null;
    public static String query;

    private static DBHelper instance = null;

    public static DBHelper getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBHelper();
            myconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/designpattern", "root", "");
            mystatement = myconnection.createStatement();
        }
        return instance;
    }

    public Connection getMyconnection() {
        return myconnection;
    }

    public void setMyconnection(Connection myconnection) {
        this.myconnection = myconnection;
    }

    public Statement getMystatement() {
        return mystatement;
    }

    public void setMystatement(Statement mystatement) {
        this.mystatement = mystatement;
    }

    public ResultSet getMyresultset() {
        return myresultset;
    }

    public void setMyresultset(ResultSet myresultset) {
        this.myresultset = myresultset;
    }

    public ResultSetMetaData getMyresultSetMetaData() {
        return myresultSetMetaData;
    }

    public void setMyresultSetMetaData(ResultSetMetaData myresultSetMetaData) {
        this.myresultSetMetaData = myresultSetMetaData;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public static PreparedStatement getPreparedStmt() {
        return preparedStmt;
    }

    public static void setPreparedStmt(PreparedStatement preparedStmt) {
        DBHelper.preparedStmt = preparedStmt;
    }

    public int rowCount() throws SQLException {
        return myresultset.getRow();
    }
    
    public void closeConnection() throws SQLException{
        myconnection.close();
    }
    
}
