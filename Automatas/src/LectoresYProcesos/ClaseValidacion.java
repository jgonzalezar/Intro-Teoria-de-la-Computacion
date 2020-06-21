/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import AutomatasFinitos.AFN;
import AutomatasFinitos.AFNL;
import AutomatasFinitos.Alfabeto;

/**
 *
 * @author fanat
 */
public class ClaseValidacion {
    private AFNL afnl;
    private AFN afn;

    public ClaseValidacion(AFNL afnl) {
        this.afnl = afnl;
    }

    public ClaseValidacion(AFN afn) {
        this.afn = afn;
    }
    
    void validarAFNLambdaToAFN(AFNL afnl,Alfabeto sigma){
        AFN Afn = afnl.AFN_LambdaToAFN();
        
        for (int i = 0; i < 5000; i++) {
            afnl.getSigma().generarCadenaAleatoria((int) (Math.random()*i)%100);
            
        }
        
        
    }
}
