/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securesystemwithcovertchannel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author matiashrnndz
 */
public class SecureSubject {
    
    private String name;
    private int temp;
    private String currentCharInBits;

    public SecureSubject(String name) {
        this.name = name;
        this.temp = 0;
        this.currentCharInBits = ""; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return name + " has recently read: " + temp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SecureSubject other = (SecureSubject) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public boolean run() {
        Date date = new Date();
        String precision = "ss";
        String formattedDate = new SimpleDateFormat(precision).format(date);
        int seconds = Integer.parseInt(formattedDate);
        int mod = seconds % 2;
        boolean success = false;
        switch(this.name) {
            case "hal": 
                if (mod != 0) {
                    while (Integer.parseInt(new SimpleDateFormat(precision).format(new Date()))%2 != mod) {};
                } else {
                    while (Integer.parseInt(new SimpleDateFormat(precision).format(new Date()))%2 != 1) {};
                    success = true;
                }
                break;
            case "moe":
                //while (Integer.parseInt(new SimpleDateFormat(precision).format(new Date()))%2 != mod) {};
                break;
            case "lyle":
                if (mod != 1) {
                    while (Integer.parseInt(new SimpleDateFormat(precision).format(new Date()))%2 != mod) {};
                } else {
                    if (this.currentCharInBits.length() < 7) {
                        if (this.getTemp() == 0) {
                            this.currentCharInBits += "0";
                        } else if (this.getTemp() == 1) {
                            this.currentCharInBits += "1";
                        }
                    } else if (this.currentCharInBits.length() == 7){
                        if (this.getTemp() == 0) {
                            this.currentCharInBits += "0";
                        } else if (this.getTemp() == 1) {
                            this.currentCharInBits += "1";
                        }
                        int currentCharInInt = Integer.parseInt(this.currentCharInBits, 2);
                        char currentCharInChar = (char) currentCharInInt;
                        String currentCharInString = String.valueOf(currentCharInChar);
                        FileLogger.recibido(currentCharInString);
                        this.currentCharInBits = "";
                    }
                    while (Integer.parseInt(new SimpleDateFormat(precision).format(new Date()))%2 != 0) {};
                }
                break;
            default:
                break;
        }
        return success;
    }
    
}
