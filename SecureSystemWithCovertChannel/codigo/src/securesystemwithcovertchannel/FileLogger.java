package securesystemwithcovertchannel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matiashrnndz
 */
public class FileLogger {
    
    private static OutputStream logTxt;
    private static OutputStream recibidoTxt;

    public static void initLog() {
        String rootDir = System.getProperty("user.dir");
        String sep = File.separator;
        File logFile = new File(rootDir + sep + "outputs" + sep + "log.txt");
        OutputStream logStream = null;
        try {
            logFile.delete();
            logFile.createNewFile();
            logStream = new FileOutputStream(logFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        logTxt = logStream;
    }
    
    public static void initRecibido() {
        String rootDir = System.getProperty("user.dir");
        String sep = File.separator;
        File logFile = new File(rootDir + sep + "outputs" + sep + "recibido.txt");
        OutputStream logStream = null;
        try {
            logFile.delete();
            logFile.createNewFile();
            logStream = new FileOutputStream(logFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        recibidoTxt = logStream;
    }
        
    public static void log(String logText) {
        logText += "\n";
        try {
            logTxt.write(logText.getBytes(Charset.forName("UTF-8")));
            logTxt.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }        
    }
    
    public static void recibido(String txt) {
        try {
            recibidoTxt.write(txt.getBytes(Charset.forName("UTF-8")));
            recibidoTxt.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }        
    }
    
}
