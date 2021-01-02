/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystem;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author estebanmuzio
 */
public class SecureSystem {

    private final InstructionObject instObject;
    private final ObjectManager objManager;
    private final ArrayList<Subject> subjects;
    private final ReferenceMonitor referenceMonitor;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String instructionListFileName = args[0];
        FileLogger.initLog();
        SecureSystem is = new SecureSystem(instructionListFileName);
        is.loadDefaultData();
        is.manageInstructions();
    }

    public SecureSystem(String instructionListFileName) {
        String rootDir = System.getProperty("user.dir");
        this.subjects = new ArrayList<>();
        this.objManager = new ObjectManager();
        this.referenceMonitor = new ReferenceMonitor(this.objManager);
        String sep = File.separator;
        this.instObject = new InstructionObject(rootDir + sep + "inputs" + sep + instructionListFileName, this.referenceMonitor);
    }
    
    private void loadDefaultData() {
       this.manageSubjects();
       this.manageObjects();
    }
    
    private void manageSubjects() {
        Subject hal = new Subject("hal");
        subjects.add(hal);
        referenceMonitor.addSubjectWithLevel(hal.getName(), Level.HIGH);
        Subject moe = new Subject("moe");
        subjects.add(moe);
        referenceMonitor.addSubjectWithLevel(moe.getName(), Level.MEDIUM);
        Subject lyle = new Subject("lyle");
        subjects.add(lyle);
        referenceMonitor.addSubjectWithLevel(lyle.getName(), Level.LOW);
    }
    
    private void manageObjects() {
        Obj hobj = new Obj("hobj");
        objManager.addObject(hobj);
        referenceMonitor.addObjectWithLevel(hobj.getName(), Level.HIGH);
        Obj mobj = new Obj("mobj");
        objManager.addObject(mobj);
        referenceMonitor.addObjectWithLevel(mobj.getName(), Level.MEDIUM);
        Obj lobj = new Obj("lobj");
        objManager.addObject(lobj);
        referenceMonitor.addObjectWithLevel(lobj.getName(), Level.LOW);
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
                        currentInst = this.read(currentInst);
                        break;
                    case WRITE:
                        currentInst = this.write(currentInst);
                        break;
                    default:
                        break;
                }
                String action = currentInst.toString();
                this.printState(action);
            }
        } while(currentInst != null);
    }
    
    private Instruction read(Instruction inst) {
        Instruction processedInst = instObject.processRead(inst);
        int lastRead = 0;
        Subject currentSbj = new Subject(inst.getSbjName());
        if (processedInst.getType() != InstructionType.BAD) {
            if (subjects.contains(currentSbj)) {
                lastRead = processedInst.getValue();
                currentSbj = subjects.get(subjects.indexOf(currentSbj));
            }
        } 
        currentSbj.setTemp(lastRead);
        return processedInst;
    }
    
    private Instruction write(Instruction inst) {
        return instObject.processWrite(inst);
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
