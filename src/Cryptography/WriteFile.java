package Cryptography;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import jdk.nashorn.internal.codegen.CompilerConstants;

public class WriteFile {

    private static String FileName = "debug.txt";
    private static BufferedWriter bw = null;
    private static FileWriter fw = null;
            
    public WriteFile() {
        
    }
    
    public WriteFile(String FileName) {
        this.FileName = FileName;
    }
    
    public static boolean writeToFile(ArrayList<String> Text){
        String FullText = "";
        
        for (String data : Text) {
            FullText = FullText.concat(data).concat("\n\n");
        }
        
        return writeToFile(FullText);
    }

    public static boolean writeToFile(String Text){
        boolean result = true;
        try {
            
            fw = new FileWriter(FileName);
            bw = new BufferedWriter(fw);
            bw.write(Text);
            
        } catch (IOException e) {
            result = false;
        } finally {
            try {
                
                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return result;
    }
    
    
}
