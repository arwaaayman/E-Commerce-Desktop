/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Abdelrahman Yasser
 */
public class Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        User user = User.getInstance();
        user.setId(1);
//        Product product = new Product();
//        product.setId(1);
//        product.setName("Meat");
//        product.setAmount(3);
//        product.setPrice(200);
//        product.setCategory_id(1);

//        user.add_product(product);
//        ProxyUser proxyUser = new ProxyUser();
//        proxyUser.add_product(product);
//
//        Category category = new Category();
//        category.setId(2);
//        category.setName("Electronics1");
//        Admin admin = new Admin();
//        admin.delete_user(user.getId());
//        Cart cart = new Cart();
//        System.out.println("" + cart.calculate_total_receipt());
//        user.cancel_product(1, user.getId());
//        Order order = new Order();
//        order.calcel_order(1);
//        Admin a = new Admin();
//        a.add_sale();
//        SignUpForm form = new SignUpForm();
//        form.setVisible(true);
//ProductsForm form = new ProductsForm();
//form.setVisible(true);
//ProductsForm form = new ProductsForm();
//form.setVisible(true);
//AdminForm adminForm = new AdminForm();
//adminForm.setVisible(true);
        LoginForm login=new LoginForm();
        login.setVisible(true);

    }
}
