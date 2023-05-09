/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author BARO
 */
public class mFrm_Client extends javax.swing.JFrame {

    String username;
    String host;
    int port;
    Socket socket;
    DataOutputStream dos;
    public boolean attachmentOpen = false;
    private boolean isConnected = false;
    private String mydownloadfolder = "D:\\";

    /**
     * Creates new form mFrm_Client
     */
    public mFrm_Client() {
        initComponents();
        MyInit();
    }

    public void initFrame(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
        setTitle("Bạn đang được đăng nhập với tên: " + username);
        //Kết nối 
        connect();
    }

    void MyInit() {
        setLocationRelativeTo(null);
    }

    public void connect() {
        appendMessage(" Đang kết nối...", "Trạng thái", Color.BLACK, Color.BLACK);
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            // gửi username đang kết nối
            dos.writeUTF("CMD_JOIN " + username);
            appendMessage(" Đã kết nối", "Trạng thái", Color.BLACK, Color.BLACK);
            appendMessage(" Gửi tin nhắn bây giờ!", "Trạng thái", Color.BLACK, Color.BLACK);

            // Khởi động Client Thread 
            new Thread(new ClientThread(socket, this)).start();
            btnSend.setEnabled(true);
            // đã được kết nối
            isConnected = true;

        } catch (IOException e) {
            isConnected = false;
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến máy chủ, vui lòng thử lại sau.!", "Kết nối thất bại", JOptionPane.ERROR_MESSAGE);
            appendMessage("[IOException]: " + e.getMessage(), "Lỗi", Color.RED, Color.RED);
        }
    }

    /*
        Được kết nối
     */
    public boolean isConnected() {
        return this.isConnected;
    }

    /*
        Hiển thị Message
     */
    public void appendMessage(String msg, String header, Color headerColor, Color contentColor) {
        jpChat.setEditable(true);
        getMsgHeader(header, headerColor);
        getMsgContent(msg, contentColor);
        jpChat.setEditable(false);
    }

    /*
        Tin nhắn chat
     */
    public void appendMyMessage(String msg, String header) {
        jpChat.setEditable(true);
        getMsgHeader(header, Color.GREEN);
        getMsgContent(msg, Color.BLACK);
        jpChat.setEditable(false);
    }

    /*
        Tiêu đề tin nhắn
     */
    public void getMsgHeader(String header, Color color) {
        int len = jpChat.getDocument().getLength();
        jpChat.setCaretPosition(len);
//        jpChat.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Impact", 13), false);
        jpChat.replaceSelection(header + ":");
    }

    /*
        Nội dung tin nhắn
     */
    public void getMsgContent(String msg, Color color) {
        int len = jpChat.getDocument().getLength();
        jpChat.setCaretPosition(len);
//        jpChat.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Arial", 12), false);
        jpChat.replaceSelection(msg + "\n\n");
    }

    public void appendOnlineList(Vector list) {
        sampleOnlineList(list);
    }

    /*
        Hiển thị danh sách đang online
     */
    public void showOnLineList(Vector list) {
        try {
            jtpOnlineList.setEditable(true);
            jtpOnlineList.setContentType("text/html");
            StringBuilder sb = new StringBuilder();
            Iterator it = list.iterator();
            sb.append("<html><table>");
            while (it.hasNext()) {
                Object e = it.next();
                URL url = getImageFile();
//                Icon icon = new ImageIcon(this.getClass().getResource("/images/online.png"));
                sb.append("<tr><td><b>></b></td><td>").append(e).append("</td></tr>");
                System.out.println("Online: " + e);
            }
            sb.append("</table></body></html>");
            jtpOnlineList.removeAll();
            jtpOnlineList.setText(sb.toString());
            jtpOnlineList.setEditable(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
      ************************************  Hiển thị danh sách online  *********************************************
     */
    private void sampleOnlineList(Vector list) {
        jtpOnlineList.setEditable(true);
        jtpOnlineList.removeAll();
        jtpOnlineList.setText("");
        Iterator i = list.iterator();
        while (i.hasNext()) {
            Object e = i.next();
            /*  Hiển thị Username Online   */
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.white);

//            Icon icon = new ImageIcon(this.getClass().getResource("/images/online.png"));
            JLabel label = new JLabel();
            label.setText(" " + e);
            panel.add(label);
            int len = jtpOnlineList.getDocument().getLength();
            jtpOnlineList.setCaretPosition(len);
            jtpOnlineList.insertComponent(panel);
            /*  Append Next Line   */
            sampleAppend();
        }
        jtpOnlineList.setEditable(false);
    }

    private void sampleAppend() {
        int len = jtpOnlineList.getDocument().getLength();
        jtpOnlineList.setCaretPosition(len);
        jtpOnlineList.replaceSelection("\n");
    }

    /*
      ************************************  Show Online Sample  *********************************************
     */

 /*
        Get image file path
     */
    public URL getImageFile() {
        URL url = this.getClass().getResource("/images/online.png");
        return url;
    }

    /*
        Set myTitle
     */
    public void setMyTitle(String s) {
        setTitle(s);
    }

    /*
        Phương thức tải get download
     */
    public String getMyDownloadFolder() {
        return this.mydownloadfolder;
    }

    /*
        Phương thức get host
     */
    public String getMyHost() {
        return this.host;
    }

    /*
        Phương thức get Port
     */
    public int getMyPort() {
        return this.port;
    }

    /*
        Phương thức nhận My Username
     */
    public String getMyUsername() {
        return this.username;
    }

    /*
        Cập nhật Attachment 
     */
    public void updateAttachment(boolean b) {
        this.attachmentOpen = b;
    }

    /*
        Hàm này sẽ mở 1 file chooser
     */
    public void openFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int open = chooser.showDialog(this, "Mở Thư Mục");
        if (open == chooser.APPROVE_OPTION) {
            mydownloadfolder = chooser.getSelectedFile().toString() + "\\";
        } else {
            mydownloadfolder = "D:\\";
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

        jMenu1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jpChat = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtpOnlineList = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jtfContent = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jmDisconnect = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jmSendFile = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jpChat);

        jScrollPane2.setViewportView(jtpOnlineList);

        jLabel1.setText("Danh sách Online");
        jLabel1.setToolTipText("");

        jtfContent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfContentActionPerformed(evt);
            }
        });

        btnSend.setText("GỬI");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtfContent)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel1)
                        .addContainerGap(40, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfContent)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jMenu2.setText("Tài khoản");

        jmDisconnect.setText("Đăng xuất");
        jmDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmDisconnectActionPerformed(evt);
            }
        });
        jMenu2.add(jmDisconnect);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Chia sẻ");

        jmSendFile.setText("Gửi file");
        jmSendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSendFileActionPerformed(evt);
            }
        });
        jMenu3.add(jmSendFile);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

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

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        try {
            String content = username + " " + jtfContent.getText();
            dos.writeUTF("CMD_CHATALL " + content);
            appendMyMessage(" " + jtfContent.getText(), username);
            jtfContent.setText("");
        } catch (IOException e) {
            appendMessage(" Không thể gửi tin nhắn đi bây giờ, không thể kết nối đến Máy Chủ tại thời điểm này, xin vui lòng thử lại sau hoặc khởi động lại ứng dụng này.!", "Lỗi", Color.RED, Color.RED);
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void jtfContentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfContentActionPerformed

    }//GEN-LAST:event_jtfContentActionPerformed

    private void jmSendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSendFileActionPerformed
        // TODO add your handling code here:
        if (!attachmentOpen) {
            sendfileFrm s = new sendfileFrm();
            if (s.prepare(username, host, port, this)) {
                s.setLocationRelativeTo(null);
                s.setVisible(true);
                attachmentOpen = true;
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thiết lập Chia sẻ File tại thời điểm này, xin vui lòng thử lại sau.!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jmSendFileActionPerformed

    private void jmDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmDisconnectActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc đăng xuất không ?");
        if (confirm == 0) {
            try {
                socket.close();
                setVisible(false);
                /**
                 * Login Form *
                 */
                new loginFrm().setVisible(true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_jmDisconnectActionPerformed

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
            java.util.logging.Logger.getLogger(mFrm_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mFrm_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mFrm_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mFrm_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mFrm_Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem jmDisconnect;
    private javax.swing.JMenuItem jmSendFile;
    private javax.swing.JTextPane jpChat;
    private javax.swing.JTextField jtfContent;
    private javax.swing.JTextPane jtpOnlineList;
    // End of variables declaration//GEN-END:variables
}
