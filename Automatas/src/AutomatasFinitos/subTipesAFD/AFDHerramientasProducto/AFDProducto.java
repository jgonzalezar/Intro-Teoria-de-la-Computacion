/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos.subTipesAFD.AFDHerramientasProducto;

import AutomatasFinitos.AFD;
import AutomatasFinitos.Alfabeto;
import Herramientas.Transition;
import Herramientas.Transitions;
import java.util.ArrayList;

/**
 * la clase AFDProducto extiente a un AFD y sirve para crear un AFD que seria sus productos pero sin iniciar sus finales
 * @author equipo los javas
 * @version 1.2
 */
public class AFDProducto extends AFD
{
    /**
     * constructor para la clase
     * @param afd1 AFD base uno
     * @param afd2 AFD base dos
     */
    public AFDProducto(AFD afd1, AFD afd2)
    {
        Alfabeto Sigma1 = afd1.getSigma();
        Alfabeto Sigma2 = afd2.getSigma();
        if (!(Sigma1.toString().equals(Sigma2.toString())))
            {
                throw new Error("Los alfabetos deben ser iguales para poder realizar el producto cartesiano de los autómatas.");
            }
        Sigma = Sigma1;
        //Genera Q
        ArrayList<String> Q1 = afd1.getQ();
        ArrayList<String> Q2 = afd2.getQ();
        Q = new ArrayList<>();
        for (int i = 0; i < Q1.size(); i++) 
          for (int j = 0; j < Q2.size(); j++) 
            Q.add("("+Q1.get(i)+","+Q2.get(j)+")");
        q0 = Q.indexOf("("+Q1.get(afd1.getQ0())+","+Q2.get(afd2.getQ0())+")");
        //Genera Delta
        Transitions Delta1 = afd1.getDelta();
        Transitions Delta2 = afd2.getDelta();
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
