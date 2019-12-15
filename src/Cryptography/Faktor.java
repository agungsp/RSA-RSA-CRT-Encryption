package Cryptography;

import java.math.BigInteger;
import java.util.ArrayList;

public class Faktor {
    
    public static ArrayList<Integer> getFaktor(int bil){
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        for (int i = 1; i <= bil; i++) {
            if (bil%i == 0) {
                result.add(i);
            }
        }
        
        return result;
    }
    
    public static ArrayList<Integer> getFaktor(BigInteger bil){
        ArrayList<Integer> result = new ArrayList<Integer>();
        bil = BigInteger.valueOf(160);
        for (int i = 1; i <= bil.intValue(); i++) {
            if (bil.mod(BigInteger.valueOf(i)).equals(0)) {
                result.add(i);
            }
        }
        
        return result;
    }
    
}
