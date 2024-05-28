package Servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import nuevoClienteServidor.ViewClient;

/**
 * The Server class handles client connections and provides various services,
 * including user authentication, file retrieval, and file transfer.
 *
 * @author Jocelyn
 * @author Jeison
 * @author Joseph
 * @author Adrian
 * @author Carlos
 */
public class Server {

    private static final int PORT = 8081;
    private static final int MAX_THREADS = 10;
    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
    public static String rol;

    /**
     * Main method to start the server.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Puerto del Server: "
                    + PORT);
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("Dirección IP del servidor: "
                    + inetAddress.getHostAddress());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("IP Cliente "
                        + clientSocket.getInetAddress());
                executor.execute(() -> handleClientSession(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    /**
     * Retrieves the role of a user.
     *
     * @param userName The username of the user
     * @param encryptedPassword The encrypted password of the user
     * @return The role of the user
     */
    private static String obteinRol(String userName, String encryptedPassword) {
        String newRol = "null";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 3 && parts[0].equals(userName)
                        && parts[1].equals(encryptedPassword)) {
                    System.out.println(parts[2]);
                    newRol = parts[2];
                    return newRol;
                } else {
                    System.out.println("No se ha encontrado similitud");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return newRol;
    }

    /**
     * Handles a client session.
     *
     * @param clientSocket The socket of the client
     */
    private static void handleClientSession(Socket clientSocket) {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); OutputStream fileOut = clientSocket.getOutputStream()) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje del cliente: " + inputLine);
                String[] AuthParts = inputLine.split(",");
                
                if (AuthParts[0].startsWith("GET_") || AuthParts[0].startsWith("DOWNLOAD")) {
                      if (AuthParts[0].startsWith("DOWNLOAD")) {
                          
                        handleOtherRequests(inputLine, out, fileOut, rol);
                    }
                    handleOtherRequests(AuthParts[0], out, fileOut, rol);
                } else {

                    String action = AuthParts[0];
                    String userName = AuthParts[1];
                    String encryptedPassword = AuthParts[2];
                    String rol = AuthParts[3];

                    if (action.equals("REGISTRO")) {
                        saveUserRegistration(userName, encryptedPassword, rol);
                        out.println("REGISTRO");

                    } else if (action.equals("LOGIN")) {
                        Server.rol = obteinRol(userName, encryptedPassword);
                        System.out.println("Este es el rol del usuario"
                                + Server.rol);
                        String isAuthenticated = authenticateUser(userName,
                                encryptedPassword, rol);
                        if (isAuthenticated != null
                                && isAuthenticated.equals("LOGIN")) {
                            out.println("LOGIN," + Server.rol);
                        } else {
                            out.println("LOGIN FALLIDO");
                            clientSocket.close();
                            break;
                        }
                    } else {
                        out.println("SOLITUD INVALIDA");
                    }
                }

            }
        } catch (IOException e) {
            System.err.println("Error al manejar la conexión con el cliente: "
                    + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket del cliente: "
                        + e.getMessage());
            }
        }
    }

    /**
     * Saves user registration information to a file.
     *
     * @param userName The username of the user
     * @param encryptedPassword The encrypted password of the user
     * @param rol The role of the user
     */
    private static void saveUserRegistration(String userName,
            String encryptedPassword, String rol) {
        try (BufferedWriter writer
                = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(userName + "," + encryptedPassword + "," + rol);
            writer.newLine();
            System.out.println("Usuario registrado correctamente: "
                    + userName + encryptedPassword);
        } catch (IOException e) {
            System.err.println("Error al guardar el usuario en la base de datos: "
                    + e.getMessage());
        }
    }

    /**
     * Authenticates a user.
     *
     * @param userName The username of the user
     * @param encryptedPassword The encrypted password of the user
     * @param rol The role of the user
     * @return Authentication status
     */
    private static String authenticateUser(String userName,
            String encryptedPassword, String rol) {
        System.out.println("Nombre: " + userName + "Contraseña: "
                + encryptedPassword + "ROL: " + rol);
        String line;
        try (BufferedReader br
                = new BufferedReader(new FileReader("users.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 3 && parts[0].equals(userName)
                        && parts[1].equals(encryptedPassword)) {                    
                    return "LOGIN";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Handles requests different from the main request on the server.
     *
     * @param inputLine The input line received from the client containing the
     * request.
     * @param out The PrintWriter to send data to the client.
     * @param fileOut The OutputStream to send files to the client.
     * @param role The role of the user making the request.
     */
    private static void handleOtherRequests(String inputLine, PrintWriter out,
            OutputStream fileOut, String rol) {
    
        if (inputLine.startsWith("GET_")) {
            String requestType = inputLine.substring(4);
            switch (requestType) {
                case "VIDEOS":
                    List<File> videos = MediaManipulator.getVideos();
                    List<File> combinedVideos = new ArrayList<>(videos);

                    if (rol.equals("Admin")) {
                        List<File> videosAdmin
                                = MediaManipulator.getVideosAdmin();
                        combinedVideos.addAll(videosAdmin);
                    }
                    sendFilesList(out, combinedVideos);

                    break;

                case "FILES":
                    List<File> files = MediaManipulator.getFiles();
                    List<File> combinedFiles = new ArrayList<>(files);
                    
                    if (rol.equals("Admin")) {
                        List<File> filesAdmin
                                = MediaManipulator.getFilesAdmin();
                                combinedFiles.addAll(filesAdmin);
                    }
                   
                    sendFilesList(out, combinedFiles);
                    break;
                case "MUSIC":
                    List<File> music = MediaManipulator.getMusic();
                    List<File> combinedMusic = new ArrayList<>(music);
                    
                    if (rol.equals("Admin")) {
                        List<File> musicAdmin
                                = MediaManipulator.getMusicAdmin();
                                combinedMusic.addAll(musicAdmin);
                    }
                    sendFilesList(out, combinedMusic);
                    break;
                default:
                    System.err.println("Tipo de solicitud no válido: "
                            + requestType);
                    break;
            }
        } else if (inputLine.startsWith("DOWNLOAD,")) {
                String[] parts = inputLine.split(",");               
            if (parts.length <= 5) {
                String tipo = parts[1];
                String file = parts[2];

                String filePath = "";
                String adminPath = "";

                switch (tipo) {
                    case "MUSIC":
                        filePath = "files/musica/" + file;
                        adminPath = "files/musicaAdmin/" + file;
                        break;
                    case "VIDEO":
                        filePath = "files/videos/" + file;
                        adminPath = "files/videosAdmin/" + file;
                        break;
                    case "FILE":
                        filePath = "files/documentos/" + file;
                        adminPath = "files/documentosAdmin/" + file;
                        break;
                    default:
                        System.out.println("Tipo de dato no existe");
                        return;
                }

                System.out.println("Enviando archivo: " + filePath);
                sendFileToClient(filePath, adminPath, rol, fileOut);
            } else {
                System.out.println("Tipo de dato no existe");
            }
        }

    }

    /**
     * Sends a list of file names to the client via the specified PrintWriter.
     *
     * @param out The PrintWriter to send data to the client.
     * @param files The list of files whose names will be sent to the client.
     */
    private static void sendFilesList(PrintWriter out, List<File> files) {
        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            sb.append(file.getName()).append(";");
        }
        sb.append("END_OF_LIST");
        out.println(sb.toString());
    }

    /**
     * Send a file to the client via the specified OutputStream.
     *
     * @param filePath The path of the file to send.
     * @param adminPath The path of the admin file to send if the user has
     * administrator privileges.
     * @param role The role of the user requesting the file.
     * @param fileOut The OutputStream to send the file to the client.
     * @param out The PrintWriter to send messages to the client.
     */
    private static void sendFileToClient(String filePath, String adminPath,
            String rol, OutputStream fileOut) {
        File file = new File(filePath);

        if (!file.exists()) {

            if (rol.equals("Admin")) {
                file = new File(adminPath);
            } else {
                System.out.println("El archivo no existe");
                return;
            }

        }

        try (FileInputStream fileInputStream = new FileInputStream(file); BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);) {
            byte[] fileData = new byte[(int) file.length()];
            bufferedInputStream.read(fileData, 0, fileData.length);

            fileOut.write(fileData, 0, fileData.length);
            fileOut.flush();

            System.out.println("Archivo enviado: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
