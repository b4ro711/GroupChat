/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author BARO
 */
public class mFrm_Server extends javax.swing.JFrame {

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
    Thread t;
    ServerThread serverThread;
    /**
     * Chat List *
     */
    public Vector socketList = new Vector();
    public Vector clientList = new Vector();
    /**
     * File Sharing List *
     */
    public Vector clientFileSharingUsername = new Vector();
    public Vector clientFileSharingSocket = new Vector();
    /**
     * Server *
     */
    ServerSocket server;

    /**
     * Creates new form MainForm_Server
     */
    public mFrm_Server() {
        initComponents();
        MyInit();
        jtaShowcase.setEditable(false);
    }

    void MyInit() {
        setLocationRelativeTo(null);
    }

    public void appendMessage(String msg) {
        Date date = new Date();
        jtaShowcase.append(sdf.format(date) + ": " + msg + "\n");
        jtaShowcase.setCaretPosition(jtaShowcase.getText().length() - 1);
    }

    public void setSocketList(Socket socket) {
        try {
            socketList.add(socket);
            appendMessage("[setSocketList]: Được thêm");
        } catch (Exception e) {
            appendMessage("[setSocketList]: " + e.getMessage());
        }
    }

    public void setClientList(String client) {
        try {
            clientList.add(client);
            appendMessage("[setClientList]: Được thêm");
        } catch (Exception e) {
            appendMessage("[setClientList]: " + e.getMessage());
        }
    }

    public void setClientFileSharingUsername(String user) {
        try {
            clientFileSharingUsername.add(user);
        } catch (Exception e) {
        }
    }

    public void setClientFileSharingSocket(Socket soc) {
        try {
            clientFileSharingSocket.add(soc);
        } catch (Exception e) {
        }
    }

    public Socket getClientList(String client) {
        Socket tsoc = null;
        for (int x = 0; x < clientList.size(); x++) {
            if (clientList.get(x).equals(client)) {
                tsoc = (Socket) socketList.get(x);
                break;
            }
        }
        return tsoc;
    }

    public void removeFromTheList(String client) {
        try {
            for (int x = 0; x < clientList.size(); x++) {
                if (clientList.elementAt(x).equals(client)) {
                    clientList.removeElementAt(x);
                    socketList.removeElementAt(x);
                    appendMessage("[Removed]: " + client);
                    break;
                }
            }
        } catch (Exception e) {
            appendMessage("[RemovedException]: " + e.getMessage());
        }
    }

    public Socket getClientFileSharingSocket(String username) {
        Socket tsoc = null;
        for (int x = 0; x < clientFileSharingUsername.size(); x++) {
            if (clientFileSharingUsername.elementAt(x).equals(username)) {
                tsoc = (Socket) clientFileSharingSocket.elementAt(x);
                break;
            }
        }
        return tsoc;
    }

    public void removeClientFileSharing(String username) {
        for (int x = 0; x < clientFileSharingUsername.size(); x++) {
            if (clientFileSharingUsername.elementAt(x).equals(username)) {
                try {
                    Socket rSock = getClientFileSharingSocket(username);
                    if (rSock != null) {
                        rSock.close();
                    }
                    clientFileSharingUsername.removeElementAt(x);
                    clientFileSharingSocket.removeElementAt(x);
                    appendMessage("[FileSharing]: Hủy bỏ " + username);
                } catch (IOException e) {
                    appendMessage("[FileSharing]: " + e.getMessage());
                    appendMessage("[FileSharing]: Không thể hủy bỏ " + username);
                }
                break;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaShowcase = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jtfPort = new javax.swing.JTextField();
        btnOn = new javax.swing.JButton();
        btnOff = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtaShowcase.setColumns(20);
        jtaShowcase.setRows(5);
        jScrollPane1.setViewportView(jtaShowcase);

        jLabel1.setText("Port:");
        jLabel1.setToolTipText("");

        jtfPort.setText("8080");

        btnOn.setText("Khởi động máy chủ");
        btnOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnActionPerformed(evt);
            }
        });

        btnOff.setText("Dừng máy chủ");
        btnOff.setEnabled(false);
        btnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPort, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOff, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOn)
                    .addComponent(btnOff))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnActionPerformed
        // TODO add your handling code here:
        int port = Integer.parseInt(jtfPort.getText());
        serverThread = new ServerThread(port, this);
        t = new Thread(serverThread);
        t.start();

        new Thread(new OnlineListThread(this)).start();

        btnOn.setEnabled(false);
        btnOff.setEnabled(true);
    }//GEN-LAST:event_btnOnActionPerformed

    private void btnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOffActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "Đóng Máy Chủ.?");
        if (confirm == 0) {
            serverThread.stop();
        }
    }//GEN-LAST:event_btnOffActionPerformed

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
            java.util.logging.Logger.getLogger(mFrm_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mFrm_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mFrm_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mFrm_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mFrm_Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOff;
    private javax.swing.JButton btnOn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jtaShowcase;
    private javax.swing.JTextField jtfPort;
    // End of variables declaration//GEN-END:variables
}