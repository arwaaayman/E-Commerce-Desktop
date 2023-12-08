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
public class CardFactory {

    public CardFactory() {
    }
    
    
    public static CreditCard getCard(String type){
    
        if(type == null){
            System.out.println("null1");
            return null;
        }else if(type.equalsIgnoreCase("Visa")){
            System.out.println("Visa");
            return new Visa();
        }else if(type.equalsIgnoreCase("Master")){
            System.out.println("Master");
            return new Master();
        }else{
            System.out.println("null1");
            return null;
        }
    }
    
}
