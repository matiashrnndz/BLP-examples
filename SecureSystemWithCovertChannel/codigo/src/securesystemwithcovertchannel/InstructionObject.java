/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystemwithcovertchannel;

/**
 *
 * @author matiashrnndz
 */
public class InstructionObject {

    private final ReferenceMonitor refMonitor;
    
    public InstructionObject(ReferenceMonitor refMonitor) {
        this.refMonitor = refMonitor;
    }

    public int read(String subjName, String objName) {
        int valueRead = 0;
        if (refMonitor.existsSubject(subjName)
            && refMonitor.existsObject(objName)) {
            Instruction inst = new Instruction(subjName, objName, InstructionType.READ);
            valueRead = refMonitor.executeRead(inst);
        }
        return valueRead;
    }
     
    public void write(String subjName, String objName, int value) {
        if (refMonitor.existsSubject(subjName)
            && refMonitor.existsObject(objName)) {
            Instruction inst = new Instruction(subjName, objName, value);
            refMonitor.executeWrite(inst);
        }
    }
    
    public void create(String subjName, String objName) {
        if (refMonitor.existsSubject(subjName)
            && !refMonitor.existsObject(objName)) {
            Instruction inst = new Instruction(subjName, objName, InstructionType.CREATE);
            refMonitor.executeCreate(inst);
        }
    }

    public void destroy(String subjName, String objName) {
        if (refMonitor.existsSubject(subjName)
            && refMonitor.existsObject(objName)) {
            Instruction inst = new Instruction(subjName, objName, InstructionType.DESTROY);
            refMonitor.executeDestroy(inst);
        }
    }

}
