/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystem;

/**
 *
 * @author matiashrnndz
 */
public class Obj {
    
    private String name;
    private int value;

    public Obj(String name) {
        this.name = name;
        this.value = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + " has value: " + value;
    }
    
}
