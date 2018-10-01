/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Byron
 */
public class PaystationMain {

    /**
     * @param args the command line arguments
     */
    static Scanner keyboard = new Scanner(System.in);
    static String input;
    static PayStation ps = new PayStationImpl();
    
    public static void main(String[] args) {
        
        //accept user input   
        String option;
        printMenu();
        while (keyboard.hasNextLine()) {
            input = keyboard.nextLine();
            //valldate input
            option = input.toLowerCase().trim();
            switch (option) {
                case "1":
                    acceptCoins();
                    break;
                case "2":
                    System.out.println(ps.readDisplay() + " Minutes purchased");
                    break;
                case "3":
                    ps.buy();
                    break;
                case "4":
                    cancel();
                    break;
                case "5":
                    //change rate
                    break;
                default:
                    System.out.println("Invalid entry");
                    break;
            }
            printMenu();
        }   
    }

    private static void acceptCoins() {
        int coin;
        System.out.println("Please insert 5, 10, 25 coins (enter q to quit):");
        while(keyboard.hasNextLine()){
            input = keyboard.nextLine(); //get input
            input = input.trim();
            
            //quit if user enters q
            if(input.compareTo("q") == 0)
                break;
            try {
                coin = Integer.parseInt(input);
                //policy
                ps.addPayment(coin);
//                if (coin == 5 || coin == 10 || coin == 25) {
//                    System.out.println("accepted");
//                }else
//                    System.out.println("Invalid entry");
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid entry.");
            } catch (IllegalCoinException ex) {
                System.out.println("Error: Please enter only 5, 10, or 25.");
            }
            System.out.println("Please insert 5, 10, 25 coins (enter q to quit):");
        }
    }

    private static void printMenu() {
        System.out.println("______________________________\n\tMain Menu\n______________________________");
        System.out.println("1. Deposit Coins");
        System.out.println("2. Display");
        System.out.println("3. Buy Ticket");
        System.out.println("4. Cancel");
        System.out.println("5. Change Rate\n");
        
        System.out.println("Please enter a number: ");
    }

    private static void cancel() {
        Map coins = ps.cancel();
        
        int nickels = getCoins(coins,5);
        int dimes = getCoins(coins,10);
        int quarters = getCoins(coins,25);
        int total = (nickels * 5) + (dimes * 10) + (quarters * 25);

        if (total != 0) {
            System.out.println("______________________________\nPurchase canceled.");
            System.out.println("Total:\t" + total);
            System.out.println("\t" + quarters + " quarters");
            System.out.println("\t" + dimes + " dimes");
            System.out.println("\t" + nickels + " nickels");            
        } else {
            System.out.println("Nothing Purchased");
        }
    }

    private static int getCoins(Map coinMap, int key) {
        Object num; //.get function returns an object
        
        //PayStation implementation only place numbers in the values for each key.
        //if null is returned, There are 0 coins for that particular key.
        if((num = coinMap.get(key)) == null)
            return 0;
        else // if it is not null. the value is a number, therefore it is safe to cast
            return (int)num;
    }
    
}
