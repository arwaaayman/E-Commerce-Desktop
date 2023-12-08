/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Abdelrahman Yasser
 */
public class Feedback {
    
    private int id, user_id, product_id;
    private String text;

    public Feedback(int user_id, String text) {
        this.user_id = user_id;
        this.text = text;
    }

    public Feedback(int id, int user_id, int product_id, String text) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.text = text;
    }

    public Feedback(int user_id, int product_id, String text) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

}
