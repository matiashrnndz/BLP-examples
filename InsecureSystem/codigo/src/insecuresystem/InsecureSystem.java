/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insecuresystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author estebanmuzio
 */
public class InsecureSystem {

    private final InstructionObject instObject;
    private final ObjectManager objManager;
    private final ArrayList<Subject> subjects;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String instructionListFileName = args[0];
        FileLogger.initLog();
        InsecureSystem is = new InsecureSystem(instructionListFileName);
        is.loadDefaultSubjects();
        is.manageInstructions();
    }

    public InsecureSystem(String instructionListFileName) {
        String rootDir = System.getProperty("user.dir");
        String sep = File.separator;
        this.instObject = new InstructionObject(rootDir + sep + "inputs" + sep + instructionListFileName);
        this.objManager = new ObjectManager();
        this.subjects = new ArrayList<>();
    }
    
    private void loadDefaultSubjects() {
        Subject hal = new Subject("hal");
        subjects.add(hal);
        Subject moe = new Subject("moe");
        subjects.add(moe);
        Subject lyle = new Subject("lyle");
        subjects.add(lyle);
    }
    
    private void manageInstructions() {
        Instruction currentInst = null;
        do {
            currentInst = instObject.processNextLine();
            if (currentInst != null) {
                switch (currentInst.getType()) {
                    case BAD:
                        break;
                    case READ:
                        this.read(currentInst.getSbjName(), currentInst.getObjName());
                        break;
                    case WRITE:
                        this.write(currentInst.getSbjName(), currentInst.getObjName(), currentInst.getValue());
                        break;
                    default:
                        break;
                }
                String action = currentInst.toString();
                this.printState(action);
            }
        } while(currentInst != null);
    }
    
    private void read(String subjName, String objName) {
        Subject currentSbj = new Subject(subjName);
        int lastValueRead = 0;
        if (subjects.contains(currentSbj)) {
            currentSbj = subjects.get(subjects.indexOf(currentSbj));
            lastValueRead = objManager.read(objName);
            currentSbj.setTemp(lastValueRead);
        }
    }
    
    private void write(String subjName, String objName, int value) {
        Subject currentSbj = new Subject(subjName);
        if (subjects.contains(currentSbj)) {
            objManager.write(objName, value);
        }
    }
    
    private void printState(String action) {
        String objectStates = objManager.toString();
        String subjectStates = this.toString();
        String textToPrint = action + "\n"
            + "The current state is:" + "\n"
            + objectStates
            + subjectStates;
        FileLogger.log(textToPrint);
        System.out.println(textToPrint);
    }

    @Override
    public String toString() {
        String subjectStates = "";
        for (Subject current : subjects) {
            subjectStates += current.toString() + "\n";
        }
        return subjectStates;
    }

}
