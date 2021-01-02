/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystemwithcovertchannel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author matiashrnndz
 */
public class ObjectManager {
    
    private ArrayList<SecureObject> objs;

    public ObjectManager() {
        objs = new ArrayList<>();
    }
    
    public void addObject(SecureObject obj) {
        objs.add(obj);
    }
    
    public int read(String obj) {
        int value = 0;
        Iterator it = objs.iterator();
        while (it.hasNext() && (value==0)) {
            SecureObject current = (SecureObject) it.next();
            if (obj.equalsIgnoreCase(current.getName())){
                value = current.getValue();
            }
        }
        return value;
    }
    
    public void write(String obj, int value) {
        Iterator it = objs.iterator();
        while (it.hasNext()) {
            SecureObject current = (SecureObject) it.next();
            if (obj.equalsIgnoreCase(current.getName())){
                current.setValue(value);
                return;
            }
        }
    }
    
    public void create(String objName) {
        SecureObject newObj = new SecureObject(objName);
        objs.add(newObj);
    }

    public void destroy(String objName) {
        objs.remove(new SecureObject(objName));
    }
    
    @Override
    public String toString() {
        String objStates = "";
        Iterator it = objs.iterator();
        while (it.hasNext()) {
            SecureObject current = (SecureObject) it.next();
            objStates += current.toString() + "\n";
        }
        return objStates;
    }

    
}
