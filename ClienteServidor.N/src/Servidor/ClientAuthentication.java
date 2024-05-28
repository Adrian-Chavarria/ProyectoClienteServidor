/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ClientAuthentication class handles user authentication by storing,
 * encrypting, and verifying user credentials. It uses the AES encryption
 * algorithm to secure passwords and stores the data in a plain text file named
 * users.txt.
 *
 * @author Jocelyn
 * @author Jeison
 * @author Joseph
 * @author Adrian
 * @author Carlos
 */
public class ClientAuthentication {

    private static final String File = "users.txt";

    /**
     * Registers a new user in the system.
     *
     * @param userName The username.
     * @param password The user's password.
     * @param rol The user's role.
     * @throws Exception If an error occurs during encryption.
     */
    public void checkInUsers(String userName, String password, String rol) throws Exception {
        System.out.println(userName + "," + password + "," + rol);

        try (FileWriter fw = new FileWriter("users.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            String encryptedPassword = AesEcryption.encrypt(password);

            pw.println(userName + "," + encryptedPassword + "," + rol);
            System.out.println("Usuario registrado correctamente: " + userName + encryptedPassword);

        } catch (IOException e) {
            System.out.println("Problemas al guardar usuario: " + e.getMessage());

        }
    }

    /**
     * Encrypts and saves a password to the file.
     *
     * @param password The password to encrypt.
     * @param password1 An additional parameter not used.
     * @param usuario An additional parameter not used.
     */
    public void encryptAndSavePassword(String password, String password1, String usuario) {
        try {
            String encryptedPassword = AesEcryption.encrypt(password);
            saveEncryptedPasswordToFile(encryptedPassword);
        } catch (Exception e) {
            System.err.println("Error al encriptar contraseña: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user in the system by verifying their username and
     * password.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @param role The role of the user trying to log in.
     * @return A message indicating whether the authentication was successful or
     * not, along with the user's role if successful, or "false-false" on
     * failure.
     */
    
    public String authenticateUser(String username, String password, String rol) {
        try (BufferedReader reader = new BufferedReader(new FileReader(File))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String storedUsername = parts[0];
                String encripted = parts[1];
                System.out.println("Contraseña " + parts[1]);
                String decryptedPassword = AesEcryption.decrypt(verifyPassword(encripted));

                if (username.equals(storedUsername) && password.equals(decryptedPassword)) {
                    return "LOGIN" + rol;
                }
            }
        } catch (IOException e) {
            System.err.println("Error para leer la base de datos: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ClientAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "false-false";
    }

    /**
     * Saves a user's encrypted password in the "users.txt" file.
     *
     * @param encryptedPassword The encrypted password to save to the file.
     */
    
    private void saveEncryptedPasswordToFile(String encryptedPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(encryptedPassword);
            writer.newLine();
            System.out.println("Contraseña encriptada guardada correctamente en la base de datos");
        } catch (IOException e) {
            System.err.println("Error al guardar la contraseña encriptada en la base de datos: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user by verifying their credentials.
     *
     * @param username The username.
     * @param password The user's password.
     * @param rol The user's role.
     * @return "LOGIN" followed by the role if authentication is successful,
     * "false-false" if it fails.
     */
    private String verifyPassword(String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(password)) {
                    return parts[1];
                }
            }

            return null;
        } catch (IOException e) {
            System.err.println("Error al obtener la contraseña encriptada desde la base de datos: " + e.getMessage());
            return null;
        }
    }
}
