/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;

import AutomatasFinitos.AFD;
import Herramientas.Transition;
import Herramientas.Transitions;
import java.util.ArrayList;

/**
 *
 * @author equipo los javas
 * @version 1.2
 */
public class AFDSimplificacion extends AFD
{
    
    public AFDSimplificacion(AFD afd1)
    {
       AFD simp = afd1.simplificar();
       
       Sigma=simp.getSigma();
       Q=simp.getQ();
       q0=simp.getQ0();
       F=simp.getF();
       Delta = simp.getDelta();
       
    }

    @Override
    AFD simplificar() {
        return new AFD(Sigma, Q, q0, F, Delta);
    }
    
    
}
