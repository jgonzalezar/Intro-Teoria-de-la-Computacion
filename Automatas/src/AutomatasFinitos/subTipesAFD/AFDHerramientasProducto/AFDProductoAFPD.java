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
        ArrayList<String> QAFD = afd.getQ();
        ArrayList<String> QAFPD = afpd.getQ();
        Q = new ArrayList<>();
        for (int i = 0; i < QAFPD.size(); i++) 
          for (int j = 0; j < QAFD.size(); j++) 
            Q.add("("+QAFPD.get(i)+","+QAFD.get(j)+")");
        q0 = Q.indexOf("("+QAFPD.get(afd.getQ0())+","+QAFD.get(afpd.getQ0())+")");
        //Genera F
        ArrayList<Integer> F1 = afd.getF();
        ArrayList<Integer> F2 = afpd.getF();
        F = new ArrayList<>();
        for (int i = 0; i < QAFPD.size(); i++) 
          for (int j = 0; j < QAFD.size(); j++)
              if (F1.contains(i) && F2.contains(j))
                  F.add(i*QAFD.size()+j);
        //----Gamma----
        Gamma = afpd.getGamma();
        //Genera Delta
        Transitions DeltaAFD = afd.getDelta();
        Transitions DeltaAFPD = afpd.getDelta();
        Delta = new Transition();
        for (String estadosq1 : QAFPD)
          for (String estadosq2 : QAFD)
            for (int k = 0; k < Sigma.length(); k++)
            {
                char a = Sigma.get(k);
                String preDelta = "("+estadosq1+","+estadosq2+")";
                String postDelta = "("+DeltaAFPD.cambio(a, estadosq1)+","+DeltaAFD.cambio(a, estadosq2)+")";
                Delta.add(a, preDelta, postDelta);
            }
        if (){
            
        }
    }
    
}
