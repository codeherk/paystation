/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import java.util.Scanner;

/**
 *
 * @author Byron
 */
public class PaystationMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //accept user input
        Scanner keyboard = new Scanner(System.in);
        String s;
        System.out.println("Please enter a coin:");
        
        while (keyboard.hasNextLine()) {
            s = keyboard.nextLine();
            //valldate input
        }   
    }
    
}
