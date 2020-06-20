/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;

import AutomatasFinitos.AFD;
import Herramientas.Transition;
import Herramientas.Transitions;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author equipo los javas
 * @version 1.2
 */
public class AFNL_AFN extends AFN {
    
    public AFNL_AFN(AFNL afnl) {
       AFN afn = afnl.AFN_LambdaToAFN();
       
       Sigma=afn.getSigma();
       Q=afn.getQ();
       q0=afn.getQ0();
       F=afn.getF();
       Delta = afn.getDelta();
       
    }    
    
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogTitle("Seleccione el automata que desea importar");
        
        try {
            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.CANCEL_OPTION) {
                throw new NullPointerException();
            }
            String url;
            url = fileChooser.getSelectedFile().getAbsolutePath();
            AFNL afnl = new AFNL(url);
            AFNL_AFN afnl_afn = new AFNL_AFN(afnl);
            
                
        }catch(HeadlessException | NullPointerException e){
                e.printStackTrace();
        }
        
    }
}
