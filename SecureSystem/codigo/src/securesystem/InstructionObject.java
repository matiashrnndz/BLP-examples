/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author matiashrnndz
 */
public class InstructionObject {
    
    private Scanner fileScanner;
    private final ReferenceMonitor refMonitor;

    public InstructionObject(String uri, ReferenceMonitor refMonitor) {
        this.refMonitor = refMonitor;
        this.openFile(uri);
    }

    public Instruction processNextLine() {
        Instruction inst = null;
        if(fileScanner != null && fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().toLowerCase().trim();
            if (this.syntaxValidate(line)) {
                String[] tokens = this.tokenizeLine(line);
                String operation = tokens[0];
                String subject = tokens[1];
                String obj = tokens[2];
                switch (operation) {
                    case "read":
                        inst = new Instruction(subject, obj, 0, InstructionType.READ);
                        break;
                    case "write":
                        int value = Integer.parseInt(tokens[3]);
                        inst = new Instruction(subject, obj, value, InstructionType.WRITE);
                        break;
                    default:
                        inst = new Instruction();
                        break;
                }
            } else {
                inst = new Instruction();
            };
        }
        return inst;
    }
    
    public Instruction processWrite(Instruction inst) {
        if (refMonitor.existsSubject(inst.getSbjName())
            && refMonitor.existsObject(inst.getObjName())) {
            if (!refMonitor.executeWrite(inst)) {
                inst = new Instruction("", "", 0, InstructionType.BAD);
            }
        } else {
            inst = new Instruction();
        }
        return inst;
    }
    
    public Instruction processRead(Instruction inst) {
        if (refMonitor.existsSubject(inst.getSbjName())
            && refMonitor.existsObject(inst.getObjName())) {
            int valueRead = refMonitor.executeRead(inst);
            inst.setValue(valueRead);
        } else {
            inst = new Instruction();
        }
        return inst;
    }
    
    private void openFile(String uri) {
        try {
            File file = new File(uri);
            fileScanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean syntaxValidate(String line) {
        boolean validation = line.matches("(read)\\s+(\\w+)\\s+(\\w+)") 
                || line.matches("(write)\\s+(\\w+)\\s+(\\w+)\\s+(\\d+)");
        return validation;
    }
    
    private String[] tokenizeLine(String line) {
        String[] tokens = line.split("\\s+");
        return tokens;
    }
}
