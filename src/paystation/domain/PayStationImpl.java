package paystation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar; 
    private int timeBought;
    private Map<Integer,Integer> coins; //map of coins to be returned IF user cancels

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: break;
            case 10: break;
            case 25: break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        reset();
        return r;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        //create map object
        Map<Integer,Integer> coins = new HashMap<Integer,Integer>(); // Map<> is an interface, cannot be Instantiated
        
        /*while(insertedSoFar > 25){
            insert
        }*/
        reset();
        return coins;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
    }
    public int empty(){
        //returns total of money collected by the paystation since the last call and empties it
        int total = insertedSoFar; //stored money collect to a variable before emptying it
        reset(); 
        
        //reset Map
        coins.clear(); //resets map
        return total;
    }
}
