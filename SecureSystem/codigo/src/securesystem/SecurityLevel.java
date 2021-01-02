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
public class SecurityLevel {
    
    static Level level;
    
    public static boolean dominates(Level level1,Level level2) {
        return level1.ordinal() >= level2.ordinal();
    }
    
}
