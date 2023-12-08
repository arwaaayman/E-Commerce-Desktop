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
public class LowSale implements Sale{

    @Override
    public float getSale(float price) {
        return (float) (price*0.8);
    }
    
}