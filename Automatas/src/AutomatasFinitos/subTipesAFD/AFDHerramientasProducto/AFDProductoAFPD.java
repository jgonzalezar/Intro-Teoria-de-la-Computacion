/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos.subTipesAFD.AFDHerramientasProducto;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFPD;
import AutomatasFinitos.Alfabeto;
import Herramientas.Transition;
import Herramientas.Transitions;
import java.util.ArrayList;

/**
 *
 * @author brandon
 */
public class AFDProductoAFPD extends AFPD{
    
    

    public AFDProductoAFPD(AFD afd, AFPD afpd) {
        
        Alfabeto Sigma1 = afd.getSigma();
        Alfabeto Sigma2 = afpd.getSigma();
        if (!(Sigma1.toString().equals(Sigma2.toString())))
            {
                throw new Error("Los alfabetos deben ser iguales para poder realizar el producto cartesiano de los autómatas.");
            }
        Sigma = Sigma1;
        //Genera Q
        ArrayList<String> Q1 = afd.getQ();
        ArrayList<String> Q2 = afpd.getQ();
        Q = new ArrayList<>();
        for (int i = 0; i < Q1.size(); i++) 
          for (int j = 0; j < Q2.size(); j++) 
            Q.add("("+Q1.get(i)+","+Q2.get(j)+")");
        q0 = Q.indexOf("("+Q1.get(0)+","+Q2.get(0)+")");
        //----Gamma----
        Gamma = afpd.getGamma();
        //Genera Delta
        Transitions Delta1 = afd.getDelta();
        Transitions Delta2 = afpd.getDelta();
        Delta = new Transition();
        for (String estadosq1 : Q1)
          for (String estadosq2 : Q2)
            for (int k = 0; k < Sigma.length(); k++)
            {
                char a = Sigma.get(k);
                String preDelta = "("+estadosq1+","+estadosq2+")";
                String postDelta = "("+Delta1.cambio(a, estadosq1)+","+Delta2.cambio(a, estadosq2)+")";
                Delta.add(a, preDelta, postDelta);
            }
        
    }
    
}
