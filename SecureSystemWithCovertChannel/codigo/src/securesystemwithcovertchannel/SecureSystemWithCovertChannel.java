/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystemwithcovertchannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author estebanmuzio
 */
public class SecureSystemWithCovertChannel {

    private final ObjectManager objManager;
    private final ArrayList<SecureSubject> subjects;
    private final ReferenceMonitor refMonitor;
    private final InstructionObject instObject;
    private String currentChar;
    private int currentBit;
    private boolean endOfFileSent;
    private boolean firstTime;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SecureSystemWithCovertChannel is = new SecureSystemWithCovertChannel();
        FileLogger.initLog();
        FileLogger.initRecibido();
        is.loadDefaultSubjects();
        String rootDir = System.getProperty("user.dir");
        String messageFileName = args[0];
        String sequenceFileName = args[1];
        String sep = File.separator;
        is.loop(rootDir + sep + "inputs" + sep + messageFileName, rootDir + sep + "inputs" + sep +sequenceFileName);
    }

    public SecureSystemWithCovertChannel() {
        this.subjects = new ArrayList<>();
        this.objManager = new ObjectManager();
        this.refMonitor = new ReferenceMonitor(this.objManager);
        this.instObject = new InstructionObject(this.refMonitor);
        this.currentBit = 8;
        this.endOfFileSent = false;
        this.firstTime = true;
    }
        
    private void loadDefaultSubjects() {
        SecureSubject hal = new SecureSubject("hal");
        subjects.add(hal);
        refMonitor.addSubjectWithLevel(hal.getName(), Level.HIGH);
        SecureSubject moe = new SecureSubject("moe");
        subjects.add(moe);
        refMonitor.addSubjectWithLevel(moe.getName(), Level.MEDIUM);
        SecureSubject lyle = new SecureSubject("lyle");
        subjects.add(lyle);
        refMonitor.addSubjectWithLevel(lyle.getName(), Level.LOW);
    }
    
    private void loop(String uriMessage, String uriSequence) {
        File fileMessage = new File(uriMessage);
        File fileSequence = new File(uriSequence);
        try {
            InputStream messageIn = new FileInputStream(fileMessage);
            InputStream sequenceIn = new FileInputStream(fileSequence);
            int sequenceContent;
            while ((sequenceContent = sequenceIn.read()) != -1) {
                char currentSubject = (char) sequenceContent;
                switch(currentSubject) {
                    case 'H':
                        if (!endOfFileSent) {
                            if (currentBit > 7) {
                                int content = messageIn.read();
                                if (content != -1) {
                                    this.currentChar = getBinaryString(content);
                                } else {
                                    this.currentChar = "00000100";
                                    this.endOfFileSent = true;
                                }
                                this.currentBit = 0;
                            }
                            char bitToSend = this.currentChar.charAt(this.currentBit);
                            if (this.firstTime) {
                                while (Integer.parseInt(new SimpleDateFormat("ss").format(new Date()))%2 != 0) {};
                                this.firstTime = false;
                            }
                            boolean success = manageHal(bitToSend);
                            if (success) {
                                this.currentBit++;
                            }
                        }
                        break;
                    case 'M':
                        manageMoe();
                        break;
                    case 'L':
                        manageLyle();
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
     private String getBinaryString(int content) {
        String binChar = Integer.toBinaryString(content);
        while (binChar.length() < 8) {
            binChar = "0"+binChar;
        }
        return binChar;
    }
    
    private boolean manageHal(char bitToSend) {
        String subjName = "hal";
        String objName = "obj";
        SecureSubject hal = subjects.get(subjects.indexOf(new SecureSubject(subjName)));
        boolean success = hal.run();
        FileLogger.log("RUN " + subjName);
        if (success && (bitToSend == '0')) {
            this.instObject.create(subjName, objName);
            FileLogger.log("CREATE " + subjName + " " + objName);
        }
        return success;
    }
    
    private void manageMoe() {
        String subjName = "moe";
        String objName = "obj";
        int value = 9;
        SecureSubject moe = subjects.get(subjects.indexOf(new SecureSubject(subjName)));
        this.instObject.write(subjName, objName, value);
        FileLogger.log("WRITE " + subjName + " " + objName + " " + value);
        moe.run();
        FileLogger.log("RUN " + subjName);
    }
    
    private void manageLyle() {
        String subjName = "lyle";
        String objName = "obj";
        int value = 1;
        SecureSubject lyle = subjects.get(subjects.indexOf(new SecureSubject(subjName)));
        this.instObject.create(subjName, objName);
        FileLogger.log("CREATE " + subjName + " " + objName);
        this.instObject.write(subjName, objName, value);
        FileLogger.log("WRITE " + subjName + " " + objName + " " + value);
        int valueRead = this.instObject.read(subjName, objName);
        lyle.setTemp(valueRead);
        FileLogger.log("READ " + subjName + " " + objName);
        this.instObject.destroy(subjName, objName);
        FileLogger.log("DESTROY " + subjName + " " + objName);
        lyle.run();
        FileLogger.log("RUN " + subjName); 
    }
    
}
