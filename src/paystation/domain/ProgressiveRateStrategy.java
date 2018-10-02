package paystation.domain;

public class ProgressiveRateStrategy implements RateStrategy{
    ProgressiveRateStrategy(){}
    @Override
    public int calculateTime(int insertedSoFar){
        int temp = insertedSoFar; //I am unsure if java passes by ref or by value. I do not want to change insertedSoFar. Therefore a temp variable is safe till its time to refactor
        int time;
        
        if(insertedSoFar >= 350){
            //System.out.println("amount >= $3.50 INSERTED!");
            temp -= 350;
            time = 120 + temp / 5;
        }
        //
        else if(insertedSoFar >=150){
            //System.out.println("$1.50 <= amount < $3.50 INSERTED!");
            temp -= 150;
            time = 60 + temp / 5;
        }else{
            //System.out.println("amount < $1.50 INSERTED!");
            time = temp * 2 / 5;
        }
        
        return time;
    }
}