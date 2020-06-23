/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;

/**
 *  clase con constructor que hereda AFN recibiendo un AFNL al cual convertira en AFN
 * @author equipo los javas
 * @version 1.2
 */
public class AFNL_AFN extends AFN {
    
    /**
     * contrusctor de la clase
     * @param afnl AFNL base
     */
    public AFNL_AFN(AFNL afnl) {
       AFN afn = afnl.AFN_LambdaToAFN();
       
       Sigma=afn.getSigma();
       Q=afn.getQ();
       q0=afn.getQ0();
       F=afn.getF();
       Delta = afn.getDelta();
       
    }    
}
