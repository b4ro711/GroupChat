/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author BARO
 */
public class sendfileFrm extends javax.swing.JFrame {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String myusername;
    private String host;
    private int port;
    private StringTokenizer st;
    private String sendTo;
    private String file;
    private mFrm_Client main;
    private Object chooser;

    /**
     * Creates new form sendfileFrm
     */
    public sendfileFrm() {
        initComponents();
        MyInit();
        progressbar.setVisible(false);
    }

    void MyInit() {
        setLocationRelativeTo(null);
    }

    public boolean prepare(String u, String h, int p, mFrm_Client m) {
        this.host = h;
        this.myusername = u;
        this.port = p;
        this.main = m;
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            //  Format: CMD_SHARINGSOCKET [sender]
            String format = "CMD_SHARINGSOCKET " + myusername;
            dos.writeUTF(format);
            System.out.println(format);

            new Thread(new SendFileThread(this)).start();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    class SendFileThread implements Runnable {

        private sendfileFrm form;

        public SendFileThread(sendfileFrm form) {
            this.form = form;
        }

        private void closeMe() {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("[closeMe]: " + e.getMessage());
            }
            dispose();
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String data = dis.readUTF();  // Đọc nội dung của dữ liệu được nhận từ Server
                    st = new StringTokenizer(data);
                    String cmd = st.nextToken();  //  Lấy chữ đầu tiên từ dữ liệu
                    switch (cmd) {
                        case "CMD_RECEIVE_FILE_ERROR":  // Định dạng: CMD_RECEIVE_FILE_ERROR [Message]
                            String msg = "";
                            while (st.hasMoreTokens()) {
                                msg = msg + " " + st.nextToken();
                            }
                            form.updateAttachment(false);
                            JOptionPane.showMessageDialog(sendfileFrm.this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
                            this.closeMe();
                            break;

                        case "CMD_RECEIVE_FILE_ACCEPT":  // Định dạng: CMD_RECEIVE_FILE_ACCEPT [Message]
                            /*  Bắt đầu khởi động thread File đính kèm   */

                            new Thread(new SendingFileThread(socket, file, sendTo, myusername, sendfileFrm.this)).start();
                            break;

                        case "CMD_SENDFILEERROR":
                            String emsg = "";
                            while (st.hasMoreTokens()) {
                                emsg = emsg + " " + st.nextToken();
                            }
                            System.out.println(emsg);
                            JOptionPane.showMessageDialog(sendfileFrm.this, emsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
                            form.updateAttachment(false);
                            form.disableGUI(false);
                            form.updateBtn("Gửi File");
                            break;

                        case "CMD_SENDFILERESPONSE":
                            /*
                            Format: CMD_SENDFILERESPONSE [username] [Message]
                             */
                            String rReceiver = st.nextToken();
                            String rMsg = "";
                            while (st.hasMoreTokens()) {
                                rMsg = rMsg + " " + st.nextToken();
                            }
                            form.updateAttachment(false);
                            JOptionPane.showMessageDialog(sendfileFrm.this, rMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtfSendTo = new javax.swing.JTextField();
        jtfBrowse = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        btnSendFile = new javax.swing.JButton();
        progressbar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Chọn file:");

        jLabel2.setText("Gửi đến:");

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        btnSendFile.setText("Gửi file");
        btnSendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendFileActionPerformed(evt);
            }
        });

        progressbar.setStringPainted(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(progressbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSendFile))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtfBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBrowse))
                            .addComponent(jtfSendTo, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfSendTo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSendFile, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(progressbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        // TODO add your handling code here:
        showOpenDialog();
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnSendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendFileActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        sendTo = jtfSendTo.getText();
        file = jtfBrowse.getText();

        if ((sendTo.length() > 0) && (file.length() > 0)) {
            try {
                // Format: CMD_SEND_FILE_XD [sender] [receiver] [filename]
                jtfBrowse.setText("");
                String fname = getThisFilename(file);
                String format = "CMD_SEND_FILE_XD " + myusername + " " + sendTo + " " + fname;
                dos.writeUTF(format);
                System.out.println(format);
                updateBtn("Đang gửi đi...");
                btnSendFile.setEnabled(false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không để trống.!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSendFileActionPerformed
    public void showOpenDialog() {
        JFileChooser chooser = new JFileChooser();
        int intval = chooser.showOpenDialog(this);
        if (intval == chooser.APPROVE_OPTION) {
            jtfBrowse.setText(chooser.getSelectedFile().toString());
        } else {
            jtfBrowse.setText("");
        }
    }

    /*   Phương thức này sẽ disable/enabled tất cả GUI   */
    public void disableGUI(boolean d) {
        if (d) { // Disable
            jtfSendTo.setEditable(false);
            btnBrowse.setEnabled(false);
            btnSendFile.setEnabled(false);
            jtfBrowse.setEditable(false);
            progressbar.setVisible(true);
        } else { // Enable
            jtfSendTo.setEditable(true);
            btnSendFile.setEnabled(true);
            btnBrowse.setEnabled(true);
            jtfBrowse.setEditable(true);
            progressbar.setVisible(false);
        }
    }

    /*  Nhận Form Title   */
    public void setMyTitle(String s) {
        setTitle(s);
    }

    /*   Hàm này sẽ đóng Form  */
    protected void closeThis() {
        dispose();
    }

    /*  Hàm này sẽ nhận Filename */
    public String getThisFilename(String path) {
        File p = new File(path);
        String fname = p.getName();
        return fname.replace(" ", "_");
    }

    /*  Cập nhật file đính kèm   */
    public void updateAttachment(boolean b) {
        main.updateAttachment(b);
    }

    /*  Cập nhật nút btnSendFile text  */
    public void updateBtn(String str) {
        btnSendFile.setText(str);
    }

    /**
     * Update progress bar
     *
     * @param val
     */
    public void updateProgress(int val) {
        progressbar.setValue(val);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(sendfileFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sendfileFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sendfileFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sendfileFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sendfileFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnSendFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jtfBrowse;
    private javax.swing.JTextField jtfSendTo;
    private javax.swing.JProgressBar progressbar;
    // End of variables declaration//GEN-END:variables
}
