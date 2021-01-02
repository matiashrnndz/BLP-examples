/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystem;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author matiashrnndz
 */
public class ObjectManager {
    
    private final ArrayList<Obj> objs;

    public ObjectManager() {
        objs = new ArrayList<>();
    }
    
    public void addObject(Obj obj) {
        objs.add(obj);
    }
    
    public int read(String obj) {
        int value = 0;
        Iterator it = objs.iterator();
        while (it.hasNext() && (value==0)) {
            Obj current = (Obj) it.next();
            if (obj.equalsIgnoreCase(current.getName())){
                value = current.getValue();
            }
        }
        return value;
    }
    
    public void write(String obj, int value) {
        for (Obj current : objs) {
            if (obj.equalsIgnoreCase(current.getName())){
                current.setValue(value);
                return;
            }
        }
    }

    @Override
    public String toString() {
        String objStates = "";
        for (Obj current : objs) {
            objStates += current.toString() + "\n";
        }
        return objStates;
    }
    
}
