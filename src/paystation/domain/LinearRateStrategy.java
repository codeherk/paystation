/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

/**
 *
 * @author Byron
 */
public class LinearRateStrategy implements RateStrategy {
    LinearRateStrategy(){}
    @Override
    public int calculateTime(int insertedSoFar) {
        int temp = insertedSoFar;
        return (temp * 2) / 5;
    }
    
    
}
