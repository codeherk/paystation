/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import java.util.Calendar;

/**
 *
 * @author Byron
 */
public class AlternatingRateStrategy implements RateStrategy {
    AlternatingRateStrategy(){}
    
    @Override
    public int calculateTime(int insertedSoFar) {
        int temp = insertedSoFar;
        int time;
        Calendar cal = Calendar.getInstance();
        
        //Uses Progressive Rate
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            //return new ProgressiveRateStrategy().calculateTime(insertedSoFar);
            System.out.println("It's the WEEKEND");
            if(insertedSoFar >= 350){
                System.out.println("amount >= $3.50 INSERTED!");
                temp -= 350;
                time = 120 + temp / 5;
            }
            //
            else if(insertedSoFar >=150){
                System.out.println("$1.50 <= amount < $3.50 INSERTED!");
                temp -= 150;
                time = 60 + temp / 5;
            }else{
                System.out.println("amount < $1.50 INSERTED!");
                time = temp * 2 / 5;
                }
        }else{
            //return new LinearRateStrategy().calculateTime(insertedSoFar);
            System.out.println("It's the WEEKDAY");
            time = (temp * 2) /5;
        }
        
        return time;
        
    }
}
