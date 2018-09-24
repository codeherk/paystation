package paystation.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.HashMap;
import java.util.Map;

/**
 * CIS 3296 - LAB 3
 * Byron Jenkins & Daniel Dubovoy
 * GITHUB - https://github.com/codeherk/paystation 
 * 
 * Junit test cases
 * 
 **/

public class PayStationImplTest {

    PayStation ps;
    Map<Integer, Integer> mapForTesting;

    @Before
    public void setup() {
        ps = new PayStationImpl();
        mapForTesting = new HashMap<>();
    }
    
    /********************************** Example Test Cases **********************************/
    // Uncomment @Test to test these cases. 

    public PayStationImplTest() {
    }
    
    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    //@Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    //@Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    //@Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    //@Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    //@Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    //@Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    //@Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    //@Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }

/********************************** Test Cases for Lab Three **********************************/
    /**
     * Test Case 1 -
     * Call to empty returns the total amount entered.
     */    
    @Test
    public void shouldReturnAmountEntered() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(25);
        ps.addPayment(10);
        assertEquals("Empty needs to return the amount", 40, ps.empty());
    }
    
    /**
     * Test Case 2 -
     * Canceled entry does not add to the amount returned by empty.
     */
    @Test
    public void shouldNotReturnCanceledEntry() throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(5);
        ps.cancel();
        assertEquals("Entry that was cancelled does not add to the result", 0, ps.empty());

        ps.addPayment(10);
        assertEquals("When empty is called, it will work", 10, ps.empty());
    }

    /**
     * Test Case 3 -
     * Call to empty resets the total to zero.
     */
    @Test
    public void shouldResetToZeroAfterEmptied() throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        ps.empty();
        assertEquals("Call to empty should reset the total to zero", 0, ps.readDisplay());
    }

    /**
     * Test Case 4 -
     * Call to cancel returns a map containing one coin entered.
     */
    @Test
    public void shouldReturnOneCoin() throws IllegalCoinException {

        ps.addPayment(5);
        mapForTesting.put(5, 1);
        
        //assertEquals("Returns the map with only one entered coin", mapForTesting, ps.cancel());
        assertEquals("Returns the map containing one entered coin entered", mapForTesting.get(5), ps.cancel().get(5));

    }

    /**
     * Test Case 5 -
     * Call to cancel returns a map containing a mixture of coins entered.
     */
    @Test
    public void shouldReturnMultipleCoins() throws IllegalCoinException {
        ps.addPayment(25);
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(5);
        ps.addPayment(5);
              
        mapForTesting.put(5, 3);
        mapForTesting.put(10, 1);
        mapForTesting.put(25, 2);
        assertEquals("Returns a map with a mixture of coins entered", mapForTesting, ps.cancel());
    }
    
    /**
     * Test Case 6 -
     * Call to cancel returns a map that does not contain a key for a coin not entered.
     */
    @Test
    public void shouldNotContainKeyForNoEntry() throws IllegalCoinException {
        assertFalse("Returns a map that does not contain a key for a coin not entered", ps.cancel().containsKey(25));
    }
    
    /**
     * Test Case 7 -
     * Call to cancel clears the map.
     */
    @Test
    public void shouldClearMapAfterCancel() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(25);
        ps.cancel();
        assertTrue("Returns a cleared map after call to cancel", ps.cancel().isEmpty());
    }

    /**
     * Test Case 8 -
     * Call to buy clears the map.
     */
    @Test
    public void shouldClearMapAfterBuy() throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(5);
        ps.addPayment(25);
        ps.buy();
        assertTrue("Return empty map after a call to buy", ps.cancel().isEmpty());
    }
}
    

    
   
    

