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
public class Instruction {
    
    private InstructionType type;
    private String sbjName;
    private String objName;
    private int value;

    public Instruction() {
        this.type = InstructionType.BAD;
        this.sbjName = "";
        this.objName = "";
        this.value = 0;
    }
    
    public Instruction(String sbjName, String objName) {
        this.type = InstructionType.READ;
        this.sbjName = sbjName;
        this.objName = objName;
        this.value = 0;
    }

    public Instruction(String sbjName, String objName, int value) {
        this.type = InstructionType.WRITE;
        this.sbjName = sbjName;
        this.objName = objName;
        this.value = value;
    }
    
    public Instruction(String sbjName, String objName, InstructionType type) {
        this.type = type;
        this.sbjName = sbjName;
        this.objName = objName;
        this.value = 0;
    }

    public InstructionType getType() {
        return type;
    }

    public String getSbjName() {
        return sbjName;
    }

    public String getObjName() {
        return objName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        String action = "";
        switch (type) {
            case BAD:
                action = "Bad Instruction";
                break;
            case READ:
                action = this.sbjName + " reads " + this.objName;
                break;
            case WRITE:
                action = this.sbjName + " writes value " + this.value + " to " + this.objName;
                break;
            case CREATE:
                action = this.sbjName + " creates object " + this.objName + " with value 0 and same authorization level";
                break;
            case DESTROY:
                action = this.sbjName + " destroys object " + this.objName;
                break;
            default:
                break;
        }
        return action;
    }
    
}
