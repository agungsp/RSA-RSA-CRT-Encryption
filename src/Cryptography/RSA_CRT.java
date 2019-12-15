package Cryptography;

import java.math.BigInteger;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class RSA_CRT {
        
    private  BigInteger dP;
    private  BigInteger dQ;
    private  BigInteger qInv;  
    
    private  ArrayList<BigInteger> M1Cipher;
    private  ArrayList<BigInteger> M2Cipher;
    private  ArrayList<BigInteger> h;
    private  ArrayList<BigInteger> M;

    public RSA_CRT(BigInteger d, BigInteger p, BigInteger q, ArrayList<BigInteger> Cipher) {
        CreateObject();
        
        setDP(d, p);
        setDQ(d, q);
        setQInv(p, q);
        setM1Cipher(Cipher, p);
        setM2Cipher(Cipher, q);
        setHCipher(p);
        setMASCII(q);       
    }
    
    public  BigInteger getdP() {
        return dP;
    }

    public  BigInteger getdQ() {
        return dQ;
    }

    public  BigInteger getqInv() {
        return qInv;
    }
    
    public  String decrypt() {
        String result = "";
        
        for (BigInteger ascii : M) {
            Character c = (char) ascii.intValue();
            result = result.concat(c.toString());
        }
        
        return result;
    }
    
    private  void CreateObject(){
        M1Cipher = new ArrayList<BigInteger>();
        M2Cipher = new ArrayList<BigInteger>();
        h = new ArrayList<BigInteger>();
        M = new ArrayList<BigInteger>();
    }
    
    private  void setDP(BigInteger d, BigInteger p){
        try {
            dP = d.mod(p.subtract(BigInteger.ONE));
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n Click OK and click refresh key.", "Arithmetic Exception when set \"dp\"", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private  void setDQ(BigInteger d, BigInteger q){
        try {
            dQ = d.mod(q.subtract(BigInteger.ONE));
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n Click OK and click refresh key.", "Arithmetic Exception when set \"dq\"", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private  void setQInv(BigInteger p, BigInteger q){
        try {
            qInv = q.modInverse(p);
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n Click OK and click refresh key.", "Arithmetic Exception when set \"qInv\"", JOptionPane.WARNING_MESSAGE);
        }        
    }
    
    private  void setM1Cipher(ArrayList<BigInteger> Cipher, BigInteger p){
        M1Cipher.clear();
        for (BigInteger data : Cipher) {
            BigInteger tmp = data.modPow(dP, p);
            M1Cipher.add(tmp);
        }
    }
    
    private  void setM2Cipher(ArrayList<BigInteger> Cipher, BigInteger q){
        M2Cipher.clear();
        for (BigInteger data : Cipher) {
            BigInteger tmp = data.modPow(dQ, q);
            M2Cipher.add(tmp);
        }
    }
    
    private  void setHCipher(BigInteger p){
        h.clear();
        for (int i = 0; i < M1Cipher.size(); i++) {
            BigInteger tmp = qInv.multiply(M1Cipher.get(i).subtract(M2Cipher.get(i))).mod(p);
            h.add(tmp);
        }
    }
    
    private  void setMASCII(BigInteger q){
        M.clear();
        for (int i = 0; i < h.size(); i++) {
            BigInteger tmp = M2Cipher.get(i).add(h.get(i).multiply(q));
            M.add(tmp);
        }
    }
    
}
