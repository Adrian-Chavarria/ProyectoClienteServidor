/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The Communication class manages communication between a client and a server
 * through a socket. It handles sending and receiving messages, as well as
 * receiving files from the server. The class supports both text-based
 * communication and binary data transfer.
 *
 * @author Jocelyn
 * @author Jeison
 * @author Joseph
 * @author Adrian
 * @author Carlos
 */
public class Communication {

    private final Socket socket;
    private final PrintWriter print;
    private final BufferedReader buff;
    private final InputStream binaryIn;
  

    /**
     * Constructor for initializing the Communication class.
     *
     * @param socket The socket for communication.
     * @param outPrint The PrintWriter for sending text messages.
     * @param inRead The BufferedReader for receiving text messages.
     * @param binaryOut The OutputStream for sending binary data.
     * @param binaryIn The InputStream for receiving binary data.
     * @throws IOException If an I/O error occurs when creating the input
     * stream.
     */
    public Communication(Socket socket, PrintWriter outPrint, BufferedReader inRead, OutputStream binaryOut,
            InputStream binaryIn) throws IOException {
        this.socket = socket;
        this.print = outPrint;
        this.buff = inRead;
        this.binaryIn = socket.getInputStream();

    }

    /**
     * Sends a client message to the server.
     *
     * @param message The message to send.
     */
    public void clientMessage(String message) {
        print.println(message);
    }

    /**
     * Sends a message to the server.
     *
     * @param message The message to send.
     */
    public void sendMessage(String message) {
        if (print != null) {
            System.out.println("Enviando mensaje al servidor: " + message);
            print.println(message);
            print.flush();
        } else {
            System.err.println("Sin Inicializar");
        }
    }

    /**
     * Receives a message from the server.
     *
     * @return The message received from the server.
     * @throws IOException If an I/O error occurs while reading the message.
     */
    public String receiveMessage() throws IOException {
        if (buff != null) {
            String response = buff.readLine();
            System.out.println("Mensaje recibido del servidor: " + response);
            return response;
        } else {
            System.err.println(" Sin Inicializar");
            return null;
        }

    }

    /**
     * Receives a file from the server and saves it to the specified destination
     * path.
     *
     * @param destinationPath The path where the received file will be saved.
     */
    public void receiveFileFromServer(String destinationPath) {
        File destinationFile = new File(destinationPath);
        File parentDir = destinationFile.getParentFile();

        byte[] bufByte = new byte[8192];
        try (FileOutputStream fos = new FileOutputStream(destinationPath);
                BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            int bytesRead;

            System.out.println("Iniciando bucle de lectura de datos...");
            while ((bytesRead = binaryIn.read(bufByte)) != -1) {
                bos.write(bufByte, 0, bytesRead);                
            }
            System.out.println("Saliendo del bucle while...");
            bos.flush();
            System.out.println("Archivo recibido y guardado en: " + destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the communication by closing the input and output streams and the
     * socket.
     */
    
    public void closeProcess() {
        try {
            buff.close();
            print.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error" + e);
        }

    }

}
