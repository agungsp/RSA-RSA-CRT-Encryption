package Cryptography;

public class FPB {
    public static int getFPB(int a, int b){
        int d=0;
        int i = 1; 
        int min = Math.min(a, b);
        
        while (i <= min) {
            if ((a%i==0)&(b%i==0)){
                d=i;                
            }
            i++;
        }
        return d;
    }
}
