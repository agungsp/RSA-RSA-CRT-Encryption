package Cryptography;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger m;
    private BigInteger d;
    private BigInteger e;
    private final BigInteger satu;
    private ArrayList<BigInteger> Cipher;
    private ArrayList<String> CalculateText;
    private Prima prima;

    public RSA() {
        this.satu = BigInteger.valueOf(1);
        prima = new Prima(100);
        Cipher = new ArrayList<BigInteger>();
        CalculateText = new ArrayList<String>();
        
        refreshValue();
    }
    
    public void refreshValue(){
        CalculateText.clear();
        setP();
        
        do {            
            setQ();
        } while (p == q);
        
        setN();      
        setM();
        
        calculate_E();
        calculate_D(e,m);
    }
    
    private void calculate_E(){
        boolean result = false;
        
        while (!result) {
            BigInteger tmp_e = BigInteger.valueOf(prima.getPrimaRandom());
            ArrayList<Integer> faktor_M = Faktor.getFaktor(m);
            
            if (!faktor_M.contains(tmp_e)) {
                if (!tmp_e.equals(p.subtract(satu)) && !tmp_e.equals(q.subtract(satu))) {
                    if (tmp_e.intValue() <= n.intValue()) {
                        e = tmp_e;
                        result = true;
                    }
                }
            }
        }
    }
    
    private void calculate_D(BigInteger e, BigInteger phi){
        BigInteger result = null;
        
        BigInteger tmp_phi = phi;
        BigInteger atas1 = phi;
        BigInteger atas2 = phi;
        BigInteger tmp_atas1 = BigInteger.ZERO;
        BigInteger tmp_atas2 = BigInteger.ZERO;
        
        BigInteger bawah1 = e;
        BigInteger bawah2 = BigInteger.ONE;
        
        BigInteger hasil1 = BigInteger.ZERO;
        BigInteger hasil2 = BigInteger.ZERO;
        BigInteger kali = BigInteger.ZERO;
        
        do {        
            try {
                kali = atas1.divide(bawah1);
            
                hasil1 = kali.multiply(bawah1);
                hasil2 = kali.multiply(bawah2);

                tmp_atas1 = bawah1;
                tmp_atas2 = bawah2;

                bawah1 = atas1.subtract(hasil1);
                bawah2 = atas2.subtract(hasil2);

                if (bawah2.intValue() < 0) {
                    bawah2 = bawah2.mod(tmp_phi);
                }

                atas1 = tmp_atas1;
                atas2 = tmp_atas2;
            } catch (ArithmeticException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + "\n Click OK to close the program.", "Arithmetic Exception when set \"d\"", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            
        } while (bawah1.intValue() != 1);
        result = bawah2;
        
        d = result;
    }
    
    public BigInteger getPublicKey(){
        return e;
    }
    
    public BigInteger getPrivateKey(){
        return d;
    }

    public ArrayList<BigInteger> encrypt(String PlainText){
        ArrayList<BigInteger> result = new ArrayList<BigInteger>();
        
        for (int i = 0; i < PlainText.length(); i++) {
            BigInteger ascii = BigInteger.valueOf((int) PlainText.charAt(i));
            BigInteger Cipher = ascii.modPow(e, n);
            result.add(Cipher);
        }
        
        Cipher.clear();
        Cipher.addAll(result);
        return result;
    }
    
    public String decrypt(ArrayList<BigInteger> Cipher){
        String result = "";
        for (BigInteger ascii : Cipher) {
            BigInteger tmp = ascii.modPow(d, n);
            Character c = (char) tmp.intValue();
            result = result.concat(c.toString());
        }
        return result;
    }
    
    public void makeCalculate(){
        WriteFile.writeToFile(CalculateText);
    }

    public ArrayList<BigInteger> getCipher() {
        return Cipher;
    }
    
    private void setP(){
        p = BigInteger.valueOf(prima.getPrimaRandom());
        String calStr = "\"P\" adalah bilangan prima yang ditentukan acak\n P = " + p;
        CalculateText.add(calStr);
    }
    
    private void setQ(){
        q = BigInteger.valueOf(prima.getPrimaRandom());
        String calStr = "\"Q\" adalah bilangan prima yang ditentukan acak\n Q = " + q;
        CalculateText.add(calStr);
    }
    
    private void setN(){
        n = p.multiply(q);
        String calStr = "\"N\" diperoleh dari P-Q\n" + p + "-" + q + " = " + n;
        CalculateText.add(calStr);
    }
    
    private void setM(){
        m = p.subtract(satu).multiply(q.subtract(satu));
    }
    
    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getM() {
        return m;
    }
    
    
}
