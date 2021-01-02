/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insecuresystem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author matiashrnndz
 */
public class InstructionObject {
    
    private Scanner fileScanner;

    public InstructionObject(String uri) {
        this.openFile(uri);
    }
    
    private void openFile(String uri) {
        try {
            File file = new File(uri);
            fileScanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Instruction processNextLine() {
        Instruction inst = null;
        if(fileScanner != null && fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().toLowerCase().trim();
            if (this.validateLine(line)) {
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
                        inst = new Instruction("", "", 0, InstructionType.BAD);
                        break;
                }
            } else {
                inst = new Instruction("", "", 0, InstructionType.BAD);
            };
        }
        return inst;
    }

    private boolean validateLine(String line) {
        boolean validation = line.matches("(read)\\s+(\\w+)\\s+(\\w+)") 
                || line.matches("(write)\\s+(\\w+)\\s+(\\w+)\\s+(\\d+)");
        return validation;
    }
    
    private String[] tokenizeLine(String line) {
        String[] tokens = line.split("\\s+");
        return tokens;
    }
}
