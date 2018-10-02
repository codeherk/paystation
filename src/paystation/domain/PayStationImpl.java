package paystation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * CIS 3296 - LAB 3
 * Byron Jenkins & Daniel Dubovoy
 * GITHUB - https://github.com/codeherk/paystation
 * 
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
 * This includes source code from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University 
 * 
 * The included source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */

public class PayStationImpl implements PayStation {
    
    private int insertedSoFar; 
    private int timeBought;
    // The rate calculation strategy used
    RateStrategy rateStrategy;

    
    public PayStationImpl(){
        rateStrategy = new LinearRateStrategy();
    }
    public PayStationImpl(RateStrategy rs){
        rateStrategy = rs;
    }
    private Map<Integer,Integer> coins = new HashMap<Integer,Integer>(); //map of coins to be returned IF user cancels

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
        addToMap(coinValue);
        timeBought = rateStrategy.calculateTime(insertedSoFar); //insertedSoFar / 5 * 2;
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
        //create map copy of coins then reset
        Map<Integer,Integer> inserted = new HashMap<Integer,Integer>(coins); // Map<> is an interface, cannot be Instantiated
        reset(); //reset coins. however, we have a copy of it it inserted.
        return inserted;
    }
    
    private void reset() {
        //clear map
        coins.clear();
        
        //reset timeBrought and insertedSoFar
        timeBought = insertedSoFar = 0; 
    }
    
    public int empty(){
        //returns total of money collected by the paystation since the last call and empties it
        int total = insertedSoFar; //stored money collect to a variable before emptying it
        
        timeBought = insertedSoFar = 0; // Canvas says reset total to 0 but what are they talking about when they say total. There are no variables name total
        //reset(); //reset should not be used since it clears the map. Canvas says nothing about empty clearing the map.
        return total;
    }

    public void addToMap(int coinValue) {
        //.get() will return the number of coins for that value or null if there is none
        if (coins.get(coinValue) == null) {
            coins.put(coinValue,1);
        } else {
            coins.put(coinValue, coins.get(coinValue) + 1); //
        }
        
    }
    
    public void setRateStrategy(int choice) throws IllegalChoiceException{
        switch (choice) {
            case 1: 
                rateStrategy = new LinearRateStrategy();
                System.out.println("Alphatown rate in effect");
                break;
            case 2: 
                rateStrategy = new ProgressiveRateStrategy();
                System.out.println("Betatown rate in effect");
                break;
            case 3: 
                rateStrategy = new AlternatingRateStrategy();
                System.out.println("Gammatown rate in effect");
                break;
            default:
                throw new IllegalChoiceException("Invalid rate strategy selection: " + choice);
        }
        
        //recalculate time since rate has changed;
        timeBought = rateStrategy.calculateTime(insertedSoFar);
    }
}
