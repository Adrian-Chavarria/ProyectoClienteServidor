package Servidor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The MediaManipulator class provides methods to retrieve and manage media
 * files from specific directories within a files folder. This includes methods
 * to list music files, videos, and documents.
 *
 * @author Jocelyn
 * @author Jeison
 * @author Joseph
 * @author Adrian
 * @author Carlos
 */
public class MediaManipulator {

    private static final String FILES = "files";

    /**
     * Retrieves a list of music files from the "FILES/musica" directory.
     *
     * @return A list of File objects representing music files.
     */
    public static List<File> getMusic() {
        List<File> musicFiles = new ArrayList<>();
        File musicFolder = new File(FILES);
        File subFolder = new File(musicFolder, "musica");

        if (subFolder.exists() && subFolder.isDirectory()) {
            File[] files = subFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isMusic(file.getName())) {
                        musicFiles.add(file);
                    }
                }
            }
        }
        return musicFiles;
    }

    /**
     * Retrieves a list of admin music files from the "Files/musicaAdmin"
     * directory.
     *
     * @return A list of File objects representing admin music files.
     */
    public static List<File> getMusicAdmin() {
        List<File> musica = new ArrayList<>();
        File Musicafolder = new File(FILES);
        File subFolder = new File(Musicafolder, "musicaAdmin");
        if (subFolder.exists() && subFolder.isDirectory()) {
            File[] music = subFolder.listFiles();
            System.out.println("archivos: " + music);
            if (music != null) {
                for (File file : music) {
                    System.out.println("Musica encontrada: " + file.getName());
                    if (file.isFile() && isMusic(file.getName())) {
                        System.out.println("Musica añadida: " + file.getName());
                        musica.add(file);
                    }
                }
            }
        }
        return musica;
    }

    /**
     * Retrieves a list of video files from the "Files/videos" directory.
     *
     * @return A list of File objects representing video files.
     */
    public static List<File> getVideos() {
        List<File> videos = new ArrayList<>();
        File Videosfolder = new File(FILES);
        File subFolder = new File(Videosfolder, "videos");
        if (subFolder.exists() && subFolder.isDirectory()) {
            File[] files = subFolder.listFiles();
            System.out.println("archivos: " + files);
            if (files != null) {
                for (File file : files) {
                    System.out.println("Video encontrado: " + file.getName());
                    if (file.isFile() && isVideo(file.getName())) {
                        System.out.println("Video añadido: " + file.getName());
                        videos.add(file);
                    }
                }
            }
        }
        return videos;
    }

    /**
     * Retrieves a list of admin video files from the "Files/videosAdmin"
     * directory.
     *
     * @return A list of File objects representing admin video files.
     */
    public static List<File> getVideosAdmin() {
        List<File> videos = new ArrayList<>();
        File Videosfolder = new File(FILES);
        File subFolder = new File(Videosfolder, "videosAdmin");
        if (subFolder.exists() && subFolder.isDirectory()) {
            File[] files = subFolder.listFiles();
            System.out.println("archivos: " + files);
            if (files != null) {
                for (File file : files) {
                    System.out.println("Video encontrado: " + file.getName());
                    if (file.isFile() && isVideo(file.getName())) {
                        System.out.println("Video añadido: " + file.getName());
                        videos.add(file);
                    }
                }
            }
        }
        return videos;
    }

    /**
     * Retrieves a list of document files from the "Files/documentos" directory.
     *
     * @return A list of File objects representing document files.
     */
    public static List<File> getFiles() {
        List<File> files = new ArrayList<>();
        File Filesfolder = new File(FILES);
        File subFolder = new File(Filesfolder, "documentos");

        if (subFolder.exists() && subFolder.isDirectory()) {
            File[] allFiles = subFolder.listFiles();
            if (allFiles != null) {
                for (File file : allFiles) {
                    if (file.isFile()) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }

    /**
     * Retrieves a list of admin files files from the "Files/documentosAdmin"
     * directory.
     *
     * @return A list of File objects representing admin files.
     */
    public static List<File> getFilesAdmin() {
        List<File> files = new ArrayList<>();
        File Filesfolder = new File(FILES);
        File subFolder = new File(Filesfolder, "documentosAdmin");

        if (subFolder.exists() && subFolder.isDirectory()) {
            File[] allFiles = subFolder.listFiles();
            System.out.println("archivos: " + files);
            if (allFiles != null) {
                for (File allFile : allFiles) {
                    System.out.println("Documento encontrado: " + allFile.getName());
                    if (allFile.isFile() && isFile(allFile.getName())) {
                        System.out.println("Documento añadido: " + allFile.getName());
                        files.add(allFile);
                    }
                }

            }
        }
        return files;
    }
    /**
     * Checks if a given file name corresponds to a file.
     *
     * @param fileName The name of the file to check.
     * @return True if the file is a file, otherwise false.
     */
    private static boolean isFile(String fileName) {
        String fileextension = fileName.substring(fileName.lastIndexOf(".") + 1);

        return fileextension.equalsIgnoreCase("txt") || fileextension.equalsIgnoreCase("xml")
                || fileextension.equalsIgnoreCase(".docx");
    }
    /**
     * Checks if a given file name corresponds to a music file.
     *
     * @param fileName The name of the file to check.
     * @return True if the file is a music file, otherwise false.
     */
    private static boolean isMusic(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        return extension.equalsIgnoreCase("mp3") || extension.equalsIgnoreCase("wav")
                || extension.equalsIgnoreCase("flac");
    }

    /**
     * Checks if a given file name corresponds to a video file.
     *
     * @param fileName The name of the file to check.
     * @return True if the file is a video file, otherwise false.
     */
    private static boolean isVideo(String fileName) {
        String[] videoExtensions = {".mp4", ".avi", ".mkv", ".mov", ".webm"};
        for (String ext : videoExtensions) {
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
