/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Abdelrahman Yasser
 */
public class ProxyUser extends Account {

    private User user = User.getInstance();

    public ProxyUser() {
    }

    @Override
    public void add_product(Product product) {
        try {

            DBHelper dBHelper = DBHelper.getInstance();
            dBHelper.setQuery("select * from designpattern.product where id = " + product.getId());
            dBHelper.setMyresultset(dBHelper.getMystatement().executeQuery(dBHelper.getQuery()));
            dBHelper.getMyresultset().next();

            if (product.getAmount() > dBHelper.getMyresultset().getInt("amount")) {
                ProductsForm form = new ProductsForm();
                JOptionPane.showMessageDialog(form, "Amount not enough");
            } else {
                user.add_product(product);

                int new_amount = dBHelper.getMyresultset().getInt("amount") - product.getAmount();
                try {
                    dBHelper = DBHelper.getInstance();
                    dBHelper.setQuery("update product set amount = '" + new_amount + "' where id = '" + product.getId() + "'");
                    PreparedStatement preparedStmt = dBHelper.getPreparedStmt();
                    preparedStmt = dBHelper.getMyconnection().prepareStatement(dBHelper.getQuery());
                    preparedStmt.execute();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProxyUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ProxyUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
