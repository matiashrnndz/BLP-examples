/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystemwithcovertchannel;

import java.util.HashMap;

/**
 *
 * @author matiashrnndz
 */
public class ReferenceMonitor {
    
    private final ObjectManager objManager;
    private final HashMap<String, Level> subjLevels;
    private final HashMap<String, Level> objLevels;

    public ReferenceMonitor(ObjectManager objManager) {
        this.objManager = objManager;
        this.subjLevels = new HashMap<>();
        this.objLevels = new HashMap<>();
    }
    
    public void addSubjectWithLevel(String subj, Level level) {
        subjLevels.put(subj, level);
    }
    
    public void addObjectWithLevel(String obj, Level level) {
        objLevels.put(obj, level);
    }
    
    public boolean existsSubject(String subj) {
        return subjLevels.containsKey(subj);
    }
    
    public boolean existsObject(String obj) {
        return objLevels.containsKey(obj);
    }
    
    public int executeRead(Instruction inst) {
        Level subjLevel = subjLevels.get(inst.getSbjName());
        Level objLevel = objLevels.get(inst.getObjName());
        int valueRead = 0;
        if (SecurityLevel.dominates(subjLevel, objLevel)) {
            valueRead = objManager.read(inst.getObjName());
        }
        return valueRead;
    }
    
    public boolean executeWrite(Instruction inst) {
        Level objLevel = objLevels.get(inst.getObjName());
        Level subjLevel = subjLevels.get(inst.getSbjName());
        boolean wrote = false;
        if (SecurityLevel.dominates(objLevel, subjLevel)) {
            objManager.write(inst.getObjName(), inst.getValue());
            wrote = true;
        }
        return wrote;
    }

    public void executeCreate(Instruction inst) {
        Level subjLevel = subjLevels.get(inst.getSbjName());
        objLevels.put(inst.getObjName(), subjLevel);
        objManager.create(inst.getObjName());
    }

    public boolean executeDestroy(Instruction inst) {
        Level objLevel = objLevels.get(inst.getObjName());
        Level subjLevel = subjLevels.get(inst.getSbjName());
        boolean destroyed = false;
        if (SecurityLevel.dominates(objLevel, subjLevel)) {
            objManager.destroy(inst.getObjName());
            objLevels.remove(inst.getObjName());
            destroyed = true;
        }
        return destroyed;
    }
    
}
