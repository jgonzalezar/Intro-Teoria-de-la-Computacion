/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;

import Herramientas.Tuple;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author julia
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*AFD afd;
        try {
            afd = new AFD("D:\\Documents\\GitHub\\Intro-Teoria-de-la-Computacion\\Automatas\\src\\AutomatasPredefinidos\\a.DFA");
        } catch (Error ex) {
            Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        AFN afn;
        try {
            afn = new AFN("D:\\Documents\\GitHub\\Intro-Teoria-de-la-Computacion\\Automatas\\src\\AutomatasPredefinidos\\a.NFA");
        } catch (Error ex) {
            Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
