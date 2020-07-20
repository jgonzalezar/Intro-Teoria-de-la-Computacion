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
import Herramientas.TransitionAFPD;
import Herramientas.Transitions;
import Herramientas.trioPila;
import java.util.ArrayList;

/**
 *
 * @author brandon
 */
public class AFDProductoAFPD extends AFPD{
    
    

    public AFDProductoAFPD(AFD afd, AFPD afpd) {
        
        Alfabeto SigmaAFD = afd.getSigma();
        Alfabeto SigmaAFPD = afpd.getSigma();
        if (!(SigmaAFD.toString().equals(SigmaAFPD.toString())))
            {
                throw new Error("Los alfabetos deben ser iguales para poder realizar el producto cartesiano de los autómatas.");
            }
        Sigma = SigmaAFD;
        //Genera Q
        ArrayList<String> QAFD = afd.getQ();
        ArrayList<String> QAFPD = afpd.getQ();
        Q = new ArrayList<>();
        for (int i = 0; i < QAFPD.size(); i++) 
          for (int j = 0; j < QAFD.size(); j++) 
            Q.add("("+QAFPD.get(i)+","+QAFD.get(j)+")");
        q0 = Q.indexOf("("+QAFPD.get(afd.getQ0())+","+QAFD.get(afpd.getQ0())+")");
        //Genera F
        ArrayList<Integer> FAFD = afd.getF();
        ArrayList<Integer> FAFPD = afpd.getF();
        F = new ArrayList<>();
        for (int i = 0; i < QAFPD.size(); i++) 
          for (int j = 0; j < QAFD.size(); j++)
              if (FAFD.contains(i) && FAFPD.contains(j))
                  F.add(i*QAFD.size()+j);
        //----Gamma----
        Gamma = afpd.getGamma();
        //Genera Delta
        Transitions DeltaAFD = afd.getDelta();
        Transitions DeltaAFPD = afpd.getDelta();
        Delta = new TransitionAFPD();
        for (String estadosq1 : QAFPD){
          trioPila trio = DeltaAFPD.cambios('$', estadosq1, '$');
          for (String estadosq2 : QAFD){
            if (trio!=null){
                for (String estadosq3 : QAFD)
                Delta.add('$', "("+estadosq1+","+estadosq2+")", trio.getPila(), "("+trio.getDuo().getEstado()+","+estadosq3+")", trio.getDuo().getPila());
            }
            
            for (int k = 0; k < Sigma.length(); k++)
            {
                char a = Sigma.get(k);
                String preDelta = "("+estadosq1+","+estadosq2+")";
                trioPila trioAFPD;
                trioAFPD = DeltaAFPD.cambios(a, estadosq1, a);
                if(trioAFPD!=null){
                String postDelta = "("+trioAFPD.getDuo().getEstado()+","+DeltaAFD.cambio(a, estadosq2)+")";
                Delta.add(a, preDelta, a, postDelta, trioAFPD.getDuo().getPila());
                }
            }
          }
        }
    }
    
}
