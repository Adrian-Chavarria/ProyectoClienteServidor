/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package nuevoClienteServidor;

import Servidor.AesEcryption;
import Servidor.ClientAuthentication;
import Servidor.Communication;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The CheckIn class represents the user registration window in the client application.
 * Allows users to register by providing a username, password and selecting their role (admin or user).
 * The class communicates with the server to register new users.
 * 
 * 
 */

public class CheckIn extends javax.swing.JFrame {

    public static Socket sharedSocket;
    public static boolean sharedAuth;
    public static String  shareRol;
  
    private static final String SERVER_ADDRESS = "192.168.0.2"; 
    private static final int SERVER_PORT = 8081; 

    private ClientAuthentication authentication = new ClientAuthentication();

    backgroundPanel fondo = new backgroundPanel();

    
    public CheckIn() {
        this.setContentPane(fondo);
        initComponents();
        addPlaceHolderStyle(txtUserNew);
        addPlaceHolderStyle(txtPasswordNew);
    }
   
/**
 * 
 * @return shareRol
 */
    public static String getShareRol() {
        return shareRol;
    }

    /**
 * Applies a placeholder style to the specified text field.
 * Change the text field font to italic and set the text color to gray.
 *
 * @param textField The JTextField to which the placeholder style will be applied.
 */
    public void addPlaceHolderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.gray);
    }

    /**
 * Removes the placeholder style from the specified text field.
 * Change the text field font to italic and restore the text color to black.
 *
 * @param textField The JTextField from which the placeholder style will be removed.
 */
    
    public void removePlaceHolderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.black);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new backgroundPanel();
        jLabel5 = new javax.swing.JLabel();
        txtUserNew = new javax.swing.JTextField();
        btnCheckIn = new javax.swing.JButton();
        txtPasswordNew = new javax.swing.JPasswordField();
        radioButtonAdmin = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario.png"))); // NOI18N

        txtUserNew.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtUserNew.setText("Usuario");
        txtUserNew.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserNewFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUserNewFocusLost(evt);
            }
        });

        btnCheckIn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCheckIn.setText("Registrarse");
        btnCheckIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckInActionPerformed(evt);
            }
        });

        txtPasswordNew.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtPasswordNew.setText("Contraseña");
        txtPasswordNew.setEchoChar('\u0000');
        txtPasswordNew.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordNewFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordNewFocusLost(evt);
            }
        });

        radioButtonAdmin.setText("Usuario Admin");
        radioButtonAdmin.setPreferredSize(new java.awt.Dimension(125, 24));
        radioButtonAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonAdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(radioButtonAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtUserNew, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPasswordNew, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(287, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUserNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(txtPasswordNew, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioButtonAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserNewFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserNewFocusGained
        if (txtUserNew.getText().equals("Usuario")) {
            txtUserNew.setText(null);
            txtUserNew.requestFocus();

            removePlaceHolderStyle(txtUserNew);

        }
    }//GEN-LAST:event_txtUserNewFocusGained

    private void txtUserNewFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserNewFocusLost
        if (txtUserNew.getText().length() == 0) {
            addPlaceHolderStyle(txtUserNew);
            txtUserNew.setText("Usuario");

        }
    }//GEN-LAST:event_txtUserNewFocusLost

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.requestFocusInWindow();
    }//GEN-LAST:event_formWindowGainedFocus
/**
/**
 * Handles the click event of the "Register" button in the registration window.
 * This method gets the username and password entered by the user, as well as the selected role (user or administrator).
 * Then, it establishes a connection with the server using a socket and sends a message to the server to register the new user.
 * If the server response indicates that registration was successful, display the login window.
 * If the response indicates an error, display an error message to the user.
 *
 * @param evt The action event that triggered this method (in this case, the click of the "Register" button).
 */
 
    private void btnCheckInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckInActionPerformed
        String username = txtUserNew.getText();
        String password = txtPasswordNew.getText();
        
         if (radioButtonAdmin.isSelected()) {
            shareRol = "Admin";
        } else {
            shareRol = "Usuario";
        }

        System.out.println("Rol admin: " + shareRol);
        
        
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); 
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream binaryOut = socket.getOutputStream(); 
                InputStream binaryIn = socket.getInputStream()) {
            
                System.out.println("Dirección del socket en Login: " + socket.getLocalAddress() + ":" + socket.getLocalPort());
                String encryptedPassword = AesEcryption.encrypt(password);
                
                Communication clientCommunication = new Communication(socket, out, in, binaryOut, binaryIn);
                clientCommunication.sendMessage("REGISTRO," + username + "," + encryptedPassword + "," + shareRol );

         
            String response = clientCommunication.receiveMessage();
           
            System.out.println("Respuesta del servidor: " + response );
            if (response.equals("REGISTRO")) { 
               
                 LoginClient newFrame = new LoginClient();
                    newFrame.setVisible(true);
                    this.dispose();
                    in.close();
                
            } else {
                JOptionPane.showMessageDialog(this, "Error: Fallo al autenticar con el servidor");
            }

        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor");
        } catch (Exception ex) {
            Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCheckInActionPerformed

    private void txtPasswordNewFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordNewFocusGained
        if (txtPasswordNew.getText().equals("Contraseña")) {
            txtPasswordNew.setText(null);
            txtPasswordNew.requestFocus();

            txtPasswordNew.setEchoChar('*');

            removePlaceHolderStyle(txtPasswordNew);
        }
    }//GEN-LAST:event_txtPasswordNewFocusGained

    private void txtPasswordNewFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordNewFocusLost
        if (txtPasswordNew.getText().length() == 0) {
            addPlaceHolderStyle(txtPasswordNew);
            txtPasswordNew.setText("Contraseña");
            txtPasswordNew.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_txtPasswordNewFocusLost

    private void radioButtonAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonAdminActionPerformed
     
    }//GEN-LAST:event_radioButtonAdminActionPerformed
/**
 * 
 * @param args 
 */
public static void main(String args[]) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("Dirección IP del servidor: " + inetAddress.getHostAddress());
        } catch (UnknownHostException e) {
            System.err.println("No se pudo obtener la dirección IP del servidor: " + e.getMessage());
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginClient().setVisible(true);

            }

        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckIn;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton radioButtonAdmin;
    private javax.swing.JPasswordField txtPasswordNew;
    private javax.swing.JTextField txtUserNew;
    // End of variables declaration//GEN-END:variables
/**
 * **
 * Custom class that extends JPanel and is used to display a background image on a panel.
 * Cita: Maxita. (2015, 15 de octubre). How to Implement 
 * Bubble Sort in Java [Video]. YouTube. https://youtu.be/CfKlAHInank?si=XD_Du-OgDjimZ9OD
 */
 
    class backgroundPanel extends JPanel {

        private Image imagen;

        @Override
        public void paint(Graphics g) {

            imagen = new ImageIcon(getClass().getResource("/Imagenes/fondoAzul.png")).getImage();

            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

            setOpaque(false);

            super.paint(g);
        }

    }
}
