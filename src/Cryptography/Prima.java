package Cryptography;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Prima {
   private int limit = 10;

    public Prima() {
        
    }
    
    public Prima(int limit) {
        this.limit = limit;
    }
    
    private int calculate(int val){
        int found = 0;
        int result = 0;
        for (int i = 1; i <= val ; i++) {
            if (val%i == 0) {
                found++;
            }
        }
        if (found == 2) {
            result = val;
        }
        return result;
    }
    
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    
    public ArrayList<Integer> getPrimaList(){
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        int i = 1;
        
        while (result.size() < limit) { 
            int tmp = calculate(i);
            if (tmp != 0) {
                result.add(i);
            }
            i++;
        }
        return result;
    }    
    
    public int getPrimaRandom(){
        int result = 0;
        
        while (result == 0) {            
            int tmp = calculate(new Random().nextInt(limit) + 1);
            if (tmp != 0) {
                result = tmp;
            }
        }
        
        return result;
    } 
}
