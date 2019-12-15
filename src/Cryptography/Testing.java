package Cryptography;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Testing {

    private int counter;
    private int bitLength;

        
    private String toBinaryStr(String text){
        String Result = "";
        
        for (int i = 0; i < text.length(); i++) {
            int ascii = (int) text.charAt(i);
            Result = Result + Integer.toBinaryString(ascii);
        }           
        
        return Result;
    }
    
    public double getAvalancheEffect(String plaintext, String cipher) {
        double Result = 0;
        counter = 0;
        bitLength = 0;
        
        try {
            String textBinary = toBinaryStr(plaintext);
            String cipherBinary = toBinaryStr(cipher);
            
            

            for (int i = 0; i < textBinary.length(); i++) {
                
                if (textBinary.charAt(i) != cipherBinary.charAt(i)) {
                    counter++;
                }
                bitLength++;
            } 
            Result = ((double) counter / (double) bitLength) * 100;
        } catch (ArithmeticException e) {
            Result = 0;
        }
        
        return Result;
    }
    
    public int getDifferentBitsCount() {
        return counter;
    }
    
    public int getBitsLength(){
        return bitLength;
    }
    
    public double getTroughput(String text, long time){
        double Result = 0;
        try {
            Result = (double) text.length() / time;
        } catch (ArithmeticException e) {
            Result = 0;
        }
        
        return Result;                
    }
    
    public int getErrorDecrypt(String plainText, String decryptText){
        int Result = 0;
        
        for (int i = 0; i < plainText.length(); i++) {
            char plain = plainText.charAt(i);
            char decrypt = decryptText.charAt(i);
            if (plain != decrypt) {
                Result++;
            }
        }
        
        return Result;                
    }
}
