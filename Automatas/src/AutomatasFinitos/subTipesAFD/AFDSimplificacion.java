/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos.subTipesAFD;

import AutomatasFinitos.AFD;

/**
 * la clase simplificacion crea una simplificacion de un AFD
 * @author equipo los javas
 * @version 1.2
 */
public class AFDSimplificacion extends AFD
{
    /**
     * constructor para la clase
     * @param afd1 AFD base 
     */
    public AFDSimplificacion(AFD afd1)
    {
       AFD simp = afd1.simplificar();
       
       Sigma=simp.getSigma();
       Q=simp.getQ();
       q0=simp.getQ0();
       F=simp.getF();
       Delta = simp.getDelta();
       elina();
       
    }
    
    private void elina(){
        hallarEstadosInaccesibles();
       eliminarEstadosInaccesibles();
    }

    @Override
    public AFD simplificar() {
        return new AFD(Sigma, Q, q0, F, Delta);
    }
    
    
}
