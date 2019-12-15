package Forms;

import Cryptography.FPB;
import Cryptography.RSA;
import Cryptography.RSA_CRT;
import Cryptography.Testing;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class CustomFilter extends javax.swing.filechooser.FileFilter  {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Text documents (*.txt)";
    }
}

public class Main extends javax.swing.JFrame {
    
    private RSA rsa;
    private RSA_CRT rsa_crt;
    private LogForm formLog;
    private Testing testing;
            
    public Main() {
        initComponents();
        rsa = new RSA();
        formLog = new LogForm(this, false);
        testing = new Testing();
        LabelStatus.setText("Ready!");
        CbCustom.setSelected(false);
        CbCustomState();
        RbgMethod.setSelected(RadioRSA.getModel(), true);
    }
    
    private void BrowseFile(){
        int returnVal = fileChooser.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            EditPathFile.setText(file.getAbsolutePath());
            LableFilename.setText(file.getName());
            try {
                EditPlainText.read(new FileReader(file.getAbsolutePath()), null);
                LabelStatus.setText("File Loaded!");
            } catch (IOException ex) {
                LabelStatus.setText("problem accessing file"+file.getAbsolutePath());
            }
        } else {
            LabelStatus.setText("File access cancelled by user.");
        }
    }
    
    private void Encrypt(){
        long start = 0;
        long end = 0;
        long duration;
        String cipher = "";
        
        
//        start = System.currentTimeMillis();
        start = System.nanoTime();
        for (BigInteger cipherCode : rsa.encrypt(EditPlainText.getText())) {
            cipher = cipher.concat(cipherCode.toString() + " ");
        }
                
//        end = System.currentTimeMillis();
        end = System.nanoTime();
        duration = (end - start);
        
        EditCipher.setText(cipher);        
        LabelKeterangan.setText("<html>P = " + rsa.getP() + 
                                "<br>Q = " + rsa.getQ() +
                                "<br>E = " + rsa.getPublicKey() +
                                "<br>D = " + rsa.getPrivateKey() + "</html>");
        LabelStatus.setText("Encrypted (total time: " + duration + " ns)");
        
        rsa_crt = new RSA_CRT(rsa.getPrivateKey(), rsa.getP(), rsa.getQ(), rsa.getCipher());
        LabelKeterangan1.setText("<html>dp = " + rsa_crt.getdP()+ 
                                 "<br>dq = " + rsa_crt.getdQ() +
                                 "<br>qInv = " + rsa_crt.getqInv()+ "</html>");
        
        formLog.addLog("\n\tEncrypt");
        formLog.addLog("===========================");
        formLog.addLog("Duration\t: " + duration + " ns");
        formLog.addLog("Text Size\t: " + EditPlainText.getText().length() + " character");
        formLog.addLog("Throughput\t: " + testing.getTroughput(EditPlainText.getText(), duration));
    }
    
    private void Decrypt(){
        long start = 0;
        long end = 0;
        long duration;
        String method = "";
        if (RadioRSA.isSelected()) {
//            start = System.currentTimeMillis();
            start = System.nanoTime();
            EditDecrypt.setText(rsa.decrypt(rsa.getCipher()));
//            end = System.currentTimeMillis();
            end = System.nanoTime();
            method = "RSA";
        } else if (RadioCRT.isSelected()){
//            start = System.currentTimeMillis();
            start = System.nanoTime();
            EditDecrypt.setText(rsa_crt.decrypt());
//            end = System.currentTimeMillis();
            end = System.nanoTime();
            method = "CRT";
        }
        duration = (end - start);
        LabelStatus.setText("Decrypted by " + method + " (total time: " + duration + " ns)");
        
        formLog.addLog("\n\tDecrypt by " + method);
        formLog.addLog("===========================");
        formLog.addLog("Duration\t\t: " + duration + " ns");
        formLog.addLog("Text Size\t\t: " + EditPlainText.getText().length() + " character");
        formLog.addLog("Throughput\t\t: " + testing.getTroughput(EditPlainText.getText(), duration));
        formLog.addLog("Avalanche Effect\t: " + testing.getAvalancheEffect(EditPlainText.getText(), EditCipher.getText()) + "%");
        formLog.addLog("Different Bits\t\t: " + testing.getDifferentBitsCount());
        formLog.addLog("Bits Length\t\t: " + testing.getBitsLength());
        formLog.addLog("Error Count\t\t: " + testing.getErrorDecrypt(EditPlainText.getText(), EditDecrypt.getText()) + " / " + EditPlainText.getText().length() + " character");
    }
    
    private void Refresh(){
        rsa.refreshValue();
        LabelKeterangan.setText("<html>P = " + rsa.getP() + 
                                "<br>Q = " + rsa.getQ() +
                                "<br>E = " + rsa.getPublicKey() +
                                "<br>D = " + rsa.getPrivateKey() + "</html>");
        EditCipher.setText("");
        EditDecrypt.setText("");
        LabelStatus.setText("Public key and private key has been reset");
        formLog.ClearLog();
    }
    
    private void CbCustomState(){
        if (CbCustom.isSelected()) {
            EditPlainText.setEditable(true);
            BtnBrowseFile.setEnabled(false);
            EditPlainText.setBackground(Color.WHITE);
            EditPlainText.requestFocus();
            LableFilename.setText("(No File)");
        } else {
            EditPlainText.setEditable(false);
            BtnBrowseFile.setEnabled(true);
            EditPlainText.setText("");
            EditPlainText.setBackground(new Color(204, 204, 204));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        RbgMethod = new javax.swing.ButtonGroup();
        EditPathFile = new javax.swing.JTextField();
        BtnBrowseFile = new javax.swing.JButton();
        PanelPlainText = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EditPlainText = new javax.swing.JTextArea();
        LableFilename = new javax.swing.JLabel();
        CbCustom = new javax.swing.JCheckBox();
        PanelChiper = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        EditCipher = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        LabelStatus = new javax.swing.JLabel();
        BtnEncrypt = new javax.swing.JButton();
        PanelDecrypt = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        EditDecrypt = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        BtnDecrypt = new javax.swing.JButton();
        LabelKeterangan = new javax.swing.JLabel();
        BtnRefresh = new javax.swing.JButton();
        LabelKeterangan1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        RadioCRT = new javax.swing.JRadioButton();
        RadioRSA = new javax.swing.JRadioButton();
        BtnShowLog = new javax.swing.JButton();

        fileChooser.setFileFilter(new CustomFilter());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        EditPathFile.setEditable(false);

        BtnBrowseFile.setText("Browse File");
        BtnBrowseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBrowseFileActionPerformed(evt);
            }
        });

        PanelPlainText.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EditPlainText.setEditable(false);
        EditPlainText.setColumns(20);
        EditPlainText.setLineWrap(true);
        EditPlainText.setRows(5);
        EditPlainText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                EditPlainTextCaretUpdate(evt);
            }
        });
        EditPlainText.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                EditPlainTextInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jScrollPane1.setViewportView(EditPlainText);

        LableFilename.setText("(No File)");

        CbCustom.setText("Custom");
        CbCustom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CbCustomStateChanged(evt);
            }
        });
        CbCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbCustomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelPlainTextLayout = new javax.swing.GroupLayout(PanelPlainText);
        PanelPlainText.setLayout(PanelPlainTextLayout);
        PanelPlainTextLayout.setHorizontalGroup(
            PanelPlainTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPlainTextLayout.createSequentialGroup()
                .addComponent(LableFilename, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CbCustom)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        PanelPlainTextLayout.setVerticalGroup(
            PanelPlainTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPlainTextLayout.createSequentialGroup()
                .addGroup(PanelPlainTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LableFilename)
                    .addComponent(CbCustom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );

        PanelChiper.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EditCipher.setEditable(false);
        EditCipher.setColumns(20);
        EditCipher.setLineWrap(true);
        EditCipher.setRows(5);
        jScrollPane2.setViewportView(EditCipher);

        jLabel2.setText("Chiper");

        javax.swing.GroupLayout PanelChiperLayout = new javax.swing.GroupLayout(PanelChiper);
        PanelChiper.setLayout(PanelChiperLayout);
        PanelChiperLayout.setHorizontalGroup(
            PanelChiperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        PanelChiperLayout.setVerticalGroup(
            PanelChiperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelChiperLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(220, 220, 220));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelStatus.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LabelStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LabelStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        BtnEncrypt.setText("Encrypt");
        BtnEncrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEncryptActionPerformed(evt);
            }
        });

        PanelDecrypt.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EditDecrypt.setEditable(false);
        EditDecrypt.setColumns(20);
        EditDecrypt.setLineWrap(true);
        EditDecrypt.setRows(5);
        jScrollPane3.setViewportView(EditDecrypt);

        jLabel3.setText("Decrypt");

        javax.swing.GroupLayout PanelDecryptLayout = new javax.swing.GroupLayout(PanelDecrypt);
        PanelDecrypt.setLayout(PanelDecryptLayout);
        PanelDecryptLayout.setHorizontalGroup(
            PanelDecryptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        PanelDecryptLayout.setVerticalGroup(
            PanelDecryptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDecryptLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BtnDecrypt.setText("Decrypt");
        BtnDecrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDecryptActionPerformed(evt);
            }
        });

        LabelKeterangan.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        LabelKeterangan.setBorder(javax.swing.BorderFactory.createTitledBorder("RSA Var"));

        BtnRefresh.setText("Refresh Key");
        BtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshActionPerformed(evt);
            }
        });

        LabelKeterangan1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        LabelKeterangan1.setBorder(javax.swing.BorderFactory.createTitledBorder("CRT Var"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Decrypt"));

        RbgMethod.add(RadioCRT);
        RadioCRT.setText("CRT");

        RbgMethod.add(RadioRSA);
        RadioRSA.setText("RSA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RadioRSA)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(RadioCRT)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioCRT)
                    .addComponent(RadioRSA)))
        );

        BtnShowLog.setText("Show Log");
        BtnShowLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnShowLogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(EditPathFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnBrowseFile))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LabelKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LabelKeterangan1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(BtnEncrypt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnDecrypt))
                            .addComponent(BtnShowLog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelPlainText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelChiper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelDecrypt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditPathFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBrowseFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnRefresh)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelPlainText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelDecrypt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelChiper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnDecrypt)
                            .addComponent(BtnEncrypt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnShowLog))
                    .addComponent(LabelKeterangan1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelKeterangan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBrowseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBrowseFileActionPerformed
        BrowseFile();
    }//GEN-LAST:event_BtnBrowseFileActionPerformed

    private void BtnEncryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEncryptActionPerformed
        Encrypt();
    }//GEN-LAST:event_BtnEncryptActionPerformed

    private void BtnDecryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDecryptActionPerformed
        Decrypt();
    }//GEN-LAST:event_BtnDecryptActionPerformed

    private void BtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshActionPerformed
        Refresh();
    }//GEN-LAST:event_BtnRefreshActionPerformed

    private void EditPlainTextInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_EditPlainTextInputMethodTextChanged
//        
    }//GEN-LAST:event_EditPlainTextInputMethodTextChanged

    private void EditPlainTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_EditPlainTextCaretUpdate
        
    }//GEN-LAST:event_EditPlainTextCaretUpdate

    private void CbCustomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CbCustomStateChanged
        
    }//GEN-LAST:event_CbCustomStateChanged

    private void CbCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbCustomActionPerformed
        CbCustomState();
    }//GEN-LAST:event_CbCustomActionPerformed

    private void BtnShowLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnShowLogActionPerformed
        formLog.setVisible(true);
    }//GEN-LAST:event_BtnShowLogActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBrowseFile;
    private javax.swing.JButton BtnDecrypt;
    private javax.swing.JButton BtnEncrypt;
    private javax.swing.JButton BtnRefresh;
    private javax.swing.JButton BtnShowLog;
    private javax.swing.JCheckBox CbCustom;
    private javax.swing.JTextArea EditCipher;
    private javax.swing.JTextArea EditDecrypt;
    private javax.swing.JTextField EditPathFile;
    private javax.swing.JTextArea EditPlainText;
    private javax.swing.JLabel LabelKeterangan;
    private javax.swing.JLabel LabelKeterangan1;
    private javax.swing.JLabel LabelStatus;
    private javax.swing.JLabel LableFilename;
    private javax.swing.JPanel PanelChiper;
    private javax.swing.JPanel PanelDecrypt;
    private javax.swing.JPanel PanelPlainText;
    private javax.swing.JRadioButton RadioCRT;
    private javax.swing.JRadioButton RadioRSA;
    private javax.swing.ButtonGroup RbgMethod;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
